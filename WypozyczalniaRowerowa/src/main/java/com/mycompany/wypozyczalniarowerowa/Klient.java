/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wypozyczalniarowerowa;

import java.io.Serializable;
/**
 *
 * @author demci
 */
public class Klient implements Serializable {

    private static final long serialVersionUID = 1L;
            
    private Integer id;
    private String imie;
    private String nazwisko;
    private String nrdokumentu;
    private String nrtelefonu;
    private Boolean usuniety;

    public Klient (){
        super();
    }

    public Klient(int id, String imie, String nazwisko, String nrdokumentu, String nrtelefonu, Boolean usuniety) {
        super();
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrdokumentu = nrdokumentu;
        this.nrtelefonu = nrtelefonu;
        this.usuniety = usuniety;        
    }
    
    public Integer getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getNrdokumentu() {
        return nrdokumentu;
    }

    public String getNrtelefonu() {
        return nrtelefonu;
    }

    public Boolean getUsuniety() {
        return usuniety;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setNrdokumentu(String nrdokumentu) {
        this.nrdokumentu = nrdokumentu;
    }

    public void setNrtelefonu(String nrtelefonu) {
        this.nrtelefonu = nrtelefonu;
    }

    public void setUsuniety(Boolean usuniety) {
        this.usuniety = usuniety;
    }
}