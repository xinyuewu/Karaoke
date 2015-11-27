package uk.ac.dundee.computing.aec.karaoke.stores;

import com.datastax.driver.core.utils.Bytes;
import java.nio.ByteBuffer;

public class Track {

    private ByteBuffer bTrack = null;
    private int length;
    private String type;
    private java.util.UUID UUID=null;
    private String name;
    
    //constructor
    public void Track(){
    }//end constructor
    
    public void setUUID(java.util.UUID UUID){
        this.UUID =UUID;
    }//end setUUID()
    
    public String getSUUID(){
        return UUID.toString();
    }//end getSUUID()
    
     public void setName(String s){
        this.name =s;
    }//end getName()
    
    public String getName(){
        return name;
    }//end setName()
    
    public void setTrack(ByteBuffer bTrack, int length, String type) {
        this.bTrack = bTrack;
        this.length = length;
        this.type=type;
    }//end setPic()

    public ByteBuffer getBuffer() {
        return bTrack;
    }//end getBuffer()

    public int getLength() {
        return length;
    }//end getLength()
    
    public String getType(){
        return type;
    }//end getType()

    public byte[] getBytes() {       
        byte image[] = Bytes.getArray(bTrack);
        return image;
    }//end getBytes()
}//end class

