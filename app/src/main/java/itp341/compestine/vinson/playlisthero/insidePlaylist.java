package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class insidePlaylist extends Activity {

    SongSingleton songsSingleton;
    ArrayList<Song> songs;
    Song holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_playlist);

        songsSingleton = SongSingleton.getInstance();
        Intent i = getIntent();
        songs = songsSingleton.getSongs();
        SongAdapter adapter = new SongAdapter(this, songs);

        ListView listView = (ListView)findViewById(R.id.songList);
        listView.setAdapter(adapter);

        //Test ListView
        Drawable yonder = getResources().getDrawable(R.drawable.yonder);
        Song testSong = new Song("Bolton Stretch", "Yonder Mountain String Band",yonder, 50);
        songsSingleton.newSong(testSong);

       


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
}
