package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class SuggestedSongAdapter extends ArrayAdapter<SuggestedSong>{

    SuggestedSong suggestedSong;

    public SuggestedSongAdapter(Context context, ArrayList<SuggestedSong> suggestions){
        super(context, 0, suggestions);
    }

    public View getView(int position, View convertView, ViewGroup parent){
       suggestedSong = getItem(position);
    if(convertView == null){

    }


        return convertView;
    }

}
