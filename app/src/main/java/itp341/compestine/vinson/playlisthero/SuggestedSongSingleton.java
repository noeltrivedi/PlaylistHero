package itp341.compestine.vinson.playlisthero;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class SuggestedSongSingleton {
    private static SuggestedSongSingleton ourInstance = new SuggestedSongSingleton();
    private static ArrayList<SuggestedSong> suggestions = new ArrayList<>();
    private SuggestedSongSingleton() {
    }
    public static SuggestedSongSingleton getInstance() {
        return ourInstance;
    }

    public ArrayList<SuggestedSong> getSuggestions(){
        return suggestions;
    }

    public int getSize(){
        return suggestions.size();
    }

    public void newSuggestion(SuggestedSong suggestion){
        suggestions.add(suggestion);
    }

    public void deleteSuggestion(int position){
        suggestions.remove(position);
    }


}
