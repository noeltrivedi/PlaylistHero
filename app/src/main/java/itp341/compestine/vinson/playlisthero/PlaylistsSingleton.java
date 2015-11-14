package itp341.compestine.vinson.playlisthero;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class PlaylistsSingleton {
    private static PlaylistsSingleton singleton = new PlaylistsSingleton();
    private static ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    private PlaylistsSingleton() {
    }

    public static PlaylistsSingleton getInstance() {
        return singleton;
    }

    public ArrayList<Playlist> getList(){
        return playlists;
    }

    public int getSize(){
        return playlists.size();
    }

    public void newPlaylist(Playlist addPlaylist){
        playlists.add(addPlaylist);
    }
public void deletePlaylist(int position){
        playlists.remove(position);
    }



}
