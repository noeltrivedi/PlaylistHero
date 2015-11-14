package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class SongAdapter extends ArrayAdapter<Song> {
    Song song;
    int upclick =0;
    int downclick = 0;

    public SongAdapter(Context context, ArrayList<Song> songs ){super (context, 0, songs);}
    public View getView(int position, View convertView, ViewGroup parent){
        song = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_song, parent, false);

        }

        TextView tvName = (TextView)convertView.findViewById(R.id.songName);
        TextView tvArtist = (TextView)convertView.findViewById(R.id.artistName);
        ImageView tvAlbum = (ImageView)convertView.findViewById(R.id.songImage);
        final TextView tvVotes = (TextView)convertView.findViewById(R.id.votes);

        tvName.setText(song.getName());
        tvArtist.setTag(song.getArtist());
        tvAlbum.setImageDrawable(song.getSongArt());
        tvVotes.setText(song.getVotes());

        ImageView upVote = (ImageView)convertView.findViewById(R.id.upVote);
        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks if user has already downvoted
                if(downclick != 0 && upclick == 0){
                    song.upVote();
                    upclick =0;
                    downclick = 0;
                }

               else if(upclick  == 0){
                    song.upVote();
                    upclick+=1;
                }
                tvVotes.setText(song.getVotes());
            }
        });

        ImageView downVote = (ImageView)convertView.findViewById(R.id.downVote);
        downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(upclick != 0 && downclick == 0){
                    song.downVote();
                    downclick =0;
                    upclick = 0;
                }

                else if (downclick == 0){
                    song.downVote();
                    downclick +=1;
                }
                tvVotes.setText(song.getVotes());
            }
        });

        return convertView;
    }



}
