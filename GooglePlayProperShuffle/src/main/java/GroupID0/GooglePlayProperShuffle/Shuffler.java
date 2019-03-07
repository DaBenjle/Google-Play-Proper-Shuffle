package GroupID0.GooglePlayProperShuffle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.github.felixgail.gplaymusic.api.GPlayMusic;
import com.github.felixgail.gplaymusic.api.PlaylistApi;
import com.github.felixgail.gplaymusic.model.Playlist;
import com.github.felixgail.gplaymusic.model.PlaylistEntry;
import com.github.felixgail.gplaymusic.model.Track;
import com.github.felixgail.gplaymusic.util.TokenProvider;

import svarzee.gps.gpsoauth.AuthToken;
import svarzee.gps.gpsoauth.Gpsoauth.TokenRequestFailed;

public class Shuffler
{
	private AuthToken auth;
	private GPlayMusic api;
	private Playlist playlist;
	public static final String shuffledID = "cbee0f77-4ad5-468d-aa69-455cc5a15a71";
	
	private static String getAuthStr()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File("myAuth.txt")));
			return br.readLine();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void setAuth()
	{
		String authStr = getAuthStr();
		String[] txtAuth = authStr.split(" "); 
		try
		{
			auth = TokenProvider.provideToken(txtAuth[0], txtAuth[1], txtAuth[2]);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (TokenRequestFailed e)
		{
			e.printStackTrace();
		}
	}
	
	public Shuffler(String input)
	{
		setAuth();
		api = new GPlayMusic.Builder().setAuthToken(auth).build();
		Playlist shuffleThis = getPlaylist(input);
		try
		{
			getPlaylist().removeEntries(getPlaylist().getContents(Integer.MAX_VALUE));
			getPlaylist().addTracks(getShuffledTracks(shuffleThis));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static List<Track> getShuffledTracks(Playlist input)
	{
		ArrayList<Track> output = new ArrayList<>();
		List<PlaylistEntry> contents = new ArrayList<>();
		try
		{
			contents = input.getContents(Integer.MAX_VALUE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException("Playlist doesn't exist!");
		}
		
		
		for(PlaylistEntry entry : contents)
		{
			try
			{
				Track cur = entry.getTrack();
				output.add(cur);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		Collections.shuffle(output);
		return output;
	}
	
	/*
	 * Gets playlist of a specified name.
	 */
	public Playlist getPlaylist(String name)
	{
		try
		{
			PlaylistApi playlistAPI = api.getPlaylistApi();
			for(Playlist cur : playlistAPI.listPlaylists())
			{
				if(cur.getName().equals(name))
				{
					return cur;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Gets resulting playlist from the static field shuffledID.
	 * In order to change this playlist create it manually through Google Play Music,
	 * then iterate through all playlists (using getPlaylistIDs) printing both their ID and name. 
	 * Match the wanted playlist name to its ID, then change the constant shuffledID to match.
	 * Maybe I will eventually add an option to specify a playlist, but I do not want to risk accidentally resetting one of my other playlists.
	 */
	public Playlist getPlaylist()
	{
		if(playlist == null)
		{
			PlaylistApi playlistAPI = api.getPlaylistApi();
			try
			{
				playlist = playlistAPI.getPlaylist(shuffledID);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return playlist;
	}
	
	public String getPlaylistIDs()
	{
		PlaylistApi playlistAPI = api.getPlaylistApi();
		String ans = "";
		try
		{
			for(Playlist cur : playlistAPI.listPlaylists())
			{
				ans += cur.getName() + ": " + cur.getId() + '\n';
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return ans;
	}

}
