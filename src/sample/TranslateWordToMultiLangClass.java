package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Vector;

public class TranslateWordToMultiLangClass {
    private Vector<String> languages;
    private String langFrom;

    @FXML
    private TextField transResult1;

    @FXML
    private TextField transResult2;

    @FXML
    private TextField transResult4;

    @FXML
    private TextField transResult3;

    public TranslateWordToMultiLangClass(Vector<String> langs, String lFrom,
                                         TextField transRes1, TextField transRes2, TextField transRes3, TextField transRes4){
        languages = langs;
        langFrom = lFrom;
        transResult1 = transRes1;
        transResult2 = transRes2;
        transResult3 = transRes3;
        transResult4 = transRes4;
    }

    private String translateWord(String wordToTranslate, String langTo) {
        Vector<String> translatedWords;
        try {
            translatedWords =  HttpRequestTranslation.translateHttp(wordToTranslate, langFrom, langTo);
            String translatedWordsString = translatedWords.toString();
            return translatedWordsString.substring(1, translatedWordsString.length()-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error: Probably, word not found";
    }

    public void translateWordMultipleLan(String wordToTranslate){
        Runnable task = new Runnable()
        {
            Vector<String> temp = new Vector<>();
            public void run()
            {
                if (languages.size() > 0) {
                    temp.add(translateWord(wordToTranslate, languages.elementAt(0)));

                    if (languages.size() > 1) {
                        temp.add(translateWord(wordToTranslate, languages.elementAt(1)));

                        if (languages.size() > 2) {
                            temp.add(translateWord(wordToTranslate, languages.elementAt(2)));

                            if (languages.size() > 3) {
                                temp.add(translateWord(wordToTranslate, languages.elementAt(3)));
                            }
                        }
                    }
                }

                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (languages.size() > 0) {
                            transResult1.setText(temp.elementAt(0));
                            if (languages.size() > 1) {
                                transResult2.setText(temp.elementAt(1));
                                if (languages.size() > 2) {
                                    transResult3.setText(temp.elementAt(2));
                                    if (languages.size() > 3) {
                                        transResult4.setText(temp.elementAt(3));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }
}
