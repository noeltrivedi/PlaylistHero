package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Noel on 11/14/2015.
 */
public class SearchedTrackAdapter extends ArrayAdapter<Song> {
    Song song;
    public SearchedTrackAdapter(Context context, ArrayList<Song> songs ){super (context, 0, songs);}

    public View getView(int position, View convertView, ViewGroup parent){
        song = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_searched_track, parent, false);

        }


        TextView tvName = (TextView)convertView.findViewById(R.id.searchedTrackName);
        TextView tvArtist = (TextView)convertView.findViewById(R.id.searchedTrackArtist);
        ImageView tvAlbum = (ImageView)convertView.findViewById(R.id.searchedTrackAlbum);

        tvName.setText(song.getName());
        tvArtist.setText(song.getArtist());
        tvAlbum.setImageDrawable(song.getSongArt());

        this.notifyDataSetChanged();
        ((ListView)parent).invalidateViews();


        return convertView;
    }
}
