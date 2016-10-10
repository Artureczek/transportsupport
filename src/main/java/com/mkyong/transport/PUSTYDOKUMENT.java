package com.mkyong.transport;

/**
 * Created by artur on 10.10.2016.
 */

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "PUSTY_DOKUMENT")
public class PUSTYDOKUMENT implements java.io.Serializable {


    @Id
    @Column(name = "ID_PUSTEGO_DOKUMENTU")
    private int dokumentId;

    @Lob
    @Column(name = "PUSTY_DOKUMENT")
    private byte[] dokument;

    public PUSTYDOKUMENT() {
    }

    public PUSTYDOKUMENT(int dokumentId, byte[] dokument) {
        this.dokumentId = dokumentId;
        this.dokument = dokument;
    }

    public int getDokumentId() {
        return dokumentId;
    }

    public void setDokumentId(int dokumentId) {
        this.dokumentId = dokumentId;
    }

    public byte[] getDokument() {
        return dokument;
    }

    public void setDokument(byte[] dokument) {
        this.dokument = dokument;
    }
}