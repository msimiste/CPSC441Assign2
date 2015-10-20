
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Master extends Thread {
	
	private ServerSocket serverSocket = null;
	private boolean stopped = false;	
		
	public Master(ServerSocket s)
	{
		serverSocket = s;
	}
	public void run(){
		
		
		while(!(stopped))
		{
			Socket clientSocket = null;
			
			try {
				//System.out.println("Did I make it");
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
	public  void setStopped()
	{
		stopped = true;
	}

}
