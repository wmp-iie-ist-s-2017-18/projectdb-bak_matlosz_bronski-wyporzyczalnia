/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wypozyczalniarowerowa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author demci
 */
public class ZarzadzanieBazaDanych {
    
    public Connection getConnection(){
        String adres = "jdbc:postgresql://localhost:5432/WypozyczalniaRowerowa";
        Properties ustawienia = new Properties();
        ustawienia.setProperty("user", "operator");
        ustawienia.setProperty("password", "operator");
        try {
            return DriverManager.getConnection(adres, ustawienia);
        } catch (SQLException ex) {
            Logger.getLogger(ZarzadzanieBazaDanych.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
