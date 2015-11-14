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
import android.widget.ListView;

import java.util.ArrayList;

public class PlaylistPick extends Activity {


    PlaylistsSingleton playListSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_pick);

        playListSingleton = PlaylistsSingleton.getInstance();

        ArrayList<Playlist> playlists = playListSingleton.getList();
        PlaylistAdapter adapter = new PlaylistAdapter(this, playlists);

        ListView listView = (ListView) findViewById(R.id.currentPlaylists);
        listView.setAdapter(adapter);

        //Test ListView
        Drawable drawable = getResources().getDrawable(R.drawable.we_be_jammin_400x400);
        Playlist test = new Playlist(drawable, "Jam Sesh", "900");
        playListSingleton.newPlaylist(test);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(PlaylistPick.this, insidePlaylist.class);
                i.putExtra("songIndex", position);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playlist_pick, menu);
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
