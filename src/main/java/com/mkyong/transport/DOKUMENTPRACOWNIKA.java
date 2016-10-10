package com.mkyong.transport;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "DOKUMENT_PRACOWNIKA")
public class DOKUMENTPRACOWNIKA implements java.io.Serializable {

    @Id
    @SequenceGenerator(name="DOKUMENT_PRACOWNIKA_SEQ", sequenceName="DOKUMENT_PRACOWNIKA_SEQ", allocationSize=1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator="DOKUMENT_PRACOWNIKA_SEQ")
    @Column(name = "ID_DOKUMENTU")
    private int dokumentId;

    @Lob
    @Column(name = "DOKUMENT")
    private byte[] dokument;

    @Column(name = "AKTYWNY")
    private Boolean aktywny;

    @Column(name = "NAZWA_DOKUMENTU")
    private String nazwa;

    @Column(name = "ROZSZERZENIE_DOKUMENTU")
    private String rozszerzenieDokumentu;

    @JoinColumn(name = "ID_PRACOWNIKA")
    @ManyToOne
    private PRACOWNIK pracownik;

    @JoinColumn(name = "ID_NAZWY_DOKUMENTU")
    @ManyToOne
    private NAZWADOKUMENTU nazwadokumentu;

    public DOKUMENTPRACOWNIKA() {
    }

    public DOKUMENTPRACOWNIKA(int dokumentId, byte[] dokument, Boolean aktywny, PRACOWNIK pracownik, NAZWADOKUMENTU nazwadokumentu) {
        this.dokumentId = dokumentId;
        this.dokument = dokument;
        this.aktywny = aktywny;
        this.pracownik = pracownik;
        this.nazwadokumentu = nazwadokumentu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRozszerzenieDokumentu() {
        return rozszerzenieDokumentu;
    }

    public void setRozszerzenieDokumentu(String rozszerzenieDokumentu) {
        this.rozszerzenieDokumentu = rozszerzenieDokumentu;
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

    public Boolean getAktywny() {
        return aktywny;
    }

    public void setAktywny(Boolean aktywny) {
        this.aktywny = aktywny;
    }

    public PRACOWNIK getPracownik() {
        return pracownik;
    }

    public void setPracownik(PRACOWNIK pracownik) {
        this.pracownik = pracownik;
    }

    public NAZWADOKUMENTU getNazwadokumentu() {
        return nazwadokumentu;
    }

    public void setNazwadokumentu(NAZWADOKUMENTU nazwadokumentu) {
        this.nazwadokumentu = nazwadokumentu;
    }
}