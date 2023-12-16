package com.example.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class UpravljanjeVozilima {
    private static final String FILE_PATH = "vozila.txt";
    private ObservableList<Vozilo> listaVozila;

    public UpravljanjeVozilima() {
        this.listaVozila = FXCollections.observableArrayList();
        ucitavanjeIzFajla();
    }

    public void dodajVozilo(Vozilo vozilo) {
        listaVozila.add(vozilo);
        System.out.println("Dodano je vozilo. "+vozilo);
    }

    /**
     * Filtrira tabelu vozila na osnovu unesenih kriterija pretrage, vrste pretrage, dostupnosti za kupovinu i dostupnosti za iznajmljivanje
     * @param searchCriteria Vrijednost koja se traži
     * @param searchType Vrsta pretrage (Naziv, Model, Vrsta Goriva, Registracija)
     * @param dostupnoZaKupovinu Dostupnost za kupovinu
     * @param dostupnoZaIznajmljivanje Dostupnost za iznajmljivanje
     * @return Filtrirana lista vozila koja zadovoljava sve kriterije
     */
    public ObservableList<Vozilo> filtriranjeTabele(String searchCriteria, String searchType, boolean dostupnoZaKupovinu, boolean dostupnoZaIznajmljivanje) {
        ObservableList<Vozilo> filtriranaLista = FXCollections.observableArrayList();

        for (Vozilo vozilo : listaVozila) {
            boolean isMatch = true;

            if (!searchCriteria.isEmpty()) {
                switch (searchType) {
                    case "Naziv":
                        isMatch = vozilo.getNaziv().toLowerCase().startsWith(searchCriteria);
                        break;
                    case "Model":
                        isMatch = vozilo.getModel().toLowerCase().startsWith(searchCriteria);
                        break;
                    case "Vrsta Goriva":
                        isMatch = vozilo.getVrstaGoriva().toString().toLowerCase().startsWith(searchCriteria);
                        break;
                    case "Registracija":
                        isMatch = vozilo.getRegistracija().toLowerCase().startsWith(searchCriteria);
                        break;
                    default:
                        System.out.println("error");
                        break;
                }
            }
            boolean kupovinaMatch = !dostupnoZaKupovinu || vozilo.isDostupnoZaKupovinu();
            boolean iznajmljivanjeMatch = !dostupnoZaIznajmljivanje || vozilo.isDostupnoZaIznajmljivanje();

            if (isMatch && kupovinaMatch && iznajmljivanjeMatch) {
                filtriranaLista.add(vozilo);
            }
        }

        return filtriranaLista;
    }

    /**
     * Uklanja vozilo iz liste vozila
     * @param vozilo koje se uklanja
     * @return true ako je uspješno uklonjeno, false ako nije uspjelo uklanjanje
     */
    public boolean obrisiVozilo(Vozilo vozilo) {
        boolean removed = listaVozila.remove(vozilo);
        if (removed) {
            sacuvajUFajl();
        }
        return removed;
    }

    public ObservableList<Vozilo> getListaVozila() {

        return listaVozila;
    }

    /**
     * Učitava podatke o vozilima iz tekstualnog fajla vozila.txt
     * @return lista vozila
     */
    public ObservableList<Vozilo> ucitavanjeIzFajla() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vozilo novoVozilo = konstruisiVoziloIzLinije(line);
                if (!listaVozila.contains(novoVozilo)) {
                    listaVozila.add(novoVozilo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle or log the exception appropriately
        }
        return listaVozila;
    }

    /**
     * Upisuje podatke u tekstualni fajl vozila.txt
     */
    public void sacuvajUFajl() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Vozilo vozilo : listaVozila) {
                writer.write(vozilo.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle or log the exception appropriately
        }
        System.out.println("Vozila su sačuvana u vozila.txt");
    }

    /**
     * Kreira objekte na osnovu vozila.txt
     * @param line jedna linija sadrži podatke o jednom vozilo objektu
     * @return vozilo , konstruisan objekat pomoću podataka iz tekstualnog fajla
     */
    private Vozilo konstruisiVoziloIzLinije(String line) {
        String[] atributi = line.split(",");
        boolean kupovina,iznajmljivanje;
        kupovina= atributi[6].equalsIgnoreCase("dostupno za kupovinu");
        iznajmljivanje= atributi[7].equalsIgnoreCase("dostupno za iznajmljivanje");
        return new Vozilo(  atributi[0],
                            atributi[1],
                            Integer.parseInt(atributi[2]),
                            atributi[3],
                            Integer.parseInt(atributi[4]),
                            VrstaGoriva.valueOf(atributi[5]),
                            kupovina,
                            iznajmljivanje
                            );
    }

    /**
     * Ažurira postojeće vozilo sa novim podacima
     * @param selectedVozilo vozilo koje se ažurira
     * @param updatedVozilo  novo vozilo sa ažuriranim podacima
     */
    public void updateVozilo(Vozilo selectedVozilo, Vozilo updatedVozilo) {
        int index = listaVozila.indexOf(selectedVozilo);

        if (index != -1) {
            listaVozila.set(index, updatedVozilo);
            System.out.println("Ažuriranje vozila: " + selectedVozilo);
            sacuvajUFajl();
        }
    }
    public void setListaVozila(ObservableList<Vozilo> listaVozila) {
        this.listaVozila = listaVozila;
    }
}
