package com.example.projekat;

import javafx.fxml.FXML;

public class AddCarController extends CarDetailsController {
    // Koristi se kao zastava koja određuje da li se radi o spremanju podataka ili zatvaranju prozora
    private boolean isSaving = false;

    /**
     * Provjerava da li vozilo već postoji i dodaje ga u listu vozila
     * Upisuje listu vozila u fajl vozila.txt i osvježava pregled tabele
     */
    @FXML
    public void sacuvajVozilo() {
        if (!checkInput()) {
            showErrorDialog("Greška!", "Vozilo već postoji.");
            return;
        }
        // Kreira se vozilo koristeći unos korisnika
        Vozilo vozilo = kreirajVozilo();
        // Učitavanje originalne liste vozila
        listaVozila = upravljanjeVozilima.ucitavanjeIzFajla();
        // Ako lista ne sadrži vozilo onda se vozilo dodaje u listu i sačuva se u vozila.txt fajl
        if (!listaVozila.contains(vozilo)) {
            upravljanjeVozilima.dodajVozilo(vozilo);
            upravljanjeVozilima.sacuvajUFajl();
            refreshTable();
        }

        isSaving = true;
        closeWindow();
    }

    /**
     * Zatvara prozor nakon provjere da li ima nespremljenih podataka
     * Ako postoje nespremljeni podaci prikazuje se ConfirmationDialog koji tražu potvrdu korisnika da želi da zatvori prozor
     */
    @FXML
    protected void closeWindow() {
        if (!isSaving && hasUnsavedData()) {
            if (!showConfirmationDialog("Upozorenje", "Da li ste sigurni da zelite zatvoriti prozor?")) {
                return;
            }
        }
        nazivTextField.getScene().getWindow().hide();
        isSaving = false;
    }

    private boolean hasUnsavedData() {
        return !nazivTextField.getText().isEmpty() || !modelTextField.getText().isEmpty() || !kilometrazaTextField.getText().isEmpty() || !registracijaTextField.getText().isEmpty() || !godisteTextField.getText().isEmpty();
    }

}
