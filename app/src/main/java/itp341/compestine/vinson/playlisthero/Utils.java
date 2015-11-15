package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.UserPublic;

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

    public static JSONArray convertSongArray(ArrayList<Song> songs)
    {
        JSONArray array = new JSONArray();
        for(Song s : songs)
        {
            Log.i("JSON", "Song " + s.getName() + ": " + s.getJSONObject());
            array =array.put(s.getJSONObject());
        }
        Log.i("JSON2", array.toString());
        return array;
    }

    public static Playlist convertUserToPlaylist(UserPublic u)
    {
        if(u.images.isEmpty())
        {
            return new Playlist(null, u.display_name, "100", u.id);
        }
        String playlistPicture = u.images.get(0).url;
        Drawable albumArt = null;
        try {
            InputStream is = (InputStream) new URL(playlistPicture).getContent();
            albumArt = Drawable.createFromStream(is, playlistPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Playlist(albumArt, u.display_name, ""+900, u.id);
    }

    public static Song convertTrackToSong(Track t)
    {
        if(t == null)
        {
            Log.e("TrackNull", "TRACK NULL");
            return null;
        }
        if(t.album.images.size() == 0)
            return new Song(t.name, Utils.formatArtists(t.artists), null, 1, t.id);
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

        return new Song(t.name, Utils.formatArtists(t.artists), albumArt, 1, t.id);
    }

    public static void pushToParse(Song song)
    {
        ParseObject songToUpload = new ParseObject(InsidePlaylist.djID);
        songToUpload.put("songID", song.getSongID());
        songToUpload.put("Votes", song.getVotesInt());
        songToUpload.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Parse", "Success");
                } else {
                    Log.i("Parse", "Failure");
                }
            }
        });
    }

    public static void updateVotesParse(final Song song)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(InsidePlaylist.djID);
        query.whereEqualTo("songID", song.getSongID());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size() > 0)
                {
                    ParseObject po = objects.get(0);
                    po.put("Votes", song.getVotesInt());
                    try {
                        po.save();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

}
