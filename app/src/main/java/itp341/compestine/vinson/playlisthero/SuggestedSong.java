package itp341.compestine.vinson.playlisthero;

import android.graphics.drawable.Drawable;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class SuggestedSong {
    int score;
    Drawable albumArt;
    String name;

    public SuggestedSong(int score, Drawable albumArt, String name){
        this.score = score;
        this.albumArt = albumArt;
        this.name = name;
    }

    public int getScore(){
        return score;
    }

    public Drawable getAlbumArt(){
        return albumArt;
    }
    public String getName(){
        return name;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setAlbumArt(Drawable albumArt){
        this.albumArt = albumArt;
    }
    public void setName(String name){
        this.name = name;
    }


}
