package sample;

import java.sql.*;


//UID - unique ID
//kazde slowo posiada jakis UID, dowolne dwa slowa ktore maja ten sam UID - sa tlumaczeniami samych siebie
//przyklad: UID.cat = UID.kot

//istnieje tabela w ktorej jest zapisany actualnie swobodny UID
//po jego wykorzystaniu UID sie zwieksza o 1

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionStr = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionStr, dbUser, dbPass);

        return dbConnection;
    }

    public boolean checkTableExistence(Connection conn, String tableName)throws SQLException{

        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(TABLE_NAME)  FROM information_schema.tables where TABLE_NAME = '" + tableName +"'";
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()) {
            if (rs.getInt("COUNT(TABLE_NAME)") > 0) {
                stmt.close();
                rs.close();
                return true;
            }
        }
        stmt.close();
        rs.close();
        return false;
    }



    public boolean checkDBExistence(Connection connection)throws ClassNotFoundException, SQLException{
        ResultSet rs = connection.getMetaData().getCatalogs();

        //iterate each catalog in the ResultSet
        while (rs.next()) {
            // Get the database name, which is at position 1
            String databaseName = rs.getString(1);
            if(databaseName.equals(dbName)){
                rs.close();
                return true;
            }
        }
        rs.close();

        return false;
    }
    public void createDB(Statement stmt)throws ClassNotFoundException, SQLException{
        String query = "CREATE DATABASE " + dbName;
        stmt.executeUpdate(query);
    }
    public void checkDBIfExistsandCreate()throws ClassNotFoundException, SQLException{
        String connectionStr = "jdbc:mysql://" + dbHost + ":" + dbPort;

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection(connectionStr, dbUser, dbPass);
        Statement stmt = conn.createStatement();

        if(checkDBExistence(conn) == false){
            //System.out.println("doesn't exist");
            createDB(stmt);

        }
        else
            //System.out.println("already exists");

        if(stmt!=null)
            stmt.close();
        if(conn!=null)
            conn.close();
    }
}
