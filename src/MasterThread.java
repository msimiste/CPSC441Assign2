import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MasterThread extends Thread {

	
	private int port;
	private ServerSocket serverSocket = null;
	private boolean stopped = false;
	private Thread runningThread = null;
	
	
	public MasterThread(int port)
	{
		//serverSocket = s;
		this.port = port;		
	}
	
	@Override
	public void run(){
		
		int t = 0;
		
		while(!(stopped))
		{
			Socket clientSocket = null;
			
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				
				e.printStackTrace();
				if(isStopped())
				{
					System.out.println("Server Stopped");
					return;
				}
				throw new RuntimeException("Client Connection error",e);
				
				
			}
			new Thread (new SlaveThread(clientSocket)).start();
			
		}
	}
	
/*	public synchronized void stop(){
		stopped = true;
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	
	
	private synchronized boolean isStopped() {
		return this.stopped;
	}

}
