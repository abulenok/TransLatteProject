package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class RepeatWordsController {
    @FXML
    private Button startTestButton;

    @FXML
    private TextField textFieldLang1;

    @FXML
    private TextField textFieldLang2;

    @FXML
    private Button submitWordPairButton;
    @FXML
    private MenuButton menuButton1;

    @FXML
    private RadioMenuItem radioMenuItem_ar1;

    @FXML
    private ToggleGroup toggleGroupLeft;

    @FXML
    private RadioMenuItem radioMenuItem_de1;

    @FXML
    private RadioMenuItem radioMenuItem_en1;

    @FXML
    private RadioMenuItem radioMenuItem_fr1;

    @FXML
    private RadioMenuItem radioMenuItem_it1;

    @FXML
    private RadioMenuItem radioMenuItem_es1;

    @FXML
    private RadioMenuItem radioMenuItem_pl1;

    @FXML
    private RadioMenuItem radioMenuItem_ru1;

    @FXML
    private Text textCorrectness;

    @FXML
    private MenuButton menuButton21;

    @FXML
    private RadioMenuItem radioMenuItem_ar11;

    @FXML
    private ToggleGroup toggleGroupRight;

    @FXML
    private RadioMenuItem radioMenuItem_de11;

    @FXML
    private RadioMenuItem radioMenuItem_en11;

    @FXML
    private RadioMenuItem radioMenuItem_fr11;

    @FXML
    private RadioMenuItem radioMenuItem_it11;

    @FXML
    private RadioMenuItem radioMenuItem_es11;

    @FXML
    private RadioMenuItem radioMenuItem_pl11;

    @FXML
    private RadioMenuItem radioMenuItem_ru11;

    @FXML
    private Text textLanFrom1;

    @FXML
    private Text textLanTo1;

    @FXML
    void initialize() {

        startTestButton.setOnAction(event->{
            startTestButton.setVisible(false);
            menuButton1.setVisible(false);
            menuButton21.setVisible(false);
            textFieldLang1.setVisible(true);
            textFieldLang2.setVisible(true);
            submitWordPairButton.setVisible(true);

            String langUserTransFrom = getLangToStartFrom();
            String langUserTransTo = getLangToStartTo();

            textLanFrom1.setText(getFullLanName(langUserTransFrom));
            textLanTo1.setText(getFullLanName(langUserTransTo));

            textLanFrom1.setVisible(true);
            textLanTo1.setVisible(true);

            try {
                RepeatWordsEngineClass rwengn = new RepeatWordsEngineClass(langUserTransFrom, langUserTransTo);
                rwengn.createWordsSetOfStartLanguage();

                if(!rwengn.wordsSetUserTranslFromIsEmpty()){
                    textFieldLang1.setText(rwengn.wordUserHasToGiveTranslationTo());
                }
                else{
                    textFieldLang2.setVisible(false);
                    textFieldLang1.setVisible(false);
                    submitWordPairButton.setVisible(false);
                    textCorrectness.setText("No words in "
                            +langUserTransFrom+ " - " +langUserTransTo+" language pair!");
                    textCorrectness.setFill(Color.DARKBLUE);
                    textLanFrom1.setVisible(false);
                    textLanTo1.setVisible(false);
                }

                submitWordPairButton.setOnAction(event1 ->{

                    if(rwengn.wordsSetUserTranslFromIsEmpty()){
                        textFieldLang2.setVisible(false);
                        textFieldLang1.setVisible(false);
                        submitWordPairButton.setVisible(false);
                        textCorrectness.setText("Finished all words in "
                                +langUserTransFrom+ " - " +langUserTransTo+" language pair!");
                        textCorrectness.setFill(Color.DARKBLUE);
                        textLanFrom1.setVisible(false);
                        textLanTo1.setVisible(false);
                    } else {

                        String wordToCheck = textFieldLang2.getText();

                        if (rwengn.userAnswerIsCorrect(wordToCheck)) {
                            textCorrectness.setText("Correct!");
                            textCorrectness.setFill(Color.GREEN);
                        }
                        else {
                            textCorrectness.setText("Wrong!");
                            textCorrectness.setFill(Color.RED);
                        }

                        rwengn.removeCheckedWordFromWordsSetStartLang();

                        if (!rwengn.wordsSetUserTranslFromIsEmpty()) {
                            textFieldLang1.setText(rwengn.wordUserHasToGiveTranslationTo());
                        }
                        else
                            textFieldLang1.clear();

                        textFieldLang2.clear();
                    }
                });

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }


    private String getLangToStartTo(){
        if(radioMenuItem_ar11.isSelected()){
            return "ar";
        }
        if(radioMenuItem_de11.isSelected()){
            return "de";
        }
        if(radioMenuItem_en11.isSelected()){
            return "en";
        }
        if(radioMenuItem_fr11.isSelected()){
            return "fr";
        }
        if(radioMenuItem_it11.isSelected()){
            return "it";
        }
        if(radioMenuItem_pl11.isSelected()){
            return "pl";
        }
        if(radioMenuItem_es11.isSelected()){
            return "es";
        }
        if(radioMenuItem_ru11.isSelected()){
            return "ru";
        }
        return "pl";
    }

    private String getLangToStartFrom(){
        if(radioMenuItem_ar1.isSelected()){
            menuButton1.setText("Arabic");
            return "ar";
        }
        if(radioMenuItem_de1.isSelected()){
            menuButton1.setText("German");
            return "de";
        }
        if(radioMenuItem_en1.isSelected()){
            menuButton1.setText("English");
            return "en";
        }
        if(radioMenuItem_fr1.isSelected()){
            menuButton1.setText("French");
            return "fr";
        }
        if(radioMenuItem_it1.isSelected()){
            menuButton1.setText("Italian");
            return "it";
        }
        if(radioMenuItem_pl1.isSelected()){
            menuButton1.setText("Polish");
            return "pl";
        }
        if(radioMenuItem_es1.isSelected()){
            menuButton1.setText("Spanish");
            return "es";
        }
        if(radioMenuItem_ru1.isSelected()){
            menuButton1.setText("Russian");
            return "ru";
        }
        return "en";
    }
    public String getFullLanName(String lan){
        if(lan.equals("ar")){
            return "Arabic";
        }
        if(lan.equals("de")){
            return "German";
        }
        if(lan.equals("en")){
            return "English";
        }
        if(lan.equals("fr")){
            return "French";
        }
        if(lan.equals("it")){
            return "Italian";
        }
        if(lan.equals("pl")){
            return "Polish";
        }
        if(lan.equals("es")){
            return "Spanish";
        }
        if(lan.equals("ru")){
            return "Russian";
        }
        else
            return"";
    }

}
