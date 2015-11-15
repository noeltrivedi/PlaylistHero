package itp341.compestine.vinson.playlisthero;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class SuggestedSongAdapter extends ArrayAdapter<Song>{

    Song suggestedSong;
    SongSingleton accepted;
    ArrayList<Song> queue;
    SuggestedSongSingleton suggested;
    ArrayList<Song> suggestions;
    int index;

    public SuggestedSongAdapter(Activity activity, ArrayList<Song> suggestions){
        super(activity, 0, suggestions);
    }

    public View getView(int position, View convertView, ViewGroup parent){
       //The song that has been selected
        suggestedSong = getItem(position);
        //The songs that have been accepted
        accepted= SongSingleton.getInstance();
        queue = accepted.getSongs();
        //List of songs that have been suggested
        suggested = SuggestedSongSingleton.getInstance();
        suggestions = suggested.getSuggestions();
        index = position;


    if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_suggested_song, parent, false);
    }

        TextView tvName = (TextView) convertView.findViewById(R.id.suggestedSongName);
        TextView tvScore = (TextView) convertView.findViewById(R.id.suggestedSongScore);
        ImageButton tvAlbum = (ImageButton)convertView.findViewById(R.id.suggestedAlbumArt);

        tvName.setText(suggestedSong.getName());
        tvScore.setText(suggestedSong.getVotes());
        tvAlbum.setImageDrawable(suggestedSong.getSongArt());

        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast message = Toast.makeText(getContext(), "Added to Queue", Toast.LENGTH_SHORT);
                message.show();
                queue.add(suggestedSong);
                suggestions.remove(index);

            }
        });




        return convertView;
    }

}
