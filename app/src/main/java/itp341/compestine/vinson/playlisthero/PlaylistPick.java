package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PlaylistPick extends Activity {


    PlaylistsSingleton playListSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_pick);

        SpotifyApi api = new SpotifyApi();

        SpotifyService spotify = api.getService();
/*
        String searchString = "wagon+wheel";
        spotify.searchTracks(searchString, new SpotifyCallback<TracksPager>(){
            @Override
            public void success(TracksPager tracksPager, Response response)
            {
                //String toast = response.toString();
                for(Track t : tracksPager.tracks.items) {
                    String toast = "Track found: " + t.name + " by " + t.artists.toString();
                    Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(SpotifyError spotifyError) {
                String toast = "Track not found";
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
            }
        });
  */
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
