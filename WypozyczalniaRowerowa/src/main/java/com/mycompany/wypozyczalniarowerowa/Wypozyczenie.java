/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wypozyczalniarowerowa;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author demci
 */
public class Wypozyczenie implements Serializable{
    private int id;
    
    private int klientid;
    private String klient;
    private int rowerid;
    private String rower;
    
    private Date wypozyczenieod;
    private Date wypozyczeniedo;
    private Integer kosztwypozyczenia;
    private Boolean usuniete;
    
    public Wypozyczenie(){
        super();
    }

    public Wypozyczenie(int id, int klientid, int rowerid, String klient, String rower, Date wypozyczenieod, Date wypozyczeniedo, Integer kosztwypozyczenia, Boolean usuniete) {
        super();
        this.id = id;
        this.klientid = klientid;
        this.klient = klient;
        this.rowerid = rowerid;
        this.rower = rower;
        this.wypozyczenieod = wypozyczenieod;
        this.wypozyczeniedo = wypozyczeniedo;
        this.kosztwypozyczenia = kosztwypozyczenia;
        this.usuniete = usuniete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKlientId() {
        return klientid;
    }
     
    public void setKlientId(int klientid) {
        this.klientid = klientid;
    }
      
    public String getKlient() {
        return klient;
    }

    public void setKlient(String klient) {
        this.klient = klient;
    }

    public int getRowerId() {
        return rowerid;
    }
     
    public void setRowertId(int rowerid) {
        this.rowerid = rowerid;
    }
    
    public String getRower() {
        return rower;
    }

    public void setRower(String rower) {
        this.rower = rower;
    }

    public Date getWypozyczenieod() {
        return wypozyczenieod;
    }

    public void setWypozyczenieod(Date wypozyczenieod) {
        this.wypozyczenieod = wypozyczenieod;
    }

    public Date getWypozyczeniedo() {
        return wypozyczeniedo;
    }

    public void setWypozyczeniedo(Date wypozyczeniedo) {
        this.wypozyczeniedo = wypozyczeniedo;
    }

    public Integer getKosztwypozyczenia() {
        return kosztwypozyczenia;
    }

    public void setKosztwypozyczenia(Integer kosztwypozyczenia) {
        this.kosztwypozyczenia = kosztwypozyczenia;
    }

    public Boolean getUsuniete() {
        return usuniete;
    }

    public void setUsuniete(Boolean usuniete) {
        this.usuniete = usuniete;
    }  
}