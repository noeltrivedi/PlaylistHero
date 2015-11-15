package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.client.Response;

public class AddSong extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        EditText searchBar = (EditText)findViewById(R.id.searchBar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    handled = true;
                    String search = v.getText().toString();
                    search.replace(' ', '+');
                    searchSong(search);
                }
                return handled;
            }
        });

    }

    private void searchSong(String search)
    {
        SpotifyApi api = new SpotifyApi();

        SpotifyService spotify = api.getService();
        spotify.searchTracks(search, new SpotifyCallback<TracksPager>(){
            @Override
            public void success(TracksPager tracksPager, Response response)
            {
                //String toast = response.toString();
                for(Track t : tracksPager.tracks.items) {
                    String toast = "Track found: " + t.name + " by " + Utils.formatArtists(t.artists);
                    Log.i("Text:", toast);
                }
            }
            @Override
            public void failure(SpotifyError spotifyError) {
                String toast = "Track not found";
                Log.e("Search", toast);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_song, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
