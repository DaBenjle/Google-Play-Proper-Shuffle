package GroupID0.GooglePlayProperShuffle;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread
{
	
	private Socket socket;
	
	public ServerThread(Socket socket)
	{
		super("Server Thread");
		this.socket = socket;
	}
	
	@Override
	public void run()
	{
		try
		{
			DataInputStream in = new DataInputStream(socket.getInputStream());
			String playlistName = in.readLine();
			System.out.println(playlistName);
			//new Shuffler("Ben's Favorites");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
