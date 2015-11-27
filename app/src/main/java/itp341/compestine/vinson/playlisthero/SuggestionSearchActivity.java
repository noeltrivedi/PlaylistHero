package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.client.Response;

public class SuggestionSearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_search);

        SearchSingleton searchSingleton = SearchSingleton.getInstance();
        ArrayList<Song> songs = searchSingleton.getSongs();
        DisplayableAdapter<Song> adapter = new DisplayableAdapter(this, songs);

        ListView listView = (ListView) findViewById(R.id.searchedTracks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song selectedSong = ((DisplayableAdapter<Song>) (parent.getAdapter())).getItem(position);
                Utils.pushToParse(selectedSong, SuggestionsActivity.djID);
                Toast.makeText(SuggestionSearchActivity.this,
                        "Added " + selectedSong.getName() + " to the suggestions list",
                        Toast.LENGTH_LONG)
                        .show();

                SongSingleton.getInstance().newSong(selectedSong);

                setResult(SuggestionsActivity.RESULT_OK);
                finishActivity(SuggestionsActivity.ADD_SONG_CODE);
            }
        });

        EditText searchBar = (EditText) findViewById(R.id.searchBar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    handled = true;
                    String search = v.getText().toString();
                    search.trim().replace(' ', '+');
                    searchSong(search);
                    Utils.hideSoftKeyboard(SuggestionSearchActivity.this);
                }
                return handled;
            }
        });
    }

    private void searchSong(String search) {
        Utils.spotify.searchTracks(search, new SpotifyCallback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                SearchSingleton searchSingleton = SearchSingleton.getInstance();
                searchSingleton.clear();
                for (Track t : tracksPager.tracks.items) {
                    searchSingleton.addSong(Utils.formatTrack(t));
                }

                ListView lv = (ListView) findViewById(R.id.searchedTracks);
                ((DisplayableAdapter<Song>) lv.getAdapter()).notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.menu_suggestion_search, menu);
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
