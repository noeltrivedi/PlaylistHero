package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class insidePlaylist extends Activity {
    public static int ADD_SONG_CODE = 1234, RESULT_OK = 12;
    SongSingleton songsSingleton;
    ArrayList<Song> songs;
    Song holder;
   ImageButton addButton;
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

        //Add Button
        addButton = (ImageButton)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(insidePlaylist.this, AddSong.class);
                insidePlaylist.this.startActivityForResult(i, ADD_SONG_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == ADD_SONG_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                ListView listView = (ListView)findViewById(R.id.songList);
                ((SongAdapter)listView.getAdapter()).notifyDataSetChanged();
            }
        }
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
