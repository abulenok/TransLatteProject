package sample;

import java.sql.Connection;
import java.sql.SQLException;

public class AddWordPairClass {
    private DatabaseHandlerOwnDictionary dbhandler = DatabaseHandlerOwnDictionarySingleton.getInstance();
    private Connection conn = null;

    public void addWordsPairToPersonalDictionary(String leftLan, String rightLan, String txFieldAddWordLeft,
                                                 String txFieldAddWordRight) throws SQLException, ClassNotFoundException {
        dbhandler.checkDBIfExistsandCreate();
        conn = dbhandler.getDbConnection();
        if(dbhandler.checkTableExistence(conn, "UID")==false){
            dbhandler.createUIDTable(conn);
        }
        if(dbhandler.checkTableExistence(conn, leftLan)==false){
            dbhandler.createTableSpecificLan(conn, leftLan);
        }
        if(dbhandler.checkTableExistence(conn, rightLan)==false){
            dbhandler.createTableSpecificLan(conn, rightLan);
        }
        if(!leftLan.equals(rightLan)){
            insertWordPair(leftLan, rightLan, txFieldAddWordLeft, txFieldAddWordRight);
        }

        conn.close();
    }
    private void insertWordPair(String leftLan, String rightLan,
                                String leftWord, String rightWord) throws SQLException, ClassNotFoundException {
        int uidl, uidr, uid;
        uidl = dbhandler.findUIDforAWord(leftLan, leftWord);
        uidr = dbhandler.findUIDforAWord(rightLan, rightWord);
        if(uidl == -1 && uidr == -1){
            uid = dbhandler.findUIDfromUIDTable();
            dbhandler.insertWord(leftLan, leftWord, uid);
            dbhandler.insertWord(rightLan, rightWord, uid);
        }
        else if(uidl == -1 && uidr != -1){
            uid = uidr;
            dbhandler.insertWord(leftLan, leftWord, uid);
        }
        else if(uidl != -1 && uidr == -1){
            uid = uidl;
            dbhandler.insertWord(rightLan, rightWord, uid);
        }
    }
}
