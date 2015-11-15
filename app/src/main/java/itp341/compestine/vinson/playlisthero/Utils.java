package itp341.compestine.vinson.playlisthero;

import java.util.List;

import kaaes.spotify.webapi.android.models.ArtistSimple;

/**
 * Created by Noel on 11/14/2015.
 */
public class Utils {
    static String formatArtists(List<ArtistSimple> artists)
    {
        if(artists.size() > 0) {
            String formattedArtists = "";
            for(int i = 0; i < artists.size(); i++)
            {
                formattedArtists += artists.get(i).name;
                if(i != artists.size()-1)
                    formattedArtists += ", ";
            }

            return formattedArtists;
        }
        else
        {
            return null;
        }
    }
}
