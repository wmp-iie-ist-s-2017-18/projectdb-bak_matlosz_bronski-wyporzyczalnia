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
public class ZarzadzanieRowerami {
    
    private ZarzadzanieBazaDanych zarzadzenieBazaDanych;
    
    public ZarzadzanieRowerami() {
        try{
            zarzadzenieBazaDanych = new ZarzadzanieBazaDanych();
        }
        catch(Throwable e){
            System.err.println("Wyjatek: " + e);
            throw new ExceptionInInitializerError(e);
        }        
    }
    
    public int dodajRower(String model, int stawka){
        int rowerID = 0;
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT dodajRower(?, ?)");
            statement.setString(1, model);
            statement.setInt(2, stawka);
            ResultSet rs = statement.executeQuery(); 
            
            while(rs.next()) {
                rowerID = rs.getInt(1);
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

        return rowerID;        
    }    
    
    public void usunRower(int id){        
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT usunRower(?)");
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
    
    public void aktualizujRower(Rower rower){        
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT aktualizujRower(?, ?, ?)");
            statement.setInt(1, rower.getId());
            statement.setString(2, rower.getModel());
            statement.setInt(3, rower.getStawka());
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
    
    public Rower pobierzRower (int id){
        Rower rower = null;
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            PreparedStatement statement = connection.prepareStatement("SELECT id, model, stawka, usuniety FROM rowery WHERE id = ?");
            statement.setInt(1, id);            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                rower = new Rower(rs.getInt("id"), rs.getString("model"), rs.getInt("stawka"), rs.getBoolean("usuniety"));
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
        
       return rower;
    }
    
    public List<Rower> pobierzRowery(){
        List<Rower> listarowerow = new ArrayList<>();        
        try {  
            Connection connection = zarzadzenieBazaDanych.getConnection();            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, model, stawka FROM rowery WHERE usuniety = false ORDER BY id DESC"); 
            
            while(rs.next()) {
                Rower rower = new Rower(rs.getInt("id"), rs.getString("model"), rs.getInt("stawka"), false);
                listarowerow.add(rower);
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
 
        return listarowerow;
    }    
}