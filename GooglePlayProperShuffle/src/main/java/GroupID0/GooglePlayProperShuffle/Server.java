package GroupID0.GooglePlayProperShuffle;

import java.io.IOException;
import java.net.ServerSocket;

public class Server
{
	public static void main(String[] args)
	{
		new Server(Integer.parseInt(args[0]));
	}
	
	private boolean running = true;
	
	public Server(int port)
	{
		try
		{
			ServerSocket socket = new ServerSocket(port);
			do
			{
				ServerThread thread = new ServerThread(socket.accept());
				thread.start();
			} while(running);
			socket.close();
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
