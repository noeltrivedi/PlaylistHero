package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class Playlist {

    Drawable songImage;
    String playlistTitle;
    String numListeners;
    public Playlist (Drawable songImage, String playlistTitle, String numListeners){
        this.songImage = songImage;
        this.playlistTitle = playlistTitle;
        this.numListeners = numListeners;
    }

    public Drawable getSongImage(){
        return songImage;
    }

    public String getPlaylistTitle(){
        return playlistTitle;
    }

    public String getNumListeners(){
        return numListeners;
    }

    public void setSongImage(Drawable songImage){
        this.songImage = songImage;
    }

    public void setPlaylistTitle(String playlistTitle){
        this.playlistTitle = playlistTitle;
    }

    public void setNumListeners(String numListeners) {
        this.numListeners = numListeners;
    }
}
