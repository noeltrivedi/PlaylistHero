package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;

public class PlaylistPick extends Activity {
    private static final int SPOTIFY_REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "crowdj://callback";

    PlaylistsSingleton playListSingleton;
    ImageButton DJ;

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
                Intent i = new Intent(PlaylistPick.this, InsidePlaylist.class);
                i.putExtra("songIndex", position);
                startActivity(i);
            }
        });

        DJ = (ImageButton)findViewById(R.id.DJButton);
        DJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent i = new Intent(PlaylistPick.this, DJActivity.class);
                startActivity(i);
                */
                AuthenticationRequest.Builder builder =
                        new AuthenticationRequest.Builder(Utils.client_id, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

                builder.setScopes(new String[]{"streaming", "playlist-read-private", "playlist-read-collaborative"});
                AuthenticationRequest request = builder.build();

                AuthenticationClient.openLoginActivity(PlaylistPick.this, SPOTIFY_REQUEST_CODE, request);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SPOTIFY_REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            switch(response.getType().toString())
            {
                case "token":
                    String token = response.getAccessToken();
                    Utils.setAuthToken(token);
                    Intent i = new Intent(PlaylistPick.this, DJActivity.class);
                    startActivity(i);
                    break;
                default:
                    Log.e("ERROR", "Error logging in to spotify");
                    Toast.makeText(this, "You need Spotify Premium to use DJ mode", Toast.LENGTH_LONG).show();
            }
        }
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
