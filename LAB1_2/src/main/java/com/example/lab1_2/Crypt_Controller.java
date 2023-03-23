package com.example.lab1_2;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Crypt_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea Crypt;

    @FXML
    private Button Crypt_Button;

    @FXML
    private Tab Crypt_Change;
    @FXML
    private MenuItem open;

    @FXML
    private Button Decrypt_Button;

    @FXML
    private Tab Decrypt_Change;

    @FXML
    private TextField Key;

    @FXML
    private CheckBox Vizener;

    @FXML
    private CheckBox Zesar;

    @FXML
    private TextField Decrypt_Key;

   @FXML
    private TextArea TextArea_Decrypt;

    @FXML
    void initialize() {
        open.setOnAction(event -> {
            Open();
        });

                 //Стиль для CheckBox-a

        Vizener.setOnAction(event ->{

            if(Vizener.isSelected()){
                Zesar.setVisible(false);
            } else {
                Zesar.setVisible(true);
            }
        });
        Zesar.setOnAction(event ->{
            if(Zesar.isSelected()){
                Vizener.setVisible(false);
                Key.setVisible(false);
                Decrypt_Key.setVisible(false);
            } else {
                Vizener.setVisible(true);
                Key.setVisible(true);
                Decrypt_Key.setVisible(true);
            }
        });
                            //Шифрування методом Цезаря

        Crypt_Button.setOnAction(event ->{
            if(Zesar.isSelected()) {
                Crypt_Zesar();
            } else {
                // Місце для шифрування методом Віженера
            } if (Vizener.isSelected()) {
                Vizener_Crypt();
                }
        });
                        //Розшифрування методом Цезаря

        Decrypt_Button.setOnAction(event ->{
            if(Zesar.isSelected()) {
                Decrypt_Zesar();
            } else{
                // Місце для дешифрування методом Віженера
            } if (Vizener.isSelected()) {
                Vizener_Decrypt();
            }
        });
    }
    void Decrypt_Zesar(){
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File selectedfile = fileChooser.showOpenDialog(stage);
        try {
            FileReader fileReader = new FileReader(selectedfile);
            int c;
            StringBuilder decryptedText = new StringBuilder();
            while ((c = fileReader.read()) != -1) {
                c -= 3;
                decryptedText.append((char) c);
            }
            fileReader.close();
            TextArea_Decrypt.setText(decryptedText.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void Crypt_Zesar(){
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File selectedfile = fileChooser.showOpenDialog(stage);
        try {
            FileWriter fileWriter = new FileWriter(selectedfile);
            String text = Crypt.getText();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                c += 3;
                fileWriter.write(c);
            }
            fileWriter.close();
            FileReader fileReader = new FileReader(selectedfile);
            int c;
            StringBuilder encryptedText = new StringBuilder();
            while ((c = fileReader.read()) != -1) {
                encryptedText.append((char) c);
            }
            fileReader.close();
            Crypt.setText(encryptedText.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void Vizener_Crypt(){
        char leters [] = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        String cipherText = "";

        String t = Crypt.getText();
        String k = Key.getText();

        t = t.toLowerCase();
        System.out.println(t);


        int tl = t.length();
        k += k.repeat(tl / k.length());

        System.out.println(k);

        for (int i = 0; i < t.length(); i++) {
            int r = 0;
            int x = 0;

            char c = t.charAt(i);
            char c1 = k.charAt(i);

            for (int j = 0; j < 26; j++) {
                if (c == leters[j]){
                    r = j + 1;
                }
            }

            for (int j = 0; j < 26; j++) {
                if (c1 == leters[j]) {
                    x = j + 1;
                }
            }

            int sum = r + x;
            sum = sum % 26;
            if (sum % 26 == 0){
                sum = 26;
            }

            for (int j = 0; j < 26; j++) {
                if (sum == j + 1){
                    cipherText += leters[j];
                }
            }
        }
        Crypt.setText(cipherText);
    }
    void Vizener_Decrypt(){
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File selectedfile = fileChooser.showOpenDialog(stage);

        try (FileReader fr = new FileReader(selectedfile)) {
            Scanner scan = new Scanner(fr);

            String text1 = "";
            while (scan.hasNextLine()) {

                String text = "";
                String cipherText = "";

                text = scan.nextLine();

                String key = Decrypt_Key.getText();
                key += key.repeat(text.length() / key.length());
                char leters[] = "abcdefghijklmnopqrstuvwxyz".toCharArray();

                for (int i = 0; i < text.length(); i++) {
                    int r = 0;
                    int x = 0;

                    char c = text.charAt(i);
                    char c1 = key.charAt(i);

                    if (c == c1) {
                        cipherText += ' ';
                    }

                    for (int j = 0; j < 26; j++) {
                        if (c == leters[j]) {
                            r = j + 1;
                        }
                    }

                    for (int j = 0; j < 26; j++) {
                        if (c1 == leters[j]) {
                            x = j + 1;
                        }
                    }

                    int sum = r - x;
                    if (sum < 0) {
                        sum += 26;
                    }

                    for (int j = 0; j < 26; j++) {
                        if (sum % 26 == j + 1) {
                            cipherText += leters[j];
                        }
                    }
                }
                text1 += cipherText + "\n";
            }
            TextArea_Decrypt.setText(text1);


        }catch (FileNotFoundException e){
            Crypt.setText("Файл не знайдено");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     public void Open(){
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File selectedfile = fileChooser.showOpenDialog(stage);
        try {
            FileReader fileReader = new FileReader(selectedfile);
            int c;
            StringBuilder decryptedText = new StringBuilder();
            while ((c = fileReader.read()) != -1) {
                decryptedText.append((char) c);
            }
            fileReader.close();
            Crypt.setText(decryptedText.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}