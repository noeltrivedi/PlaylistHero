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
public class SongAdapter extends ArrayAdapter<Song> {
    Song song;

    public SongAdapter(Context context, ArrayList<Song> songs ){super (context, 0, songs);}
    public View getView(int position, View convertView, ViewGroup parent){
        song = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_song, parent, false);

        }

        TextView tvName = (TextView)convertView.findViewById(R.id.songName);
        TextView tvArtist = (TextView)convertView.findViewById(R.id.artistName);
        ImageView tvAlbum = (ImageView)convertView.findViewById(R.id.songImage);
        TextView tvVotes = (TextView)convertView.findViewById(R.id.votes);

        tvName.setText(song.getName());
        tvArtist.setTag(song.getArtist());
        tvAlbum.setImageDrawable(song.getSongArt());
        tvVotes.setText(song.getVotes());

        ImageButton upVote = (ImageButton)convertView.findViewById(R.id.upVote);
        ImageButton downVote = (ImageButton)convertView.findViewById(R.id.downVote);

        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.upVote();
            }
        });

        downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.downVote();
            }
        });


        return convertView;
    }



}
