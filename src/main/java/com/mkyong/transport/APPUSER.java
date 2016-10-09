package com.mkyong.transport;

import javax.persistence.*;


@Entity
@Table(name = "APPUSER")
public class APPUSER implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="APPUSER_SEQ", sequenceName="APPUSER_SEQ", allocationSize=1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator="APPUSER_SEQ")
    @Column(name = "ID_USER")
    private int appUserId;

    @Column(name = "NAZWAUZYTKOWNIKA")
    private String nazwaUzytkownika;

    @Column(name = "IMIE")
    private String imie;

    @Column(name = "NAZWISKO")
    private String nazwisko;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "HASLO")
    private String haslo;

    public APPUSER() {
    }

    public APPUSER(/*int appUserId, */String nazwaUzytkownika, String imie,
                   String nazwisko, String email, String haslo) {
       /* this.appUserId = appUserId;*/
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.haslo = haslo;
        this.email = email;
    }

    public int getappUserId() {
        return this.appUserId;
    }

    public void setappUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getnazwaUzytkownika() {
        return this.nazwaUzytkownika;
    }

    public void setnazwaUzytkownika(String nazwaUzytkownika) {
        this.nazwaUzytkownika = nazwaUzytkownika;
    }

    public String getimie() {
        return this.imie;
    }

    public void setimie(String imie) {
        this.imie = imie;
    }

    public String getnazwisko() {
        return this.nazwisko;
    }

    public void setnazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

}