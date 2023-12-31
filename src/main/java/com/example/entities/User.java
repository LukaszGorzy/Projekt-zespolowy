package com.example.entities;

import com.example.dto.StudentDto;
import com.example.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    private String rokst;

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

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getRokst() {
        return rokst;
    }

    public void setRokst(String rokst) {
        this.rokst = rokst;
    }

    public String getKierunek() {
        return kierunek;
    }

    public void setKierunek(String kierunek) {
        this.kierunek = kierunek;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public StudentDto getStudentDto() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(id);
        studentDto.setMail(mail);
        studentDto.setImie(imie);
        studentDto.setNazwisko(nazwisko);
        studentDto.setKierunek(kierunek);
        studentDto.setPlec(plec);
        studentDto.setRokst(rokst);
        return studentDto;
    }
}
