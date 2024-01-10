package com.example.projekat;

public interface VehicleManagement {
    void dodajVozilo(Vozilo vozilo);
    void updateVozilo(Vozilo selectedVozilo, Vozilo updatedVozilo);
    boolean obrisiVozilo(Vozilo vozilo);
}