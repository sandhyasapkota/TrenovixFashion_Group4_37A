/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sahki
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    
    public User(String username, String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
//    public static void main(String[] args) {
//        
//    }
//    
}



