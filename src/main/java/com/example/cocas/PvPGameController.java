package com.example.cocas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import java.io.FileWriter;
import java.io.IOException;

public class PvPGameController implements Initializable {
    @FXML
    private Button boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9;
    @FXML
    private Text resultado;
    @FXML
    private TextField jugador1;
    @FXML
    private TextField jugador2;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int turno = 0;
    ArrayList<Button> botones;
    String rutaArchivo = "resultados.txt";

    /**
     * Metodo para prepara el tablero
     * @param url Ni idea
     * @param resourceBundle x2
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        botones = new ArrayList<>(Arrays.asList(boton1,boton2,boton3,boton4,boton5,boton6,boton7,boton8,boton9));
        botones.forEach(button ->{
                hacerClick(button);
                button.setFocusTraversable(false);
                });
    }
    /**
     * Metodo para el reinicio de juego
     * @param event el evento
     */
    @FXML
    void reinicio(ActionEvent event) {
        botones.forEach(this::reiniciarBotones);
        turno = 0;
        resultado.setText("");
        jugador1.setDisable(false);
        jugador2.setDisable(false);
    }
    /**
     * Metodo para reiniciar el boton
     * @param boton el boton
     */
    public void reiniciarBotones(Button boton) {
        boton.setDisable(false);
        boton.setText("");
    }
    /**
     * Metodo para marcar una casilla
     * @param boton el boton
     */
    private void hacerClick(Button boton) {
        boton.setOnMouseClicked(mouseEvent -> {
            turnosdelJugador(boton);
            boton.setDisable(true);
            victoria();
            jugador1.setDisable(true);
            jugador2.setDisable(true);
        });
    }
    /**
     * Metod para los turnos de los jugadores
     * @param boton el boton
     */
    public void turnosdelJugador(Button boton) {
        if(turno % 2 == 0){
            boton.setText("x");
            turno++;
        } else {
            boton.setText("o");
            turno++;
        }
    }
    /**
     * Metodo para detectar si han ganado
     */
    public void victoria(){
        for (int i = 0; i < 8; i++) {
            String linia = switch (i){
                case 0 -> boton1.getText() + boton2.getText() + boton3.getText();
                case 1 -> boton4.getText() + boton5.getText() + boton6.getText();
                case 2 -> boton7.getText() + boton8.getText() + boton9.getText();
                case 3 -> boton1.getText() + boton4.getText() + boton7.getText();
                case 4 -> boton2.getText() + boton5.getText() + boton8.getText();
                case 5 -> boton3.getText() + boton6.getText() + boton9.getText();
                case 6 -> boton1.getText() + boton5.getText() + boton9.getText();
                case 7 -> boton7.getText() + boton5.getText() + boton3.getText();
                default -> null;
            };
            if (linia.equals("xxx")){
                resultado.setText("Gana " + jugador1.getText());
                botones.forEach(button -> button.setDisable(true));
                guardarEnResultado(rutaArchivo, jugador1.getText());
            }
            else if (linia.equals("ooo")){
                resultado.setText("Gana " + jugador2.getText());
                botones.forEach(button -> button.setDisable(true));
                guardarEnResultado(rutaArchivo, jugador2.getText());
            }
            else if (turno == 9) resultado.setText("Empate");
        }
    }
    /**
     * Metodo para navegar entre los fxml
     * @param event el evento
     */
    public void BvB(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("bvbjuego.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Metodo para navegar entre los fxml
     * @param event el evento
     */
    public void PvB(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pvbjuego.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Metodo para guardar resultado en un .txt
     * @param rutaArchivo donde esta el .txt
     * @param linea cual linia tiene que cojer
     */
    public static void guardarEnResultado(String rutaArchivo, String linea) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}