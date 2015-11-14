package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(Context context, ArrayList<Playlist>playlists){super (context, 0, playlists);}

    public View getView(int position, View convertView, ViewGroup parent){
        Playlist  playlist = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_playlist, parent, false);
        }

        TextView tvPlaylistName = (TextView) convertView.findViewById(R.id.playlistName);
        TextView tvNumListeners = (TextView)convertView.findViewById(R.id.numListeners);
        ImageView tvSongImage = (ImageView)convertView.findViewById(R.id.playlistPic);

        tvPlaylistName.setText(playlist.getPlaylistTitle());
        tvNumListeners.setText(playlist.getNumListeners());
        tvSongImage.setImageDrawable(playlist.getSongImage());

        return convertView;
    }


}
