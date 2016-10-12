package com.mkyong.transport;

import javax.persistence.*;


@Entity
@Table(name = "TRASA")
public class TRASA implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="TRASA_SEQ", sequenceName="TRASA_SEQ", allocationSize=1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator="TRASA_SEQ")
    @Column(name = "ID_TRASY")
    private int trasaId;

    @Column(name = "ODLEGLOSC")
    private int odleglosc;

    @Column(name = "LOKACJA_STARTOWA")
    private String lokacjaStartowa;

    @Column(name = "LOKACJA_KONCOWA")
    private String lokacjaKoncowa;

    @Column(name = "PRIORYTER")
    private String priorytet;

    @JoinColumn(name = "ID_USER")
    @ManyToOne
    private APPUSER user;

    public TRASA(int odleglosc, String lokacjaStartowa, String lokacjaKoncowa, String priorytet, APPUSER user) {
        this.odleglosc = odleglosc;
        this.lokacjaStartowa = lokacjaStartowa;
        this.lokacjaKoncowa = lokacjaKoncowa;
        this.priorytet = priorytet;
        this.user = user;
    }

    public TRASA() {
    }

    public int getTrasaId() {
        return trasaId;
    }

    public void setTrasaId(int trasaId) {
        this.trasaId = trasaId;
    }

    public int getOdleglosc() {
        return odleglosc;
    }

    public void setOdleglosc(int odleglosc) {
        this.odleglosc = odleglosc;
    }

    public String getLokacjaStartowa() {
        return lokacjaStartowa;
    }

    public void setLokacjaStartowa(String lokacjaStartowa) {
        this.lokacjaStartowa = lokacjaStartowa;
    }

    public String getLokacjaKoncowa() {
        return lokacjaKoncowa;
    }

    public void setLokacjaKoncowa(String lokacjaKoncowa) {
        this.lokacjaKoncowa = lokacjaKoncowa;
    }

    public String getPriorytet() {
        return priorytet;
    }

    public void setPriorytet(String priorytet) {
        this.priorytet = priorytet;
    }

    public APPUSER getUser() {
        return user;
    }

    public void setUser(APPUSER user) {
        this.user = user;
    }
}