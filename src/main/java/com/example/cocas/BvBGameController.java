package com.example.cocas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BvBGameController implements Initializable {
    @FXML
    private Button boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9;
    @FXML
    private Text resultado;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int turno = 0;
    private boolean partida = true;
    String rutaArchivo = "resultados.txt";
    ArrayList<Button> botones;
    List<Integer> botonesRestantes = new ArrayList<>();
    Random random = new Random();
    /**
     * Metodo para prepara tablero
     * @param url Ni idea
     * @param resourceBundle x2
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        botones = new ArrayList<>(Arrays.asList(boton1,boton2,boton3,boton4,boton5,boton6,boton7,boton8,boton9));
        botones.forEach(button ->button.setFocusTraversable(false));
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
        partida = true;
        botonesRestantes.clear();
    }
    /**
     * Metodo para que las IAs se peleen
     * @throws InterruptedException
     */
    public void bvb() throws InterruptedException {
        int indiceAleatorio = random.nextInt(botones.size());
        while (partida)
            if (!botonesRestantes.contains(indiceAleatorio)) {
                hacerClick(botones.get(indiceAleatorio));
                botonesRestantes.add(indiceAleatorio);
                System.out.println(indiceAleatorio);
            }
            else indiceAleatorio = random.nextInt(botones.size());
        System.out.println(indiceAleatorio);
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
    private void hacerClick(Button boton) throws InterruptedException {
        turnosdelJugador(boton);
        boton.setDisable(true);
        victoria();
        Thread.sleep(500);
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
                resultado.setText("Gana Bot1");
                botones.forEach(button -> button.setDisable(true));
                partida = false;
                guardarEnResultado(rutaArchivo, "Bot1");
            }
            else if (linia.equals("ooo")){
                resultado.setText("Gana Bot2");
                botones.forEach(button -> button.setDisable(true));
                partida = false;
                guardarEnResultado(rutaArchivo, "Bot2");
            }
            else if (turno == 9) {
                resultado.setText("Empate");
                partida = false;
            }
        }
    }
    /**
     * Metodo para navegar entre los fxml
     * @param event el evento
     */
    public void PvP(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pvpjuego.fxml")));
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