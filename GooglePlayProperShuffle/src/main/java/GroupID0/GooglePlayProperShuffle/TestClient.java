package GroupID0.GooglePlayProperShuffle;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestClient
{
	public static void main(String[] args)
	{
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		try
		{
			Socket client = new Socket(serverName, port);
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF("Techno Favorites");
			client.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}