package com.mkyong.transport;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "TRASA")
public class TRASA implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="TRASA_SEQ", sequenceName="TRASA_SEQ", allocationSize=1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator="TRASA_SEQ")
    @Column(name = "ID_TRASY")
    private Long trasaId;

    @Column(name = "ODLEGLOSC")
    private Double odleglosc;

    @Column(name = "LOKACJA_STARTOWA")
    private String lokacjaStartowa;

    @Column(name = "LOKACJA_KONCOWA")
    private String lokacjaKoncowa;

    @Column(name = "PRIORYTET")
    private String priorytet;

    @JsonIgnore
    @OneToMany(mappedBy = "trasa", fetch = FetchType.EAGER)
    private Set<TRASAPOJAZD> pojazdy = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trasa", fetch = FetchType.EAGER)
    private Set<TRASAPRACOWNIK> kierowcy = new HashSet<>();

    @JoinColumn(name = "ID_USER")
    @ManyToOne
    private APPUSER user;

    public TRASA(Double odleglosc, String lokacjaStartowa, String lokacjaKoncowa, String priorytet, APPUSER user) {
        this.odleglosc = odleglosc;
        this.lokacjaStartowa = lokacjaStartowa;
        this.lokacjaKoncowa = lokacjaKoncowa;
        this.priorytet = priorytet;
        this.user = user;
    }

    public TRASA() {    }

    public Long getTrasaId() {        return trasaId;    }

    public void setTrasaId(Long trasaId) {        this.trasaId = trasaId;    }

    public Double getOdleglosc() {        return odleglosc;    }

    public void setOdleglosc(Double odleglosc) {
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

    public Set<TRASAPOJAZD> getPojazdy() {
        return pojazdy;
    }

    public void setPojazdy(Set<TRASAPOJAZD> pojazdy) {
        this.pojazdy = pojazdy;
    }

    public Set<TRASAPRACOWNIK> getKierowcy() {
        return kierowcy;
    }

    public void setKierowcy(Set<TRASAPRACOWNIK> kierowcy) {
        this.kierowcy = kierowcy;
    }
}