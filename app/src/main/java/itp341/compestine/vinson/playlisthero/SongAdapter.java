package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class SongAdapter extends ArrayAdapter<Song> {
    Song song;
    boolean upclick = false;
    boolean downclick = false;
    ImageView upVote;
    ImageView downVote;


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
        tvArtist.setText(song.getArtist());
        tvAlbum.setImageDrawable(song.getSongArt());
        tvVotes.setText(song.getVotes());


        upVote = (ImageView)convertView.findViewById(R.id.upVote);
        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tr = (TableRow)v.getParent().getParent();
                ListView lv = (ListView)tr.getParent();
                int position = lv.getPositionForView(tr);
                song = getItem(position);
                //Checks if user has already downvoted
                Log.i("SongVote", song.getName() + " has " + song.getVotes() + " (" + upclick + "/" + downclick + ")");
                if (downclick) {
                    downclick = false;
                    song.upVote();
                }
                if (!upclick) {
                    upclick = true;
                    song.upVote();
                } else
                {
                    upclick = false;
                    song.downVote();
                }
                tvVotes.setText(song.getVotes());
                Log.i("SongVote", song.getName() + " has " + song.getVotes() + " (" + upclick + "/" + downclick + ")");
                sort();
                Utils.updateVotesParse(song);
            }
        });

        downVote = (ImageView)convertView.findViewById(R.id.downVote);
        downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tr = (TableRow)v.getParent().getParent();
                ListView lv = (ListView)tr.getParent();
                int position = lv.getPositionForView(tr);
                song = getItem(position);

                if(upclick)
                {
                    upclick = false;
                    song.downVote();
                }
                if(!downclick)
                {
                    downclick = true;
                    song.downVote();
                }
                else
                {
                    downclick = false;
                    song.upVote();
                }
                tvVotes.setText(song.getVotes());
                sort();
                Utils.updateVotesParse(song);
            }
        });

        return convertView;
    }

    private void sort()
    {
        Collections.sort(SongSingleton.getInstance().getSongs(), new Comparator<Song>() {
            @Override
            public int compare(Song lhs, Song rhs) {
                return -1 * Integer.compare(lhs.getVotesInt(), rhs.getVotesInt());
            }
        });
    }

}
