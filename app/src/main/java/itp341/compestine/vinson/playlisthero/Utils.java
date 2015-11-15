package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by Noel on 11/14/2015.
 */
public class Utils {
    public static final String client_id = "77ec119ce23f42efb66d5d18fbb04580";
    private static String authToken = null;
    private static SpotifyApi api = new SpotifyApi();
    public static SpotifyService spotify;
    static
    {
        spotify = api.getService();
    }

    public static void setAuthToken(String token)
    {
        authToken = token;
        api.setAccessToken(authToken);
    }

    public static String getAuthToken()
    {
        return authToken;
    }

    public static String formatArtists(List<ArtistSimple> artists)
    {
        if(artists.size() > 0) {
            String formattedArtists = "";
            for(int i = 0; i < artists.size(); i++)
            {
                formattedArtists += artists.get(i).name;
                if(i != artists.size()-1)
                    formattedArtists += ", ";
            }

            return formattedArtists;
        }
        else
        {
            return null;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Song convertTrackToSong(Track t)
    {
        if(t == null)
        {
            Log.e("TrackNull", "TRACK NULL");
            return null;
        }
        if(t.album.images.size() == 0)
            return new Song(t.name, Utils.formatArtists(t.artists), null, 0);
        String albumURL = t.album.images.get(0).url;

        for(Image i : t.album.images)
        {
            if(i.height == 64 && i.width == 64)
            {
                albumURL = i.url;
                break;
            }
        }
        //Convert the album art URL into an image
        Drawable albumArt = null;
        try {
            InputStream is = (InputStream) new URL(albumURL).getContent();
            albumArt = Drawable.createFromStream(is, albumURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Song(t.name, Utils.formatArtists(t.artists), albumArt, 0);
    }
}
