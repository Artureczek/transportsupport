package com.mkyong.transport;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "PRACOWNIK")
public class PRACOWNIK implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="PRACOWNIK_SEQ", sequenceName="PRACOWNIK_SEQ", allocationSize=1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator="PRACOWNIK_SEQ")
    @Column(name = "ID_PRACOWNIKA")
    private Long pracownikId;

    @Column(name = "IMIE")
    private String imie;

    @Column(name = "NAZWISKO")
    private String nazwisko;

    @Column(name = "PESEL")
    private String pesel;

    @Column(name = "STAWKA")
    private Long stawka;

    @Column(name = "DATA_URODZENIA")
    private Date dataUrodzenia;

    @Column(name = "CZY_KIEROWCA")
    private Boolean czyKierowca;


    @JoinColumn(name = "ID_USER")
    @ManyToOne
    private APPUSER user;

    public PRACOWNIK() {
    }

    public PRACOWNIK(String imie, String nazwisko, String pesel, Long stawka, Date dataurodzenia, Boolean czyKierowca, APPUSER iduser) {

        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.stawka = stawka;
        this.dataUrodzenia = dataurodzenia;
        this.czyKierowca = czyKierowca;
        this.user = iduser;
    }

    public Boolean getCzyKierowca() { return czyKierowca; }

    public void setCzyKierowca(Boolean czyKierowca) { this.czyKierowca = czyKierowca; }

    public Long getPracownikId() {
        return pracownikId;
    }

    public void setPracownikId(Long pracownikId) {
        this.pracownikId = pracownikId;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Long getStawka() { return stawka; }

    public void setStawka(Long stawka) {
        this.stawka = stawka;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public APPUSER getUser() { return user;  }

    public void setUser(APPUSER user) { this.user = user; }
}