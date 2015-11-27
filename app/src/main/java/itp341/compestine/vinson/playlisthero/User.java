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
public class User implements Displayable{

    Drawable profilePicture;
    String username;
    String numListeners;
    String userID;

    public User(Drawable profilePicture, String username, String numListeners, String userID){
        this.profilePicture = profilePicture;
        this.username = username;
        this.numListeners = numListeners;
        this.userID = userID;
    }

    public String getUserID()
    {
        return userID;
    }

    @Override
    public String getTitle()
    {
        return username;
    }

    @Override
    public String getSubtitle() {
        return numListeners + " listeners";
    }

    @Override
    public Drawable getArt() {
        return profilePicture;
    }
}
