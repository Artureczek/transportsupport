package com.mkyong.transport;
import javax.persistence.*;

@Entity
@Table(name = "TRASA_PRACOWNIK")
public class TRASAPRACOWNIK implements java.io.Serializable {

    @Id
    @SequenceGenerator(name = "TRASA_PRACOWNIK_SEQ", sequenceName = "TRASA_PRACOWNIK_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRASA_PRACOWNIK_SEQ")
    @Column(name = "ID_TRASY_PRACOWNIK")
    private int trasaPojazdId;

    @JoinColumn(name = "ID_TRASY")
    @ManyToOne
    private TRASA trasa;

    @JoinColumn(name = "ID_PRACOWNIKA")
    @ManyToOne
    private PRACOWNIK pracownik;

}