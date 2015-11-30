package uk.ac.dundee.computing.aec.karaoke.models;

import com.datastax.driver.core.BoundStatement;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import org.apache.commons.lang3.time.DateUtils;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

/**
 *
 * @author clevelandsullivan
 */
public class PlayModel {
    Cluster cluster;
    
     public void PlayModel() {
    }//end constructor
    
    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }//end setCluster()
    
    public void insertPlay(UUID track,String username){
      Session session = cluster.connect("spotify");
      Date timestamp = new Date();
      Date adate = new Date();
      String newdate = new SimpleDateFormat("yyyy-MM-dd").format(adate);
      PreparedStatement psInsertPlay = session.prepare("insert into Played ( trackID, date, user, interaction_time) values(?,?,?,?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertPlay);
            session.execute(bsInsertPic.bind(track, newdate, username, timestamp));
    session.close();
    }
    
    public HashMap getPlayEvents(UUID track){
        ResultSet record =null;
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        Date adate = new Date();
        Date aWeek = DateUtils.addDays(new Date(),-7);
      //String newdate = new SimpleDateFormat("yyyy-MM-dd").format(adate);
        Session session = cluster.connect("spotify");
        PreparedStatement psGetPlay = session.prepare("select trackid, date from Played where trackid = ? and interaction_time > ? and interaction_time <= ?");
            BoundStatement bsInsertPic = new BoundStatement(psGetPlay);
            record = session.execute(bsInsertPic.bind(track,aWeek,adate));
        //Integer i = 0;    
        if (!record.isExhausted()) {
           for (Row row : record) {
               if(hmap.containsKey(row.getUUID("trackid").toString()+"_"+row.getString("date"))){
                   hmap.put(row.getUUID("trackid").toString()+"_"+row.getString("date"), hmap.get(row.getUUID("trackid").toString()+"_"+row.getString("date"))+1);
               }else{
                   hmap.put(row.getUUID("trackid").toString()+"_"+row.getString("date"),1);
               } //i++;
           }
           //hmap.put("records", i);
        }       
        session.close();
        return hmap;
    } 
}
