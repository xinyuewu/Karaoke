package uk.ac.dundee.computing.aec.karaoke.lib;

import com.datastax.driver.core.*;

public final class CassandraStore {

    //constructor
    public CassandraStore() {
    }//end constructor

    public static void SetUpKeySpaces(Cluster c) {
        try {
            //Add some keyspaces here
            String createkeyspace = "create keyspace if not exists project  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists project.Tracks ("
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

            //build the keyspace
            try {
                PreparedStatement statement = session.prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(statement);
                ResultSet rs = session.execute(boundStatement);
            }//end try
            catch (Exception et) {
                System.out.println("Can't create keyspace " + et);
            }//end catch
            //build music table
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                session.execute(cqlQuery);
            }//end try
            catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }//end catch
        }//end try
        catch (Exception et) {
            System.out.println("Other keyspace or column definition error" + et);
        }//end catch     
    }//end SetupKeySpaces()
}//end class
