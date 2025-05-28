/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author sandhya sapkota
 */
public class Validation {
//    private static final String phoneRegex = "^(98|97)[0-9]{8}$";
    private static final String usernameRegex = "^[A-Za-z0-9]+$"; // At least 3 alphanumeric characters

    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

//    private static final String passwordRegex = "^(?=.[A-Z])(?=.[a-z])(?=.\\d)(?=.[@#$!%?&])[A-Za-z\\d@#$!%?&]{8,}$";
    private static final String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$!%*?&_-]).{8,}$";


    public boolean isValidusername(String name){
        return name.matches(usernameRegex);
    }
    
    public boolean isValidEmail(String email){
        return email.matches(emailRegex);
    }
    
    public boolean isValidPassword(String password){
        return password.matches(passwordRegex);
    }
}


