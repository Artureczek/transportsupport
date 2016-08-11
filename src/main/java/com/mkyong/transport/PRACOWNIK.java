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
    private int pracownikId;

    @Column(name = "IMIE")
    private String imie;

    @Column(name = "NAZWISKO")
    private String nazwisko;

    @Column(name = "PESEL")
    private String pesel;

    @Column(name = "STAWKA")
    private float stawka;

    @Column(name = "DATA_URODZENIA")
    private Date dataUrodzenia;

    @Column(name = "ID_USER")
    private int user;

    public PRACOWNIK() {
    }

    public PRACOWNIK(String imie, String nazwisko, String pesel, float stawka, Date dataurodzenia, int iduser) {

        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.stawka = stawka;
        this.dataUrodzenia = dataurodzenia;
        this.user = iduser;
    }

    public int getPracownikId() {
        return pracownikId;
    }

    public void setPracownikId(int pracownikId) {
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

    public float getStawka() {
        return stawka;
    }

    public void setStawka(float stawka) {
        this.stawka = stawka;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}