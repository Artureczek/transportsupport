package com.mkyong.transport;
import javax.persistence.*;

@Entity
@Table(name = "TRASA_PRACOWNIK")
public class TRASAPRACOWNIK implements java.io.Serializable {

    @Id
    @SequenceGenerator(name = "TRASA_PRACOWNIK_SEQ", sequenceName = "TRASA_PRACOWNIK_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRASA_PRACOWNIK_SEQ")
    @Column(name = "ID_TRASY_PRACOWNIK")
    private Long trasaPojazdId;

    @JoinColumn(name = "ID_TRASY")
    @ManyToOne
    private TRASA trasa;

    @JoinColumn(name = "ID_PRACOWNIKA")
    @ManyToOne
    private PRACOWNIK pracownik;

    public TRASAPRACOWNIK() {
    }

    public TRASAPRACOWNIK(TRASA trasa, PRACOWNIK pracownik) {
        this.trasa = trasa;
        this.pracownik = pracownik;
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

    public PRACOWNIK getPracownik() {
        return pracownik;
    }

    public void setPracownik(PRACOWNIK pracownik) {
        this.pracownik = pracownik;
    }
}