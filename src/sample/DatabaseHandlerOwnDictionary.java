package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DatabaseHandlerOwnDictionary extends DatabaseHandler {

    public void createTableSpecificLan(Connection conn, String tableName)throws ClassNotFoundException, SQLException {
        Statement stmt = conn.createStatement();
        String query = "CREATE TABLE " + tableName + "(ID int NOT NULL AUTO_INCREMENT, Word varchar(255) NOT NULL, UID int NOT NULL, PRIMARY KEY (ID));";
        stmt.executeUpdate(query);
        stmt.close();
    }
    public void createUIDTable(Connection conn)throws ClassNotFoundException, SQLException{
        Statement stmt = conn.createStatement();
        String query = "CREATE TABLE UID (ID int NOT NULL AUTO_INCREMENT, uidtype varchar(10) NOT NULL, UID int NOT NULL, PRIMARY KEY (ID));";
        stmt.executeUpdate(query);
        query = "INSERT INTO UID (uidtype, UID) VALUES ('actual', 100);";
        stmt.executeUpdate(query);
        stmt.close();
    }
    public Vector<String> getWordsSetToCheck(String lan, String uid) throws SQLException, ClassNotFoundException {
        Statement stmt = dbConnection.createStatement();
        Vector<String> resultVect = new Vector<>();
        String query = null;
        if(checkTableExistence(dbConnection, lan)) {
            query = "SELECT * FROM " + lan + " WHERE UID = '" + uid + "';";

            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                resultVect.add(rs.getString(2));
            }
            rs.close();
        }
        stmt.close();

        return resultVect;
    }


    //adds pair of (word + uid) to a Vector, ex.: (Vector[0] = "cat", Vector[1] = "100")
    //so Vector size will be even
    public Vector<String> getWordsSetStartLang(String lan, int n) throws SQLException, ClassNotFoundException {
        Statement stmt = dbConnection.createStatement();
        Vector<String> resultVect = new Vector<>();
        if(checkTableExistence(dbConnection, lan)) {
            String query = "SELECT * FROM "+ lan + ";";
            ResultSet rs = stmt.executeQuery(query);
            for(int i = 0; i < n; i++) {
                if (rs.next()) {
                    resultVect.add(rs.getString(2));
                    resultVect.add(rs.getString(3));
                }
            }
            rs.close();
        }
        stmt.close();

        return resultVect;
    }

    public void insertWord(String lan, String word, int uid)throws SQLException{
        Statement stmt = dbConnection.createStatement();
        String query = "INSERT INTO " + lan + " (Word, UID) VALUES ('" + word + "', " + uid + ");";
        stmt.executeUpdate(query);
        stmt.close();
    }

    public int findUIDforAWord(String lan, String word)throws ClassNotFoundException, SQLException{
        Statement stmt = dbConnection.createStatement();
        String query = "SELECT UID FROM " + lan + " WHERE Word = '" + word + "'";
        ResultSet rs = stmt.executeQuery(query);
        int uid = -1;
        if(rs.next()){
            if(rs.getInt(1)!= 0){
                uid = rs.getInt(1);
            }
        }
        stmt.close();
        rs.close();
        return uid;
    }
    public int findUIDfromUIDTable() throws SQLException {
        int uid = 0;
        Statement stmt = dbConnection.createStatement();
        String query;
        query = "SELECT UID FROM uid WHERE uidtype = 'actual'";
        ResultSet rs = stmt.executeQuery(query);

        if(rs.next())
            uid = rs.getInt(1);
        int uidQuery = uid+1;
        query = "UPDATE uid SET uidtype = 'actual', UID = " + uidQuery +" WHERE ID = 1";

        stmt.executeUpdate(query);
        stmt.close();
        rs.close();
        return uid;
    }

}
