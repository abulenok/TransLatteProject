package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button translateButton;

    @FXML
    private TextField textToTranslateField;

    @FXML
    private MenuButton menuButton;

    @FXML
    private TextField transResult1;

    @FXML
    private TextField transResult2;

    @FXML
    private TextField transResult4;

    @FXML
    private TextField transResult3;

    @FXML
    private RadioMenuItem radioMenuItem_ar;

    @FXML
    private RadioMenuItem radioMenuItem_de;

    @FXML
    private RadioMenuItem radioMenuItem_en;

    @FXML
    private RadioMenuItem radioMenuItem_fr;

    @FXML
    private RadioMenuItem radioMenuItem_it;

    @FXML
    private RadioMenuItem radioMenuItem_es;

    @FXML
    private RadioMenuItem radioMenuItem_pl;

    @FXML
    private RadioMenuItem radioMenuItem_ru;

    @FXML
    private CheckBox checkBox_ru;

    @FXML
    private CheckBox checkBox_en;

    @FXML
    private CheckBox checkBox_pl;

    @FXML
    private CheckBox checkBox_de;

    @FXML
    private CheckBox checkBox_fr;

    @FXML
    private CheckBox checkBox_it;

    @FXML
    private CheckBox checkBox_es;

    @FXML
    private CheckBox checkBox_ar;

    @FXML
    private Label lanlabel1;

    @FXML
    private Label lanlabel2;

    @FXML
    private Label lanlabel3;

    @FXML
    private Label lanlabel4;
    @FXML
    private Button addWordToDictionaryButton;

    @FXML
    private TextField textFieldAddWord1;

    @FXML
    private TextField textFieldAddWord2;

    @FXML
    private Button buttonAddWord1;

    @FXML
    private MenuButton menuButton2;

    @FXML
    private RadioMenuItem radioMenuItem_ar1;

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
    private MenuButton menuButton21;

    @FXML
    private RadioMenuItem radioMenuItem_ar11;

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
    private Button closeAddWordButton;
    @FXML
    private AnchorPane anchorPaneAddWord;
    @FXML
    private Button repeatWordsButton;


    @FXML
    void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(radioMenuItem_de);
        toggleGroup.getToggles().add(radioMenuItem_ar);
        toggleGroup.getToggles().add(radioMenuItem_en);
        toggleGroup.getToggles().add(radioMenuItem_es);
        toggleGroup.getToggles().add(radioMenuItem_fr);
        toggleGroup.getToggles().add(radioMenuItem_ru);
        toggleGroup.getToggles().add(radioMenuItem_pl);
        toggleGroup.getToggles().add(radioMenuItem_it);

        buttonAddWord1.setOnAction(event -> {
            AddWordPairClass addWordPair = new AddWordPairClass();
            try {
                addWordPair.addWordsPairToPersonalDictionary(determineLeftLan(), determineRightLan(),
                        textFieldAddWord1.getText().trim(), textFieldAddWord2.getText().trim());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            textFieldAddWord1.clear();
            textFieldAddWord2.clear();
        });

        repeatWordsButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("repeatWords.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("TransLatte App");
            stage.showAndWait();

        });

        addWordToDictionaryButton.setOnAction(event -> {
            anchorPaneAddWord.setVisible(true);
            buttonAddWord1.setVisible(true);
            textFieldAddWord1.setVisible(true);
            textFieldAddWord2.setVisible(true);
            addWordToDictionaryButton.setVisible(false);
            closeAddWordButton.setVisible(true);
            repeatWordsButton.setVisible(false);
            menuButton2.setVisible(true);
            menuButton21.setVisible(true);

            closeAddWordButton.setOnAction(event1 -> {
                anchorPaneAddWord.setVisible(false);
                buttonAddWord1.setVisible(false);
                textFieldAddWord1.setVisible(false);
                textFieldAddWord2.setVisible(false);
                addWordToDictionaryButton.setVisible(true);
                closeAddWordButton.setVisible(false);
                repeatWordsButton.setVisible(true);
                menuButton2.setVisible(false);
                menuButton21.setVisible(false);
            });
        });


       translateButton.setOnAction(event -> {
           String wordToTranslate = textToTranslateField.getText().trim();

           if(! wordToTranslate.equals("")) {

               makeLanLabelsEmpty();
               makeTranslationResultFieldsEmpty();
               Vector<String> languages = determineTranslateToLanguages();

               if(!( languages.size()>4 || languages.size()==0)) {
                   setTextToLanLabels(languages.size(), determineTranslateToLanLabelsTitles());
                   setTranslationResultFieldsInProcess(languages.size());

                   TranslateWordToMultiLangClass tranwtmull = new TranslateWordToMultiLangClass(languages,
                           determineLanguageFrom(), transResult1, transResult2, transResult3, transResult4);
                   tranwtmull.translateWordMultipleLan(wordToTranslate);
               }

           }
       });
    }

    private void setTextToLanLabels(int languages_size, Vector<String> lanLabels){
        if (languages_size > 0) {
            lanlabel1.setText(lanLabels.elementAt(0) + ":");
            if (languages_size > 1) {
                lanlabel2.setText(lanLabels.elementAt(1) + ":");
                if (languages_size > 2) {
                    lanlabel3.setText(lanLabels.elementAt(2) + ":");
                    if (languages_size > 3) {
                        lanlabel4.setText(lanLabels.elementAt(3) + ":");
                    }
                }
            }
        }
    }
    private void setTranslationResultFieldsInProcess(int languages_size){
        if (languages_size > 0) {
            transResult1.setText("In process...");
            if (languages_size > 1) {
                transResult2.setText("In process...");
                if (languages_size > 2) {
                    transResult3.setText("In process...");
                    if (languages_size > 3) {
                        transResult4.setText("In process...");
                    }
                }
            }
        }
    }

    private void makeTranslationResultFieldsEmpty(){
        transResult1.clear();
        transResult2.clear();
        transResult3.clear();
        transResult4.clear();
    }

    private void makeLanLabelsEmpty(){
        lanlabel1.setText("");
        lanlabel2.setText("");
        lanlabel3.setText("");
        lanlabel4.setText("");
    }

    private String determineLeftLan(){
        if(radioMenuItem_ar1.isSelected()){
            return "ar";
        }
        if(radioMenuItem_de1.isSelected()){
            return "de";
        }
        if(radioMenuItem_en1.isSelected()){
            return "en";
        }
        if(radioMenuItem_fr1.isSelected()){
            return "fr";
        }
        if(radioMenuItem_it1.isSelected()){
            return "it";
        }
        if(radioMenuItem_pl1.isSelected()){
            return "pl";
        }
        if(radioMenuItem_es1.isSelected()){
            return "es";
        }
        if(radioMenuItem_ru1.isSelected()){
            return "ru";
        }
        return "en";
    }

    private String determineRightLan(){
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
        return "en";
    }

    private Vector<String> determineTranslateToLanLabelsTitles(){
        Vector<String> lanLabels = new Vector<>();
        if (checkBox_ar.isSelected()) {
            lanLabels.add("Arabic");
        }
        if (checkBox_de.isSelected()) {
            lanLabels.add("German");
        }
        if (checkBox_en.isSelected()) {
            lanLabels.add("English");
        }
        if (checkBox_fr.isSelected()) {
            lanLabels.add("French");
        }
        if (checkBox_it.isSelected()) {
            lanLabels.add("Italian");
        }
        if (checkBox_pl.isSelected()) {
            lanLabels.add("Polish");
        }
        if (checkBox_ru.isSelected()) {
            lanLabels.add("Russian");
        }
        if (checkBox_es.isSelected()) {
            lanLabels.add("Spanish");
        }
        return lanLabels;
    }

    private Vector<String> determineTranslateToLanguages(){
        Vector<String> languages = new Vector<>();

        if (checkBox_ar.isSelected()) {
            languages.add("ar");
        }
        if (checkBox_de.isSelected()) {
            languages.add("de");
        }
        if (checkBox_en.isSelected()) {
            languages.add("en");
        }
        if (checkBox_fr.isSelected()) {
            languages.add("fr");
        }
        if (checkBox_it.isSelected()) {
            languages.add("it");
        }
        if (checkBox_pl.isSelected()) {
            languages.add("pl");
        }
        if (checkBox_ru.isSelected()) {
            languages.add("ru");
        }
        if (checkBox_es.isSelected()) {
            languages.add("es");
        }
        return languages;
    }

    private String determineLanguageFrom(){
        if(radioMenuItem_ar.isSelected()){
            return "ar";
        }
        if(radioMenuItem_de.isSelected()){
            return "de";
        }
        if(radioMenuItem_en.isSelected()){
            return "en";
        }
        if(radioMenuItem_fr.isSelected()){
            return "fr";
        }
        if(radioMenuItem_it.isSelected()){
            return "it";
        }
        if(radioMenuItem_pl.isSelected()){
            return "pl";
        }
        if(radioMenuItem_es.isSelected()){
            return "es";
        }
        if(radioMenuItem_ru.isSelected()){
            return "ru";
        }
        return "en";
    }
}
