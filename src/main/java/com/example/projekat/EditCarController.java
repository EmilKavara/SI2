package com.example.projekat;

import javafx.fxml.FXML;

public class EditCarController extends CarDetailsController {

    private Vozilo selectedVozilo;

    /**
     * Postavlja vozilo koje će se uređivati
     * @param selectedVozilo Odabrano vozilo koje se uređuje
     */
    public void setEditCar(Vozilo selectedVozilo) {
        this.selectedVozilo = selectedVozilo;

        if (selectedVozilo != null) {
            // Popunjava polja sa podacima o vozilu ako je vozilo izabrano
            fillFields(selectedVozilo);
        }
    }

    /**
     * Sprema promjene na odabranom vozilu
     * Ako nije odabrano vozilo, prikazuje dijalog greške
     */
    @FXML
    protected void saveChanges() {
        if (selectedVozilo != null) {
            updateVozilo();
        } else {
            showErrorDialog("Greška", "Vozilo nije izabrano.");
        }
    }

    @FXML
    protected void closeWindow() {
        nazivTextField.getScene().getWindow().hide();
    }

    /**
     * Popunjava polja sa podacima o vozilu
     * @param vozilo
     */
    private void fillFields(Vozilo vozilo) {
        nazivTextField.setText(vozilo.getNaziv());
        modelTextField.setText(vozilo.getModel());
        godisteTextField.setText(String.valueOf(vozilo.getGodiste()));
        registracijaTextField.setText(vozilo.getRegistracija());
        kilometrazaTextField.setText(String.valueOf(vozilo.getKilometraza()));
        vrstaGorivaComboBox.setValue(vozilo.getVrstaGoriva());
        kupovinaCheckBox.setSelected(vozilo.isDostupnoZaKupovinu());
        iznajmljivanjeCheckBox.setSelected(vozilo.isDostupnoZaIznajmljivanje());
    }

    /**
     * Ažurira podatke o odabranom vozilu na osnovu unesenih vrijednosti
     */
    @FXML
    private void updateVozilo() {
        if (checkInput()) {
            // Dobavljanje unesenih vrijednosti
            String updatedNaziv = nazivTextField.getText();
            String updatedModel = modelTextField.getText();
            int updatedGodiste = Integer.parseInt(godisteTextField.getText());
            String updatedRegistracija = registracijaTextField.getText();
            int updatedKilometraza = Integer.parseInt(kilometrazaTextField.getText());
            VrstaGoriva updatedVrstaGoriva = vrstaGorivaComboBox.getValue();
            boolean updatedKupovina = kupovinaCheckBox.isSelected();
            boolean updatedIznajmljivanje = iznajmljivanjeCheckBox.isSelected();
            // Kreiranje ažuriranog vozila
            Vozilo updatedVozilo = new Vozilo(updatedNaziv, updatedModel, updatedGodiste, updatedRegistracija, updatedKilometraza, updatedVrstaGoriva, updatedKupovina, updatedIznajmljivanje);
            upravljanjeVozilima.updateVozilo(selectedVozilo, updatedVozilo);
            mainController.updateTable(upravljanjeVozilima.ucitavanjeIzFajla());
            closeWindow();
        } else {
            showErrorDialog("Pogrešan unos", "Unesite ispravne vrijednosti.");
        }
    }

}

