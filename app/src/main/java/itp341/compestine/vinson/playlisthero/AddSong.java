package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.client.Response;

public class AddSong extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        SearchSingleton searchSingleton = SearchSingleton.getInstance();
        ArrayList<Song> songs = searchSingleton.getSongs();
        SearchedTrackAdapter adapter= new SearchedTrackAdapter(this, songs);

        ListView listView = (ListView)findViewById(R.id.searchedTracks);
        listView.setAdapter(adapter);

        EditText searchBar = (EditText)findViewById(R.id.searchBar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    handled = true;
                    String search = v.getText().toString();
                    search.trim().replace(' ', '+');
                    searchSong(search);
                    Utils.hideSoftKeyboard(AddSong.this);
                }
                return handled;
            }
        });

    }

    public void onTableRowClick(View view)
    {
        String songName = ((TextView)view.findViewById(R.id.searchedTrackName)).getText().toString();
        String songArtist = ((TextView)view.findViewById(R.id.searchedTrackArtist)).getText().toString();
        ArrayList<Song> songs = SearchSingleton.getInstance().getSongs();
        Song song = null;
        for(Song s : songs)
        {
            if(s.getName().equals(songName) && s.getArtist().equals(songArtist))
            {
                song = s;
            }
        }
        if(song == null) {
            Log.e("ERROR", "Error searching for track " + songName + " in the singleton array");
            return;
        }
        Log.i("SongInfo", song.getSongID() + " " + song.getVotes());
        Utils.pushToParse(song);
        Toast.makeText(this, "Added " + song.getName() + " to the suggestions list", Toast.LENGTH_LONG).show();

        SongSingleton.getInstance().newSong(song);

        setResult(InsidePlaylist.RESULT_OK);

        finishActivity(InsidePlaylist.ADD_SONG_CODE);
        Intent i = new Intent(AddSong.this, InsidePlaylist.class);
        startActivity(i);
    }

    private void searchSong(String search)
    {
        Utils.spotify.searchTracks(search, new SpotifyCallback<TracksPager>(){
            @Override
            public void success(TracksPager tracksPager, Response response)
            {
                SearchSingleton searchSingleton = SearchSingleton.getInstance();
                searchSingleton.clear();
                for(Track t : tracksPager.tracks.items) {
                    Log.i("TrackInfo", t.name + ": " + t.id);
                    searchSingleton.addSong(Utils.convertTrackToSong(t));
                }
            }
            @Override
            public void failure(SpotifyError spotifyError) {
                String toast = "Error searching for track";
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG);
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
