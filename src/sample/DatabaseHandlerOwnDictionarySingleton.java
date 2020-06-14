package sample;

public class DatabaseHandlerOwnDictionarySingleton {

    private static DatabaseHandlerOwnDictionary uniqueInstance;

    private DatabaseHandlerOwnDictionarySingleton(){

    }
    public static DatabaseHandlerOwnDictionary getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DatabaseHandlerOwnDictionary();
        }
        return uniqueInstance;
    }


}
