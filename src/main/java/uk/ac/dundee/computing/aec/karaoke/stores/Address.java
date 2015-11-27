package uk.ac.dundee.computing.aec.karaoke.stores;
import com.datastax.driver.mapping.annotations.UDT;
import com.datastax.driver.mapping.annotations.Field;

@UDT(name = "address", keyspace = "spotify")
public class Address {
    public void Address(){
        
    }
    
    @Field(name = "street")
    private String street;
    @Field(name = "city")
    private String city;
    @Field(name = "zip")
    private int zip ;
    
    public void setStreet(String street){
        this.street = street;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setZip(int zip){
        this.zip = zip;
    }
    
    public String getStreet(){
        return this.street;
    }
    public String getCity(){
        return this.city;
    }
    public int getZip(){
        return this.zip;
    }
}
