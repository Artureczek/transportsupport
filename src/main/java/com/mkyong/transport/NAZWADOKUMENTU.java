package com.mkyong.transport;

import javax.persistence.*;


@Entity
@Table(name = "NAZWA_DOKUMENTU")
public class NAZWADOKUMENTU implements java.io.Serializable {

    @Id
    @Column(name = "ID_NAZWY_DOKUMENTU")
    private Long appUserId;

    @Column(name = "NAZWA")
    private String nazwaDokumentu;


    public enum DokumentNazwa {
        KWESTIONARIUSZ_OSOBOWY("Kwestionariusz osobowy"),
        PROKURATURA("Swiadectwo Pracy"),
        POTWIERDZENIE_KWALIFIKACJI("Potwierdzenie Kwalifikacji"),
        SWIADECTWO_UKONCZENIA_SZKOLY("Swiadectwo Ukonczenia Szkoly"),
        ORZECZENIE_LEKARSKIE("Orzeczenie Lekarskie");

        private final String nazwa;

        private DokumentNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public String value() {
            return this.nazwa;
        }
    }


    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getNazwaDokumentu() {
        return nazwaDokumentu;
    }

    public void setNazwaDokumentu(String nazwaDokumentu) {
        this.nazwaDokumentu = nazwaDokumentu;
    }

    public NAZWADOKUMENTU() {
    }

    public NAZWADOKUMENTU(Long appUserId, String nazwaDokumentu) {
        this.appUserId = appUserId;
        this.nazwaDokumentu = nazwaDokumentu;
    }
}