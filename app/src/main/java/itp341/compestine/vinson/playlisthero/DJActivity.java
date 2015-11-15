package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

public class DJActivity extends Activity {
    SuggestedSongSingleton suggestions;
    ArrayList<Song> suggestedSongs;
    SongSingleton accepted;
    ArrayList<Song> acceptedSongs;
    ListView queue;
    ListView pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);

        //Current playlist
        accepted = SongSingleton.getInstance();
        acceptedSongs = accepted.getSongs();
        SongAdapter acceptedAdapter = new SongAdapter(this, acceptedSongs);
        queue = (ListView)findViewById(R.id.currentSongs);
        queue.setAdapter(acceptedAdapter);


        //For Pending Queue
        suggestions = SuggestedSongSingleton.getInstance();
        suggestedSongs= suggestions.getSuggestions();
        final SongAdapter pendingAdapter = new SongAdapter(this, suggestedSongs);
        pending = (ListView)findViewById(R.id.suggestedSongs);
        pending.setAdapter(pendingAdapter);

        for(int i = 0; i < 10; i ++){
            Song test = new Song("40 Miles", "Yonder Mountain", getResources().getDrawable(R.drawable.yonder), 100);
            acceptedSongs.add(test);
        }

        for(int a = 0; a < 5; a ++){
            Song test2= new Song ("My Jelly", "Stranger Danger", getResources().getDrawable(R.drawable.we_be_jammin_400x400), 50);
            suggestedSongs.add(test2);
        }

        pending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                suggestedSongs.remove(position);
                pendingAdapter.notifyDataSetChanged();
            }
        });





    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dj, menu);
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
