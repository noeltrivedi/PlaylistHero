package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SuggestionsActivity extends Activity {
    public static int ADD_SONG_CODE = 1234, RESULT_OK = 12;
    public static String djID = "128019249"; //default points to Noel Trivedi's djID
    SongSingleton songsSingleton;
    ArrayList<Song> songs;
    ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_playlist);

        Intent i = getIntent();
        djID = i.getStringExtra("userID");

        songsSingleton = SongSingleton.getInstance();
        songs = songsSingleton.getSongs();
        loadSuggestions();
        SongAdapter adapter = new SongAdapter(this, songs);

        ListView listView = (ListView)findViewById(R.id.songList);
        listView.setAdapter(adapter);

        //Add Button
        addButton = (ImageButton)findViewById(R.id.addButton);
        addButton.bringToFront();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuggestionsActivity.this, SuggestionSearchActivity.class);
                SuggestionsActivity.this.startActivityForResult(i, ADD_SONG_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_SONG_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                ListView listView = (ListView)findViewById(R.id.songList);
                ((SongAdapter)listView.getAdapter()).notifyDataSetChanged();
            }
        }
    }

    private void loadSuggestions()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DJs");

        query.whereMatches("userID", djID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //success
                    ArrayList<Song> songs = SongSingleton.getInstance().getSongs();
                    songs.clear();
                    JSONArray arr = objects.get(0).getJSONArray("suggestions");
                    for(int i = 0; i < arr.length(); i++)
                    {
                        try
                        {
                            JSONObject obj = arr.getJSONObject(i);
                            String songID = obj.getString("songID");
                            String votes = obj.getString("votes");

                            Song newSong = Utils.formatTrack(Utils.spotify.getTrack(songID));
                            newSong.setVotes(Integer.parseInt(votes));

                            songs.add(newSong);

                        }
                        catch (JSONException e1)
                        {
                            e1.printStackTrace();
                        }
                    }

                    Collections.sort(SongSingleton.getInstance().getSongs(), new Comparator<Song>() {
                        @Override
                        public int compare(Song lhs, Song rhs) {
                            return -1 * Integer.compare(lhs.getVotesInt(), rhs.getVotesInt());
                        }
                    });

                    ListView listView = (ListView) findViewById(R.id.songList);

                    ((SongAdapter) listView.getAdapter()).notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inside_playlist, menu);
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

    @Override
    public void onDestroy()
    {
        SongSingleton.getInstance().getSongs().clear();
        super.onDestroy();
    }

}
