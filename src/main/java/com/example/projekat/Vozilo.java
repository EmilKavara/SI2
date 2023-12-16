package com.example.projekat;

import java.util.Objects;

public class Vozilo {
    private String naziv;
    private String model;
    private int godiste;
    private String registracija;
    private int kilometraza;
    private VrstaGoriva vrstaGoriva;
    private boolean dostupnoZaKupovinu;
    private boolean dostupnoZaIznajmljivanje;

    public Vozilo(String naziv, String model, int godiste, String registracija, int kilometraza, VrstaGoriva vrstaGoriva, boolean dostupnoZaKupovinu, boolean dostupnoZaIznajmljivanje) {
        this.naziv = naziv;
        this.model = model;
        this.godiste = godiste;
        this.registracija = registracija;
        this.kilometraza = kilometraza;
        this.vrstaGoriva = vrstaGoriva;
        this.dostupnoZaKupovinu = dostupnoZaKupovinu;
        this.dostupnoZaIznajmljivanje = dostupnoZaIznajmljivanje;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public int getKilometraza() {
        return kilometraza;
    }

    public void setKilometraza(int kilometraza) {
        this.kilometraza = kilometraza;
    }

    public VrstaGoriva getVrstaGoriva() {
        return vrstaGoriva;
    }

    public void setVrstaGoriva(VrstaGoriva vrstaGoriva) {
        this.vrstaGoriva = vrstaGoriva;
    }

    public boolean isDostupnoZaKupovinu() {
        return dostupnoZaKupovinu;
    }

    public void setDostupnoZaKupovinu(boolean dostupnoZaKupovinu) {
        this.dostupnoZaKupovinu = dostupnoZaKupovinu;
    }

    public boolean isDostupnoZaIznajmljivanje() {
        return dostupnoZaIznajmljivanje;
    }

    public void setDostupnoZaIznajmljivanje(boolean dostupnoZaIznajmljivanje) {
        this.dostupnoZaIznajmljivanje = dostupnoZaIznajmljivanje;
    }

    /**
     * U ovom formatu se spremaju vozila u vozila.txt fajl
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(naziv).append(",").append(model).append(",").append(godiste)
                .append(",").append(registracija).append(",").append(kilometraza)
                .append(",").append(vrstaGoriva);

        if (dostupnoZaKupovinu) {
            sb.append(",dostupno za kupovinu");
        } else {
            sb.append(",nije dostupno za kupovinu");
        }

        if (dostupnoZaIznajmljivanje) {
            sb.append(",dostupno za iznajmljivanje");
        } else {
            sb.append(",nije dostupno za iznajmljivanje");
        }

        return sb.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vozilo vozilo = (Vozilo) obj;


        return Objects.equals(naziv, vozilo.naziv) &&
                Objects.equals(model, vozilo.model) &&
                godiste == vozilo.godiste &&
                vrstaGoriva == vozilo.vrstaGoriva &&
                Objects.equals(registracija,vozilo.registracija) &&
                kilometraza==vozilo.kilometraza;
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv, model, godiste, vrstaGoriva, registracija, kilometraza);
    }


}
