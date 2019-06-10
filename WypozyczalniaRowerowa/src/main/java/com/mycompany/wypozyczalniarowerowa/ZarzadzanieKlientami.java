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
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author demci
 */
public class ZarzadzanieKlientami {    
    
    private ZarzadzanieBazaDanych zarzadzenieBazaDanych;
    
    public ZarzadzanieKlientami() {
        try{
            zarzadzenieBazaDanych = new ZarzadzanieBazaDanych();
        }
        catch(Throwable e){
            System.err.println("Wyjatek: " + e);
            throw new ExceptionInInitializerError(e);
        }  
    }
    public Integer dodajKlienta(String imie, String nazwisko, String nrdokumentu, String nrtelefonu){
        int klientID = 0;
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();
            
            PreparedStatement statement = connection.prepareStatement("SELECT dodajklienta(?, ?, ?, ?)");
            statement.setString(1, imie);
            statement.setString(2, nazwisko);
            statement.setString(3, nrdokumentu);
            statement.setString(4, nrtelefonu);
            ResultSet rs = statement.executeQuery(); 
            
            while(rs.next()) {
                klientID = rs.getInt(1);
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

        return klientID;        
    }
    
    public void usunKlienta(int id){
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT usunKlienta(?)");
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
    
    public void aktualizujKlienta(Klient klient){        
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT aktualizujKlienta(?, ?, ?, ?, ?)");
            statement.setInt(1, klient.getId());
            statement.setString(2, klient.getImie());
            statement.setString(3, klient.getNazwisko());
            statement.setString(4, klient.getNrdokumentu());
            statement.setString(5, klient.getNrtelefonu());
            
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
    
    public Klient pobierzKlienta (int id){
        Klient klient = null;
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT id, imie, nazwisko, nrdokumentu, nrtelefonu, usuniety FROM klienci WHERE id = ?");
            statement.setInt(1, id);            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                klient = new Klient(rs.getInt("id"), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("nrdokumentu"), rs.getString("nrtelefonu"), rs.getBoolean("usuniety"));
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
        
        return klient;
    }
    
    public List<Klient> pobierzKlientow(){
        List<Klient> listaklientow = new ArrayList<>();
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, imie, nazwisko, nrdokumentu, nrtelefonu, usuniety FROM klienci WHERE usuniety = false ORDER BY id DESC"); 
            
            while(rs.next()) {
                Klient klient = new Klient(rs.getInt("id"), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("nrdokumentu"), rs.getString("nrtelefonu"), false);
                listaklientow.add(klient);
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
        
        return listaklientow;
    }   
}