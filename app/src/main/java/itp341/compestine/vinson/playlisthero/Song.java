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
    String songID;

    public Song (String name, String artist, Drawable songArt, int votes, String songID){
        this.name = name;
        this.songArt = songArt;
        this.votes = votes;
        this.artist = artist;
        this.songID = songID;
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
    public int getVotesInt()
    {
        return votes;
    }

    public String getSongID(){
        return songID;
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

    public void setSongID(String songID){
        this.songID = songID;
    }

    public void upVote(){
        votes += 1;
    }

    public void downVote(){
        votes -= 1;
    }



}
