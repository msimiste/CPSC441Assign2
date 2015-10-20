import java.io.*;
import java.net.*;


public class SlaveThread extends Thread {

	private Socket clientSocket = null;

	public SlaveThread(Socket s) {
		clientSocket = s;
	}

	public void run() {

		String s = "";
		try {
			InputStream input = clientSocket.getInputStream();

			// read the inputstream ie, the get request into a byte array
			byte[] buf = new byte[1024];
			int count = input.read(buf);

			// extract the entire request into a string
			if (count > 0) {
				s += new String(buf, 0, count);
			}

			OutputStream output = clientSocket.getOutputStream();

			String responseHeader = formResponseHeader(s);

			// if first line of the header contains 200 OK then output the file
			if (responseHeader.contains("200 OK")) {

				File f = Utility.getFile(s);

				responseHeader = goodRequestAppendHeader(responseHeader, f);
				output.write((responseHeader).getBytes());

				// read the file into a byte array
				FileInputStream inFile = new FileInputStream(f);
				buf = new byte[1024];
				count = 0;

				// read file to byteArray/write file to output stream
				while ((count = inFile.read(buf)) > 0) {
					output.write(buf);
					output.flush();
				}
				inFile.close();
			}

			// some type of error happened
			// append the header and write to outputstream
			else 
			{
				responseHeader = badRequestAppendHeader(responseHeader);
				output.write(responseHeader.getBytes());
			}

			output.close();
			input.close();

		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	private String formResponseHeader(String s) {
		
		// conditionally form the first line of the response it will be
		// either: 200 ok, or 400 bad request or 404 not found
		String line1 = "HTTP/1.1 " + Utility.checkRequest(s) + "\r\n";
		String line2 = "Connection: close\r\n";

		// conditionally for the third line of the response to the current
		// date/time
		String line3 = "Date: "
				+ Utility.convertDateToString(System.currentTimeMillis())
				+ "\r\n";

		String header = line1 + line2 + line3;
		return header;
	}

	private String goodRequestAppendHeader(String s, File f) {

		long fileSize = f.length(); // get the filesize

		// get last modified date for file
		String lastMod = Utility.convertDateToString(f.lastModified());

		String line4 = "Last-Modified: " + lastMod + "\r\n";
		String line5 = "Content-Length: " + fileSize + "\r\n\r\n";

		String header = s + line4 + line5;
		return header;
	}

	private String badRequestAppendHeader(String s) {

		String[] arr = s.split("\r\n");
		String line1 = arr[0];
		String line4 = "Content-Type: text/html\r\n\r\n";
		String line5 = "<html>\r\n <body>\r\n" + line1
				+ " \r\n </body>\r\n  </html> ";

		String header = s + line4 + line5;
		return header;

	}

}
