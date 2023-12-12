package com.example.entities;

import com.example.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "uzytkownicy")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imie;
    private String nazwisko;
    private String mail;
    private String haslo;
    private int rokst;
    private String kierunek;
    private int grupa;
    private String plec;
    private UserRole role;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return this.imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return this.nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }


    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getHaslo() {
        return this.haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int getRokst() {
        return this.rokst;
    }

    public void setRokst(int rokst) {
        this.rokst = rokst;
    }

    public String getKierunek() {
        return this.kierunek;
    }

    public void setKierunek(String kierunek) {
        this.kierunek = kierunek;
    }

    public int getGrupa() {
        return this.grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getPlec() {
        return this.plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }


    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
