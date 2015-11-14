package itp341.compestine.vinson.playlisthero;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class SongSingleton {
    private static SongSingleton singleton= new SongSingleton();
    private static ArrayList<Song> songs = new ArrayList<Song>();
    private SongSingleton() {
    }

    public static SongSingleton getInstance() {
        return singleton;
    }

    public ArrayList<Song> getSongs(){
        return songs;
    }

    public int getSize(){
        return songs.size();
    }

    public void newSong(Song addSong){
        songs.add(addSong);
    }

    public void deleteSong(int position){
        songs.remove(position);
    }




}

