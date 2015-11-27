package uk.ac.dundee.computing.aec.karaoke.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UserType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import uk.ac.dundee.computing.aec.karaoke.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.karaoke.stores.Person;
import uk.ac.dundee.computing.aec.karaoke.stores.Address;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;

public class UserModel {

    Cluster cluster;

    public UserModel() {
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public boolean RegisterUser(String username, String Password, String firstname, String lastname, String email, String age, String street, String city, String zip) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        Map map = new HashMap();
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("spotify");
        UserType addressType = cluster.getMetadata().getKeyspace("spotify").getUserType("address");
        UDTValue address = addressType.newValue().setString("street", street).setString("city", city).setString("zip", zip);
        map.put("home", address);
        PreparedStatement ps = session.prepare("insert into userprofiles (login,password,first_name,last_name,email,age, addresses) Values(?,?,?,?,?,?,?)");
        Set<String> emails = new HashSet<>();
        emails.add(email);
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(username, EncodedPassword, firstname, lastname, emails, Integer.parseInt(age), map));
        session.close();
        return true;
    }

    public boolean IsValidUser(String username, String Password) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } 
        catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("spotify");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( boundStatement.bind( username));
        if (rs.isExhausted()) {
            System.out.println("User not found");
            return false;
        } else {
            for (Row row : rs) {
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0) 
                    return true;
            }
        }
        session.close();
        return false;
    }

    public Person getUser(String username) {
        ResultSet record = null;
        Session session = cluster.connect("spotify");
        Person p = new Person();
        UDTMapper<Address> mapper = new MappingManager(session).udtMapper(Address.class);
        PreparedStatement ps = session.prepare("select * from userprofiles where login =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        record = session.execute(boundStatement.bind(username));
        if (!record.isExhausted()) {
            for (Row row : record) {
                p.setFirstname(row.getString("first_name"));
                p.setLastname(row.getString("last_name"));
                p.setUsername(username);
                p.setAge(row.getInt("age"));
                p.setEmail(row.getSet("email", String.class));
                Map<String, UDTValue> addresses = row.getMap("addresses", String.class, UDTValue.class);
                Address address = mapper.fromUDT(addresses.get("home"));
                p.setAddress(address);
            }
        }
        session.close();
        return p;
    }

    public void updateUser(String username, String firstname, String lastname, int age, String email, String telephone, String martial_status, String street, String city, String zip) {
        Session session = cluster.connect("spotify");
        Set<String> emails = new HashSet<>();
        emails.add(email);
        Map map = new HashMap();
        UserType addressType = cluster.getMetadata().getKeyspace("spotify").getUserType("address");
        UDTValue address = addressType.newValue().setString("street", street).setString("city", city).setString("zip", zip);
        map.put("home", address);
        PreparedStatement ps = session.prepare("update userprofiles  set first_name = ?,last_name = ?,email = ?,age = ?, telephone= ?, addresses = ? where login = ?");
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind( firstname, lastname, emails, age, telephone, map, username));
    }

    public java.util.LinkedList<Person> getUsers() {
        Session session = cluster.connect("spotify");
        java.util.LinkedList<Person> Persons = new java.util.LinkedList<>();
        ResultSet result = session.execute("select login, first_name, last_name from userprofiles");
        if (!result.isExhausted()) {
            for (Row row : result) {
                Person person = new Person();
                person.setFirstname(row.getString("first_name"));
                person.setLastname(row.getString("last_name"));
                person.setUsername(row.getString("login"));
                Persons.add(person);
            }
        }
        session.close();
        return Persons;
    }

    public boolean userExist(String username) {
        Session session = cluster.connect("spotify");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind(username));
        return !rs.isExhausted();
    }

    public String changePassword(String username, String Password) {

        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return "unabe to encrypt password";
        }
        try {
            Session session = cluster.connect("spotify");
            PreparedStatement ps = session.prepare("update userprofiles  set password = ? where login = ?");
            BoundStatement boundStatement = new BoundStatement(ps);
            session.execute(boundStatement.bind(EncodedPassword, username));
            return "Updated Successfully";
        } catch (QueryExecutionException e) {
            return "Update failed";
        }
    }
}
