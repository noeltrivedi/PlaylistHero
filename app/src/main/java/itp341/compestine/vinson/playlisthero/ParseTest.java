package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ParseTest extends ListActivity{
    private List<parseSong> playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_test);

        playlist= new ArrayList<parseSong>();
        ArrayAdapter<parseSong> adapter = new ArrayAdapter<parseSong>(this, R.layout.list_item_layout, playlist);
        setListAdapter(adapter);

        refreshSongList();
    }

    private void refreshSongList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Song");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> songList, ParseException e) {
            if(e == null){
                playlist.clear();
                for(ParseObject song : songList){
                    parseSong parseSong = new parseSong(song.getObjectId(), song.getString("songID"), song.getString("Name"));
                    playlist.add(parseSong);
                }
                ((ArrayAdapter<parseSong>) getListAdapter()).notifyDataSetChanged();
            }
                else{
                Log.d(getClass().getSimpleName(), "Error: " +e.getMessage());
            }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parse_test, menu);
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
