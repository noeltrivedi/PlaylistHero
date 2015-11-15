package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DJActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {
    SongSingleton suggestions;
    ArrayList<Song> suggestedSongs;
    SongSingleton accepted;
    ArrayList<Song> acceptedSongs;
    ListView queue;
    ListView pending;

    static private Player player= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);

        initializePlayer();
        //Current playlist
        accepted = SongSingleton.getInstance();
        acceptedSongs = accepted.getSongs();
        final SongAdapter acceptedAdapter = new SongAdapter(this, acceptedSongs);
        queue = (ListView)findViewById(R.id.currentSongs);
        queue.setAdapter(acceptedAdapter);


        //For Pending Queue
        suggestions = SongSingleton.getInstance();
        suggestedSongs= suggestions.getSongs();
        final SongAdapter pendingAdapter = new SongAdapter(this, suggestedSongs);
        pending = (ListView)findViewById(R.id.suggestedSongs);
        pending.setAdapter(pendingAdapter);

        for(int i = 0; i < 10; i ++){
            Song test = new Song("40 Miles", "Yonder Mountain", getResources().getDrawable(R.drawable.yonder), 100, "34");
            acceptedSongs.add(test);
        }

        pending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song toAdd = suggestedSongs.get(position);
                suggestedSongs.remove(position);
                pendingAdapter.notifyDataSetChanged();
                acceptedSongs.add(toAdd);
                acceptedAdapter.notifyDataSetChanged();
            }
        });
    }

    public static void playSong(String trackID)
    {
        player.play("spotify:track:" + trackID);
    }

    private void initializePlayer()
    {
        Config playerConfig = new Config(this, Utils.getAuthToken(), Utils.client_id);
        player = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver(){

            @Override
            public void onInitialized(Player player) {
                player.addConnectionStateCallback(DJActivity.this);
                player.addPlayerNotificationCallback(DJActivity.this);
                player.play("spotify:track:1JO1xLtVc8mWhIoE3YaCL0");
            }

            @Override
            public void onError(Throwable throwable) {

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

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Throwable throwable) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {

    }
}
