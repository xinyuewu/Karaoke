package uk.ac.dundee.computing.aec.karaoke.stores;

import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.mapping.UDTMapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Person {
    private String firstname;
    private String lastname;
    private String username; 
    private String password;
    private int age;
    private Set<String> email;
    private Address address;
    
    public void Person(){
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setEmail(Set<String> email){
        this.email = email;
    }
    public void setAddress(Address address){
        this.address = address;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public String getLastname(){
        return this.lastname;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public int getAge(){
        return this.age;
    }
    public Set<String> getEmail(){
        return this.email;
    }
    public Address getAddress(){
        return this.address;
    }
}
