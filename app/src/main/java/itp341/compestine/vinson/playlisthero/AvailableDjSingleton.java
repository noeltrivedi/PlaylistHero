package itp341.compestine.vinson.playlisthero;

import java.util.ArrayList;

/**
 * Created by vcomp_000 on 11/14/2015.
 */
public class AvailableDjSingleton {
    private static AvailableDjSingleton singleton = new AvailableDjSingleton();
    private static ArrayList<User> users = new ArrayList<User>();
    private AvailableDjSingleton() {
    }

    public static AvailableDjSingleton getInstance() {
        return singleton;
    }

    public ArrayList<User> getList(){
        return users;
    }

    public int getSize(){
        return users.size();
    }

    public void newPlaylist(User addUser){
        users.add(addUser);
    }
    public void deletePlaylist(int position){
        users.remove(position);
    }



}
