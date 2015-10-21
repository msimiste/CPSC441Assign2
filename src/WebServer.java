import java.net.*;
import java.io.*;

/**
 * 
 * @author Mike Simister
 *
 */
 class WebServer {

	private int port;
	private ServerSocket serverSocket = null;
	private Master mt;

	public WebServer(int port) {
		this.port = port;
	}

	public void start() {

		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can not open port: " + port, e);
		}

		mt = new Master(serverSocket);
		mt.start();

	}

	public void stop() {
		mt.setStopped(); // stop the loop in master thread
	}
}
