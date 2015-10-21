
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Mike Simister
 *
 */

public class Master extends Thread {
	
	private ServerSocket serverSocket = null;
	private boolean stopped = false;	
		
	public Master(ServerSocket s)
	{
		serverSocket = s;
	}
	
	
	public void run(){		
		
		// server_listen loop, while !stopped,
		// listen for incoming connections, 
		// if one exists create a thread to handle it
		while(!(stopped))
		{
			Socket clientSocket = null;
			
			try {
				
				clientSocket = this.serverSocket.accept();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				if(stopped)
				{
					System.out.println("Server Stopped");
					return;
				}
				throw new RuntimeException("Client Connection error",e);				
			}
			new Thread(new SlaveThread(clientSocket)).start();			
		}		
	}
	/**
	 * method toggles the boolean value stopped
	 * which in turn controls the server_listen loop.
	 */
	public void setStopped()
	{
		stopped = true;
		
	}

}
