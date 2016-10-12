package com.mkyong.transport;
import javax.persistence.*;

@Entity
@Table(name = "TRASA_POJAZD")
public class TRASAPOJAZD implements java.io.Serializable {

    @Id
    @SequenceGenerator(name = "TRASA_POJAZD_SEQ", sequenceName = "TRASA_POJAZD_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRASA_POJAZD_SEQ")
    @Column(name = "ID_TRASY_POJAZD")
    private int trasaPojazdId;

    @JoinColumn(name = "ID_TRASY")
    @ManyToOne
    private TRASA trasa;

    @JoinColumn(name = "ID_POJAZDU")
    @ManyToOne
    private POJAZD pojazd;

}