/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
/**
 *
 * @author sandhya sapkota
 */

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String security_ans;
    
    public User(String username, String email,String password, String security_ans){
        this.username = username;
        this.email = email;
        this.password = password;
        this.security_ans = security_ans;
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
      public String getSecurity_ans() {
        return security_ans;
    }
    public void setSecurity_ans(String security_ans) {
        this.security_ans = security_ans;
    }
    
    public static void main(String[] args) {
        
    }
    
}



