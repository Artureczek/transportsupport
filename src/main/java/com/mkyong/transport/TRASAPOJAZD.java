package com.mkyong.transport;
import javax.persistence.*;

@Entity
@Table(name = "TRASA_POJAZD")
public class TRASAPOJAZD implements java.io.Serializable {

    @Id
    @SequenceGenerator(name = "TRASA_POJAZD_SEQ", sequenceName = "TRASA_POJAZD_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRASA_POJAZD_SEQ")
    @Column(name = "ID_TRASY_POJAZD")
    private Long trasaPojazdId;

    @JoinColumn(name = "ID_TRASY")
    @ManyToOne
    private TRASA trasa;

    @JoinColumn(name = "ID_POJAZDU")
    @ManyToOne
    private POJAZD pojazd;

    @Column(name = "KOSZT_PALIWA")
    private Double kosztPaliwa;

    public TRASAPOJAZD() {
    }

    public TRASAPOJAZD(TRASA trasa, POJAZD pojazd, Double kosztPaliwa) {
        this.trasa = trasa;
        this.pojazd = pojazd;
        this.kosztPaliwa = kosztPaliwa;
    }

    public Long getTrasaPojazdId() {
        return trasaPojazdId;
    }

    public void setTrasaPojazdId(Long trasaPojazdId) {
        this.trasaPojazdId = trasaPojazdId;
    }

    public TRASA getTrasa() {
        return trasa;
    }

    public void setTrasa(TRASA trasa) {
        this.trasa = trasa;
    }

    public POJAZD getPojazd() {
        return pojazd;
    }

    public void setPojazd(POJAZD pojazd) {
        this.pojazd = pojazd;
    }

    public Double getKosztPaliwa() {
        return kosztPaliwa;
    }

    public void setKosztPaliwa(Double kosztPaliwa) {
        this.kosztPaliwa = kosztPaliwa;
    }
}