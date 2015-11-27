package itp341.compestine.vinson.playlisthero;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class DisplayableAdapter<T extends Displayable> extends ArrayAdapter<T>
{
    //Create an ArrayAdapter for a class which implements Displayable
    public DisplayableAdapter(Context context, ArrayList<T> displayables)
    {
        super (context, 0, displayables);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        T displayable = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_displayable, parent, false);
        }

        TextView mainTitle = (TextView) convertView.findViewById(R.id.mainTitle);
        TextView subtitle = (TextView)convertView.findViewById(R.id.subtitle);
        ImageView drawable = (ImageView)convertView.findViewById(R.id.drawable);

        mainTitle.setText(displayable.getTitle());
        subtitle.setText(displayable.getSubtitle());
        drawable.setImageDrawable(displayable.getArt());

        return convertView;
    }


}
