package itp341.compestine.vinson.playlisthero;

/**
 * Created by vcomp_000 on 11/15/2015.
 */
public class parseSong {

    private String objectID;
    private String songID;
    private String name;

        parseSong(String objectID, String songID, String name){
            this.objectID = objectID;
            this.songID = songID;
            this.name = name;
        }

    public String getObjectID(){
        return objectID;
    }

    public String getSongID(){
        return songID;
    }

    public String getName(){
        return name;
    }

    public void setObjectID(String objectID){
        this.objectID = objectID;
    }

    public void setSongID(String songID){
        this.songID = songID;
    }

    public void setName(String Name){
        this.name = name;
    }


}
