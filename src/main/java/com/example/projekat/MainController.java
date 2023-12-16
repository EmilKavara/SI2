package com.example.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private ComboBox<String> attributeComboBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private CheckBox checkBoxKupovina;
    @FXML
    private CheckBox checkBoxIznajmljivanje;
    @FXML
    private Button addNewCarButton;
    @FXML
    private Button editCarButton;
    @FXML
    private Button deleteCarButton;
    @FXML
    private TableView<Vozilo> table;

    private final UpravljanjeVozilima upravljanjeVozilima = new UpravljanjeVozilima();
    private ObservableList<Vozilo> originalnaListaVozila;

    @FXML
    private void initialize() {
        configureSearchAndCheckboxHandlers();
    }

    /**
     * Prilikom unosa teksta ili označavanja CheckBox-a poziva funkciju handleSearchAndCheckbox()
     */
    private void configureSearchAndCheckboxHandlers() {
        searchTextField.setOnKeyReleased(e -> handleSearchAndCheckbox());
        checkBoxKupovina.setOnAction(e -> handleSearchAndCheckbox());
        checkBoxIznajmljivanje.setOnAction(e -> handleSearchAndCheckbox());
    }

    /**
     * Postavlja UI komponenti na scenu
     * @return scene Vraća scenu koja će se prikazati
     */
    private Scene prikazSvihVozila() {
        configureUIComponents();
        configureTableColumns();
        configureTableData();
        configureEventHandlers();

        VBox vbox = createLayout();
        Scene scene = new Scene(vbox, 950, 400);
        return scene;
    }

    /**
     * Postavljanje UI komponenti i njihovih vrijednosti
     */
    private void configureUIComponents() {
        if (attributeComboBox.getItems().isEmpty()) { // Check if items are already added
            attributeComboBox.getItems().addAll("Naziv", "Model", "Vrsta Goriva", "Registracija");
            attributeComboBox.setValue("Naziv");
        }

        searchTextField.setPromptText("Pretraži...");
        checkBoxKupovina = new CheckBox("Dostupno za Kupovinu");
        checkBoxIznajmljivanje = new CheckBox("Dostupno za Iznajmljivanje");
    }


    /**
     * Postavljanje kolona tabele
     */
    private void configureTableColumns() {
        if (table.getColumns().isEmpty()) { // Check if columns are already added
            TableColumn<Vozilo, String> nazivCol = createColumn("Naziv", "naziv");
            TableColumn<Vozilo, String> modelCol = createColumn("Model", "model");
            TableColumn<Vozilo, Integer> godisteCol = createColumn("Godište", "godiste");
            TableColumn<Vozilo, String> registracijaCol = createColumn("Registracija", "registracija");
            TableColumn<Vozilo, Integer> kilometrazaCol = createColumn("Kilometraža", "kilometraza");
            TableColumn<Vozilo, VrstaGoriva> gorivoCol = createColumn("Vrsta Goriva", "vrstaGoriva");
            TableColumn<Vozilo, Boolean> kupovinaCol = createColumn("Dostupno za Kupovinu", "dostupnoZaKupovinu");
            TableColumn<Vozilo, Boolean> iznajmljivanjeCol = createColumn("Dostupno za iznajmljivanje", "dostupnoZaIznajmljivanje");

            table.getColumns().addAll(nazivCol, modelCol, godisteCol, registracijaCol, kilometrazaCol, gorivoCol, kupovinaCol, iznajmljivanjeCol);
        }
    }

    /**
     * Kreiranje kolona tabele
     * @param columnName naziv kolone
     * @param propertyName naziv atributa klase Vozilo
     * @return kolona tabele
     */
    private <S, T> TableColumn<S, T> createColumn(String columnName, String propertyName) {
        TableColumn<S, T> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
    }

    /**
     * Unos vrijednosti u tabelu
     * Ispis vrijednosti liste u konzolu
     */
    private void configureTableData() {
        System.out.println("Originalna Lista Vozila:");
        originalnaListaVozila.forEach(System.out::println);
        table.setItems(originalnaListaVozila);
    }

    private void configureEventHandlers() {
        configureSearchAndCheckboxHandlers();
    }

    /**
     * Kreiranje UI komponenti iznad tabele
     * @return raspored UI komponenti kao VBox
     */
    private VBox createLayout() {
        HBox searchBox = new HBox(10, attributeComboBox, searchTextField, checkBoxKupovina, checkBoxIznajmljivanje,
                addNewCarButton, editCarButton, deleteCarButton);
        searchBox.setPadding(new Insets(10));
        VBox vbox = new VBox(10, searchBox, table);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    /**
     * Inicijalizuje view za prikaz liste vozila
     * @param stage za prikaz view-a
     */
    public void initializeView(Stage stage) {
        originalnaListaVozila = upravljanjeVozilima.ucitavanjeIzFajla();
        Scene scene = prikazSvihVozila();
        stage.setScene(scene);
        stage.setTitle("Prikaz Vozila");
        stage.show();
    }

    /**
     * Filtrira tabelu prema unesenim kriterijima
     */
    @FXML
    private void handleSearchAndCheckbox() {
        String searchCriteria = searchTextField.getText().toLowerCase();
        String searchType = attributeComboBox.getValue();
        boolean dostupnoZaKupovinu = checkBoxKupovina.isSelected();
        boolean dostupnoZaIznajmljivanje = checkBoxIznajmljivanje.isSelected();

        ObservableList<Vozilo> filteredList = upravljanjeVozilima.filtriranjeTabele(searchCriteria, searchType,
                dostupnoZaKupovinu, dostupnoZaIznajmljivanje);
        table.setItems(filteredList);
    }

    /**
     * Otvaranje novog prozora za dodavanje novog vozila
     */
    @FXML
    private void addNewCar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-car.fxml"));
            Parent root = fxmlLoader.load();
            Stage addCarStage = createNewStage("Dodaj Novo Vozilo", root);
            AddCarController addCarController = fxmlLoader.getController();
            addCarController.setMainController(this);
            addCarStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Otvaranje prozora za uređivanje vozila
     */
    @FXML
    private void editSelectedCar() {
        Vozilo selectedVozilo = table.getSelectionModel().getSelectedItem();
        if (selectedVozilo != null) {
            try {
                // Load the editing window
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-car.fxml"));
                Parent root = fxmlLoader.load();
                EditCarController editCarController = fxmlLoader.getController();
                editCarController.setEditCar(selectedVozilo);
                editCarController.setMainController(this);

                // Create a new stage for the editing window
                Stage editCarStage = new Stage();
                editCarStage.setTitle("Edit Car");
                editCarStage.setScene(new Scene(root));
                editCarStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showNoVehicleSelectedMessage();
        }
    }

    /**
     * Ako je vozilo izabrano briše ga iz tabele
     * Ako nije izabrano prikazuje poruku da se treba izabrati vozilo
     */
    @FXML
    private void deleteSelectedCar() {
        Vozilo selectedVozilo = table.getSelectionModel().getSelectedItem();
        originalnaListaVozila = upravljanjeVozilima.ucitavanjeIzFajla();

        if (selectedVozilo != null) {
            boolean removed = upravljanjeVozilima.obrisiVozilo(selectedVozilo);

            if (removed) {
                table.refresh();
                table.setItems(originalnaListaVozila);
                System.out.println("Brisanje vozila: " + selectedVozilo);
            }
        } else {
            showNoVehicleSelectedMessage();
        }
    }


    /**
     * Ažuriranje podataka u tabeli
     * @param updatedList Ažurirana lista
     */
    public void updateTable(ObservableList<Vozilo> updatedList) {
        table.setItems(updatedList);

        if (updatedList == null) {
            updatedList = FXCollections.observableArrayList();
        }

        originalnaListaVozila.setAll(updatedList);
    }


    private Stage createNewStage(String title, Parent root) {
        Stage addCarStage = new Stage();
        addCarStage.setTitle(title);
        addCarStage.setScene(new Scene(root));
        return addCarStage;
    }

    private void showNoVehicleSelectedMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Vozilo nije izabrano");
        alert.setHeaderText(null);
        alert.setContentText("Izaberite vozilo.");

        alert.showAndWait();
    }
}
