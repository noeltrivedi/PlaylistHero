package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
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

    public static User formatUser(UserPublic u)
    {
        if(u.images.isEmpty())
        {
            return new User(null, u.display_name, "100", u.id);
        }
        String playlistPicture = u.images.get(0).url;
        Drawable albumArt = null;
        try {
            InputStream is = (InputStream) new URL(playlistPicture).getContent();
            albumArt = Drawable.createFromStream(is, playlistPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String numListeners = "900";

        return new User(albumArt, u.display_name, numListeners, u.id);
    }

    public static Song formatTrack(Track t)
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

    public static void pushToParse(final Song song, final String djID)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DJs");
        query.whereMatches("userID", djID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null)
                {
                    ParseObject row = objects.get(0);
                    JSONArray arr = row.getJSONArray("suggestions");
                    arr.put(song.getJSONObject());

                    row.put("suggestions", arr);
                    row.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null)
                            {
                                Log.i("SAVE", "SUCCESS");
                            }
                            else
                            {
                                Log.i("SAVE", "FAIL");
                            }
                        }
                    });
                }
            }
        });
    }

    public static void updateVotesParse(final Song song) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(SuggestionsActivity.djID);
        query.whereEqualTo("songID", song.getSongID());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
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
