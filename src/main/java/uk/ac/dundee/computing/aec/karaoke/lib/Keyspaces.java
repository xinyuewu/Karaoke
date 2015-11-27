package uk.ac.dundee.computing.aec.karaoke.lib;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {
    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            String createkeyspace = "create keyspace if not exists spotify  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";

            String CreateAddressType = "CREATE TYPE if not exists spotify.address (\n"
                    + "      street text,\n"
                    + "      city text,\n"
                    + "      zip varchar\n"
                    + "  );";
            String CreateUserProfile = "CREATE TABLE if not exists spotify.userprofiles (\n"
                    + "      login text PRIMARY KEY,\n"
                    + "     password text,\n"
                    + "      age int,\n"
                    + "      first_name text,\n"
                    + "      last_name text,\n"
                    + "      email set<text>,\n"
                    + "      addresses  map<text, frozen <spotify.address>>\n"
                    + "  );";
             String CreateTrackTable = "CREATE TABLE if not exists spotify.Tracks ("
                    + " user varchar,"
                    + " trackID uuid, "
                    + " interaction_time timestamp,"
                    + " track blob,"
                    + " trackLength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (trackID)"
                    + ")";

            Session session = c.connect();
            System.out.println("Creating keyspace ");
            try {
                PreparedStatement statement = session
                        .prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(
                        statement);
                ResultSet rs = session
                        .execute(boundStatement);
                System.out.println("Created keyspace ");
            } catch (Exception et) {
                System.out.println("Can't create keyspace " + et);
            }

            System.out.println("Creating address table");
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateAddressType);
                session.execute(cqlQuery);
                System.out.println("Created address table ");
            } catch (Exception et) {
                System.out.println("Can't create address table" + et);
            }
            System.out.println("Creating userprofiles table");
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
                System.out.println("Created userprofiles table");
            } catch (Exception et) {
                System.out.println("Can't create userprofiles table" + et);
            }
            System.out.println("Creating tracks table");
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateTrackTable);
                session.execute(cqlQuery);
                System.out.println("Created tracks table");
            } catch (Exception et) {
                System.out.println("Can't create tracks table" + et);
            }
            session.close();
        } catch (Exception et) {
            System.out.println("Other keyspace or column definition error" + et);
        }
    }
}
