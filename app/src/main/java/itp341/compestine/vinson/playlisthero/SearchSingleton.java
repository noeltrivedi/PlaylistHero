package itp341.compestine.vinson.playlisthero;

import java.util.ArrayList;

/**
 * Created by Noel on 11/14/2015.
 */
public class SearchSingleton {
    private static SearchSingleton searchSingleton= new SearchSingleton();
    private static ArrayList<Song> songs = new ArrayList<Song>();

    private SearchSingleton(){}

    public static SearchSingleton getInstance()
    {
        return searchSingleton;
    }

    public ArrayList<Song> getSongs()
    {
        return songs;
    }

    public void addSong(Song s)
    {
        songs.add(s);
    }

    public int getSize()
    {
        return songs.size();
    }

    public void clear()
    {
        songs.clear();
    }


}
