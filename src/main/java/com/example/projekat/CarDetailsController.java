package com.example.projekat;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

public abstract class CarDetailsController {
    // Deklaracija UI elemenata
    @FXML
    protected TextField nazivTextField;
    @FXML
    protected TextField modelTextField;
    @FXML
    protected TextField godisteTextField;
    @FXML
    protected TextField registracijaTextField;
    @FXML
    protected TextField kilometrazaTextField;
    @FXML
    protected ComboBox<VrstaGoriva> vrstaGorivaComboBox;
    @FXML
    protected CheckBox kupovinaCheckBox;
    @FXML
    protected CheckBox iznajmljivanjeCheckBox;
    protected MainController mainController;
    protected ObservableList<Vozilo> listaVozila;
    protected UpravljanjeVozilima upravljanjeVozilima;

    public CarDetailsController() {

    }
    // Inicijalizacija UI elemenata i postavljanje uslova za unos podataka
    @FXML
    private void initialize() {
        upravljanjeVozilima = new UpravljanjeVozilima();

        configureTextField(registracijaTextField);
        configureNumericTextField(kilometrazaTextField);
        configureNumericTextField(godisteTextField);

        vrstaGorivaComboBox.getItems().setAll(VrstaGoriva.values());
        vrstaGorivaComboBox.setValue(VrstaGoriva.BENZIN);
    }

    /**
     * Prikazuje dijalog greške sa naslovom i sadržajem.
     *
     * @param title Naslov greške.
     * @param content Sadržaj greške.
     */
    protected void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected boolean showConfirmationDialog(String title, String content) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(content);
        confirmationDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Omogućava samo unos brojeva
     * @param textField za koji se postavlja uslov (godiste i kilometraza)
     */
    protected void configureNumericTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    protected void refreshTable() {
        mainController.updateTable(listaVozila);
    }

    /**
     * Provjerava korisnički unos
     * @return true ako su zadovoljeni uslovi, ako nisu onda false
     */
    protected boolean checkInput() {
        String naziv = nazivTextField.getText();
        String model = modelTextField.getText();
        int godiste = Integer.parseInt(godisteTextField.getText());
        String registracija = registracijaTextField.getText();
        int kilometraza = Integer.parseInt(kilometrazaTextField.getText());

        return !naziv.matches("^\\d.*") && !model.matches("^\\d.*") &&
                (godiste >= 1900 && godiste <= 2023) && !registracija.isBlank() && kilometraza >= 0;
    }

    /**
     * Korisnički unos pretvata u velika slova
     * @param textField za koji se vrši pretvaranje (registracija)
     */
    private void configureTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) ->
                textField.setText(newValue.toUpperCase()));
    }

    public void setMainController(MainController mainController) {

        this.mainController = mainController;
    }

    /**
     * Kreira vozilo na osnovu unesenih vrijednosti
     * @return objekat tipa Vozilo
     */
    protected Vozilo kreirajVozilo() {
        return new Vozilo(
                nazivTextField.getText(),
                modelTextField.getText(),
                Integer.parseInt(godisteTextField.getText()),
                registracijaTextField.getText(),
                Integer.parseInt(kilometrazaTextField.getText()),
                vrstaGorivaComboBox.getValue(),
                kupovinaCheckBox.isSelected(),
                iznajmljivanjeCheckBox.isSelected()
        );
    }
}
