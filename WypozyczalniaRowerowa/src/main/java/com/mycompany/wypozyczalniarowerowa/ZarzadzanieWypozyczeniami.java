/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wypozyczalniarowerowa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author demci
 */
public class ZarzadzanieWypozyczeniami {      
    private ZarzadzanieBazaDanych zarzadzenieBazaDanych;
    
    public ZarzadzanieWypozyczeniami(){
        try{
            zarzadzenieBazaDanych = new ZarzadzanieBazaDanych();
        }
        catch(Throwable e){
            System.err.println("Wyjatek: " + e);
            throw new ExceptionInInitializerError(e);
        }  
    }
    
    public Integer dodajWypozyczenie(int klientid, int rowerid, Date wypozyczenieod) {
        int wypozyczenieID = 0;
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();  
            PreparedStatement statement = connection.prepareStatement("SELECT dodajWypozyczenie(?, ?, ?)");
            statement.setTimestamp(1, new java.sql.Timestamp(wypozyczenieod.getTime()));
            statement.setInt(2, klientid);
            statement.setInt(3, rowerid);
            ResultSet rs = statement.executeQuery(); 
            
            while(rs.next()) {
                wypozyczenieID = rs.getInt(1);
            }

            statement.close();
            connection.close();
        }
        catch (SQLException e){
            System.out.println("Błąd zapytania: " + e);
        }
        catch(Exception e){
            System.err.println("Wyjatek: " + e);
        }
        
        return wypozyczenieID;
    }
    
    public void usunWypozyczenie(int id){        
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT usunWypozyczenie(?)");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(); 

            statement.close();
            connection.close();
        }
         catch (SQLException e){
            System.out.println("Błąd zapytania: " + e);
        }
        catch(Exception e){
            System.err.println("Wyjatek: " + e);
        }
    }
    
    public void aktualizujWypozyczenie(Wypozyczenie wypozyczenie){        
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT aktualizujWypozyczenie(?, ?, ?, ?)");
            statement.setInt(1, wypozyczenie.getId());
            statement.setInt(2, wypozyczenie.getKosztwypozyczenia());
            statement.setTimestamp(3, new java.sql.Timestamp(wypozyczenie.getWypozyczenieod().getTime()));
            statement.setTimestamp(4, new java.sql.Timestamp(wypozyczenie.getWypozyczenieod().getTime()));
            
            ResultSet rs = statement.executeQuery(); 

            statement.close();
            connection.close();
        }
         catch (SQLException e){
            System.out.println("Błąd zapytania: " + e);
        }
        catch(Exception e){
            System.err.println("Wyjatek: " + e);
        }
    }
    
    public List<Wypozyczenie> pobierzWypozyczenia(){
        List<Wypozyczenie> listawypozyczen = new ArrayList<>();
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
"SELECT w.id, concat(k.imie, ' ', k.nazwisko) AS klient, r.model AS rower, kosztwypozyczenia, wypozyczenieod, wypozyczeniedo, klientid, rowerid, usuniete FROM wypozyczenia AS w \n" +
"JOIN klienci AS k ON k.id = w.klientid\n" +
"JOIN rowery AS r ON r.id = w.rowerid\n" +
"WHERE usuniete = false ORDER BY id DESC"); 
            
            while(rs.next()) {
                Integer kosztwypozyczenia = null;
                        
                int intkosztwypozyczenia = rs.getInt("kosztwypozyczenia");
                if (!rs.wasNull()) {
                    kosztwypozyczenia = intkosztwypozyczenia;
                }    
                
                Date wypozyczeniedo = null;
                Timestamp timewypozyczeniedo = rs.getTimestamp("wypozyczeniedo");
                if (!rs.wasNull()) {
                    wypozyczeniedo = new Date(rs.getTimestamp("wypozyczeniedo").getTime());
                }
    
                Wypozyczenie wypozyczenie = new Wypozyczenie(rs.getInt("id"), rs.getInt("klientid"), rs.getInt("rowerid"), rs.getString("klient"), rs.getString("rower"), new Date(rs.getTimestamp("wypozyczenieod").getTime()), wypozyczeniedo, kosztwypozyczenia, false);
                listawypozyczen.add(wypozyczenie);
            }

            statement.close();
            connection.close();
        }
        catch (SQLException e){
            System.out.println("Błąd zapytania: " + e);
        }
        catch(Exception e){
            System.err.println("Wyjatek: " + e);
        }
        
        return listawypozyczen;
    }    
}