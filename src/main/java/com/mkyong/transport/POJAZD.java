package com.mkyong.transport;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by artur on 09.10.2016.
 */
    @Entity
    @Table(name = "POJAZD")
    public class POJAZD implements java.io.Serializable {

        @Id
        @SequenceGenerator(name="POJAZD_SEQ", sequenceName="POJAZD_SEQ", allocationSize=1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="POJAZD_SEQ")
        @Column(name = "ID_POJAZDU")
        private Long pojazdId;

        @Column(name = "MARKA_POJAZDU")
        private String marka;

        @Column(name = "NUMER_REJESTRACJI")
        private String nrRejestracji;

        @Column(name = "MODEL_POJAZDU")
        private String model;

        @Column(name = "SILNIK_POJAZDU")
        private String silnik;

        @Column(name = "POJEMNOSC_BAKU")
        private Long pojemnoscBaku;

        @Column(name = "POJEMNOSC_LADOWNI")
        private Long pojemnoscLadowni;

        @Column(name = "SREDNIE_SPALANIE")
        private Double srednieSpalanie;

        @Column(name = "RODZAJ_PALIWA")
        private String rodzajPaliwa;

        @JoinColumn(name = "ID_USER")
        @ManyToOne
        private APPUSER user;

    public POJAZD() {}

    public POJAZD(String marka, String nrRejestracji, String model, String silnik, Long pojemnoscBaku, Long pojemnoscLadowni, Double srednieSpalanie, String rodzajPaliwa, APPUSER user) {
        this.marka = marka;
        this.nrRejestracji = nrRejestracji;
        this.model = model;
        this.silnik = silnik;
        this.pojemnoscBaku = pojemnoscBaku;
        this.pojemnoscLadowni = pojemnoscLadowni;
        this.srednieSpalanie = srednieSpalanie;
        this.rodzajPaliwa = rodzajPaliwa;
        this.user = user;
    }

    public String getRodzajPaliwa() {  return rodzajPaliwa; }

    public void setRodzajPaliwa(String rodzajPaliwa) { this.rodzajPaliwa = rodzajPaliwa; }

    public String getNrRejestracji() { return nrRejestracji; }

    public void setNrRejestracji(String nrRejestracji) { this.nrRejestracji = nrRejestracji; }

    public Long getPojazdId() {
        return pojazdId;
    }

    public void setPojazdId(Long pojazdId) {
        this.pojazdId = pojazdId;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSilnik() {
        return silnik;
    }

    public void setSilnik(String silnik) {
        this.silnik = silnik;
    }

    public Long getPojemnoscBaku() {
        return pojemnoscBaku;
    }

    public void setPojemnoscBaku(Long pojemnoscBaku) {
        this.pojemnoscBaku = pojemnoscBaku;
    }

    public Long getPojemnoscLadowni() {
        return pojemnoscLadowni;
    }

    public void setPojemnoscLadowni(Long pojemnoscLadowni) {
        this.pojemnoscLadowni = pojemnoscLadowni;
    }

    public Double getSrednieSpalanie() {
        return srednieSpalanie;
    }

    public void setSrednieSpalanie(Double srednieSpalanie) { this.srednieSpalanie = srednieSpalanie; }

    public APPUSER getUser() {
        return user;
    }

    public void setUser(APPUSER user) {
        this.user = user;
    }
}
