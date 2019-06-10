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
public class Rower implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String model;
    private Integer stawka;
    private Boolean usuniety;    
    
    public Rower (){
        super();
    }

    public Rower(int id, String model, Integer stawka, Boolean usuniety) {
        super();
        this.id = id;
        this.model = model;
        this.stawka = stawka;
        this.usuniety = usuniety;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getStawka() {
        return stawka;
    }

    public void setStawka(Integer stawka) {
        this.stawka = stawka;
    }

    public Boolean getUsuniety() {
        return usuniety;
    }

    public void setUsuniety(Boolean usuniety) {
        this.usuniety = usuniety;
    }    
}