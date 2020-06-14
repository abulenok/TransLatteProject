package sample;

import java.sql.SQLException;
import java.util.Vector;

public class RepeatWordsEngineClass {

    private DatabaseHandlerOwnDictionary dbhandler = DatabaseHandlerOwnDictionarySingleton.getInstance();
    private Vector<String> wordsSetStartLang;
    private String langUserTransFrom;
    private String langUserTransTo;

    public RepeatWordsEngineClass(String lStartFrom, String lStartTo) throws SQLException, ClassNotFoundException {
        langUserTransFrom = lStartFrom;
        langUserTransTo = lStartTo;
        dbhandler.getDbConnection();
        dbhandler.checkDBIfExistsandCreate();
    }

    public boolean userAnswerIsCorrect(String wordToCheck){
        String wordsSetToCheckIn;
        try {
            wordsSetToCheckIn = dbhandler.getWordsSetToCheck(langUserTransTo,
                    wordsSetStartLang.elementAt(1)).toString();
            return wordsSetToCheckIn.contains(wordToCheck);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public void removeCheckedWordFromWordsSetStartLang(){
        //removes Word
        wordsSetStartLang.remove(0);
        //removes UID
        wordsSetStartLang.remove(0);
    }

    public String wordUserHasToGiveTranslationTo(){
        return wordsSetStartLang.elementAt(0);
    }

    public boolean wordsSetUserTranslFromIsEmpty(){
        return wordsSetStartLang.size()==0;
    }
    public void createWordsSetOfStartLanguage() throws SQLException, ClassNotFoundException {

        wordsSetStartLang = dbhandler.getWordsSetStartLang(langUserTransFrom, 10);

        deleteWordsWithoutTranslInChosenLang(langUserTransTo);
    }

    private void deleteWordsWithoutTranslInChosenLang(String langUserWritesTranslationTo) throws SQLException, ClassNotFoundException {
        int j = 0;
        while( j<wordsSetStartLang.size()){

            if(dbhandler.getWordsSetToCheck(langUserWritesTranslationTo,
                    wordsSetStartLang.elementAt(j + 1)).toString().length() == 2){

                wordsSetStartLang.remove(j);
                wordsSetStartLang.remove(j);
            }
            else{
                j+=2;
            }
        }
    }
}
