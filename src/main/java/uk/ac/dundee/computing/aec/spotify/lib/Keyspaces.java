package uk.ac.dundee.computing.aec.spotify.lib;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            //Add some keyspaces here
            //String Dropkeyspace = "drop keyspace if exists instagrim_150020690;"; 
            
            String createkeyspace = "create keyspace if not exists spotify  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            
            String CreateAddressType = "CREATE TYPE if not exists spotify.address (\n"
                    + "      street text,\n"
                    + "      city text,\n"
                    + "      zip int\n"
                    + "  );";
            String CreateUserProfile = "CREATE TABLE if not exists spotify.userprofiles (\n"
                    + "      login text PRIMARY KEY,\n"
                     + "     password text,\n"
                    + "      age int,\n"
                    + "      martial_status text,\n"
                    + "      telephone text,\n"
                    + "      first_name text,\n"
                    + "      last_name text,\n"
                    + "      email set<text>,\n"
                    + "      addresses  map<text, frozen <spotify.address>>\n"
                    + "  );";
            
            
            
            
            Session session = c.connect();
            /*try {
                SimpleStatement cqlQuery = new SimpleStatement(Dropkeyspace);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }*/
            try {
                PreparedStatement statement = session
                        .prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(
                        statement);
                ResultSet rs = session
                        .execute(boundStatement);
                System.out.println("created instagrim ");
            } catch (Exception et) {
                System.out.println("Can't create instagrim " + et);
            }

            //now add some column families 
                        System.out.println("" + CreateAddressType);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateAddressType);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address type " + et);
            }
            System.out.println("" + CreateUserProfile);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address Profile " + et);
            }
            /******************************************/
            
            session.close();

        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }

    }
}
