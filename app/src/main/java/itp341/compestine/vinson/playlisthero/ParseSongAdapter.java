package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/15/2015.
 */
public class ParseSongAdapter extends ArrayAdapter<parseSong>{
    parseSong parseSong;

    public ParseSongAdapter(Context context, ArrayList<parseSong>parseSongs){super(context, 0, parseSongs);}

    public View getView(int position, View convertView, ViewGroup parent){


        return convertView;
    }

}
