
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Mike Simister
 * This class is the main thread. 
 * It's job is to listen for a connection on the serverSocket's predetermined port
 * Master can accept many parallel connections.
 */

public class Master extends Thread {
	
	private ServerSocket serverSocket = null;
	private boolean stopped = false;	
		
	public Master(ServerSocket s)
	{
		serverSocket = s;
	}
	
	/**
	 * serverSocket is used to listen for a connection, when one is found a
	 * worker thread is spawned to handle the connection request
	 */
	public void run(){		
		
		// server_listen loop, while !stopped,
		// listen for incoming connections, 
		// if one exists create a thread to handle it
		while(!(stopped))
		{
			Socket clientSocket = new Socket();
			
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
			if(!(clientSocket.isClosed()))
			{
				new Thread(new SlaveThread(clientSocket)).start();			
			}
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
