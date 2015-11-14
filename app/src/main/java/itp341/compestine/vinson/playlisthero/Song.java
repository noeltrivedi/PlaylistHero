package itp341.compestine.vinson.playlisthero;

import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class Song {
    String name;
    Drawable songArt;
    int votes;
    String artist;

    public Song (String name, String artist, Drawable songArt, int votes){
        this.name = name;
        this.songArt = songArt;
        this.votes = votes;
        this.artist = artist;
    }

    public String getName(){
        return name;
    }

    public String getArtist(){
        return artist;
    }

    public Drawable getSongArt(){
        return songArt;
    }

    public String getVotes(){

        String score = String.valueOf(votes);
        return score;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }

    public void setSongArt(Drawable songArt){
        this.songArt = songArt;
    }

    public void setVotes(int votes){
        this.votes = votes;
    }

    public void upVote(){
        votes += 1;
    }

    public void downVote(){
        votes -= 1;
    }



}
