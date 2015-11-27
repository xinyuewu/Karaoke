package uk.ac.dundee.computing.aec.karaoke.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import uk.ac.dundee.computing.aec.karaoke.stores.Track;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;
import uk.ac.dundee.computing.aec.karaoke.lib.Convertors;

public class MusicModel {

    Cluster cluster;

    //constructor
    public void MusicModel() {
    }//end constructor

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }//end setCluster()

    public void insertTrack(byte[] b, String type, String name, String user, String part) {
        try {
            Convertors convertor = new Convertors();
            String types[] = Convertors.SplitFiletype(type);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int length = b.length;
            java.util.UUID picid = convertor.getTimeUUID();
            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            Boolean success = (new File("/var/tmp/instagrim/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/instagrim/" + picid));
            output.write(b);
            Session session = cluster.connect("spotify");
            PreparedStatement psInsertTrack = session.prepare("insert into Tracks ( trackID, track, user, interaction_time, trackLength, type, name) values(?,?,?,?,?,?,?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertTrack);
            Date DateAdded = new Date();
            session.execute(bsInsertPic.bind(picid, buffer, user, DateAdded, length, type, name));
            session.close();
        }//end try
        catch (IOException ex) {
            System.out.println("Error --> " + ex);
        }//end catch
    }//end insertTrack()

    public LinkedList<Track> getTrackList() {
        LinkedList<Track>tracks = new LinkedList<>();
        Session session = cluster.connect("spotify");
        PreparedStatement getTracks = session.prepare("SELECT trackID, name from Tracks");
        BoundStatement bsInsertPic = new BoundStatement(getTracks);
        Date DateAdded = new Date();
        ResultSet rs = null;
        rs = session.execute(bsInsertPic);
        session.close();
        if (!rs.isExhausted()) {
             for (Row row : rs) {
               Track track = new Track();
               UUID UUID = row.getUUID("trackID");
               track.setUUID(UUID);
               track.setName(row.getString("name"));
               tracks.add(track);
            }//end foreach
        }//end if
        else 
            return null;
        return tracks;
    }
    
    public Track getTrack(UUID trackID){
        Session session = cluster.connect("spotify");
        PreparedStatement getTrack = session.prepare("SELECT track, trackLength, name, type from Tracks where trackID = ?");
        BoundStatement bsInsertPic = new BoundStatement(getTrack);
        ResultSet rs = null;
        rs = session.execute(bsInsertPic.bind(trackID));
        session.close();
        
        if (!rs.isExhausted()) {
             Track track = new Track();
             for (Row row : rs) {
               track.setTrack(row.getBytes("track"), row.getInt("trackLength"), row.getString("type"));
               track.setName(row.getString("name"));
               track.setUUID(trackID);
            }//end foreach
            return track;
        }//end if
        else 
            return null;
    }//end getTrack()
}//end class
