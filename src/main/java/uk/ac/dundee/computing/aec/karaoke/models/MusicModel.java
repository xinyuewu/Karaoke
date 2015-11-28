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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import uk.ac.dundee.computing.aec.karaoke.lib.Convertors;
import uk.ac.dundee.computing.aec.karaoke.stores.Likes;

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
        LinkedList<Track> tracks = new LinkedList<>();
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
        else {
            return null;
        }
        return tracks;
    }

    public LinkedList<Track> getTopTracks() {
        LinkedList<Track> tracks = new LinkedList<>();
        Session session = cluster.connect("spotify");

        PreparedStatement getTracks = session.prepare("SELECT trackID, total from Likes");
        BoundStatement bsInsertPic = new BoundStatement(getTracks);
        session.execute(bsInsertPic);
        return tracks;
    }

    public Track getTrack(UUID trackID) {
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
        else {
            return null;
        }
    }//end getTrack()

    public Likes insertLike(UUID trackID, String username, String name) {
        Session session = cluster.connect("spotify");
        Likes l = getLikes(trackID);
        PreparedStatement ps;
        BoundStatement bs;
        if (l == null) {
            //do insert
            ps = session.prepare("Insert into Likes (user, trackID, name, total) VALUES (? ,?, ?, ?)");
            Set<String> users = new HashSet<>();
            users.add(username);
            bs = new BoundStatement(ps);
            session.execute(bs.bind(users, trackID, name, users.size()));
            return getLikes(trackID);
        }
        else{
            Set<String> us = new HashSet();
            for(String s : l.getLikedUsers()){
                us.add(s);
                if(s.equals(username))
                    return l;
            }
            us.add(username);
            ps=session.prepare("update Likes set user = ?, total = ? where trackID = ?");
            bs = new BoundStatement(ps);
            session.execute(bs.bind(us, us.size(), trackID));
            return getLikes(trackID);
        }
    }

    public Likes getLikes(UUID trackID) {
        Session session = cluster.connect("spotify");
        Likes l = null;
        ResultSet rs;
        PreparedStatement getLikes = session.prepare("SELECT * from Likes where trackID = ?");
        BoundStatement bs = new BoundStatement(getLikes);
        rs = session.execute(bs.bind(trackID));
        if (!rs.isExhausted()) {
            l = new Likes();
            for (Row row : rs) {
                l.setName(row.getString("name"));
                l.setLikedUsers(row.getSet("user", String.class));
                l.setTotalLikes(row.getInt("total"));
            }
            return l;
        } else {
            return l;
        }
    }
}//end class
