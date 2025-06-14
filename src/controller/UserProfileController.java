package controller;

import dao.UserDao;
import dao.UserProfileDao;
import model.UserProfile;
import view.Userprofile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserProfileController {
    private final Userprofile view;
    private final int userId;
    private final UserProfileDao profileDao = new UserProfileDao();

    public UserProfileController(Userprofile view, int userId) {
        this.view = view;
        this.userId = userId;
        loadUserProfile();
        // Make sure the button listener is properly attached
        this.view.getSaveButton().addActionListener(new SaveListener());
    }
    

    private void loadUserProfile() {
        try {
            UserProfile profile = profileDao.getProfileByUserId(userId);
            if (profile != null) {
                view.getFullNameField().setText(profile.getFirstName() != null ? profile.getFirstName() : "");
                view.getLastNameField().setText(profile.getLastName() != null ? profile.getLastName() : "");
                view.getAgeField().setText(profile.getAge() > 0 ? String.valueOf(profile.getAge()) : "");
                view.getGenderField().setText(profile.getGender() != null ? profile.getGender() : "");
                view.getCountryField().setText(profile.getCountry() != null ? profile.getCountry() : "");
                view.getContactField().setText(profile.getContact() != null ? profile.getContact() : "");
                view.getAddressField().setText(profile.getAddress() != null ? profile.getAddress() : "");
                view.getEmailField().setText(profile.getEmail() != null ? profile.getEmail() : "");
            }
            
            // Set the username from users table
            UserDao userDao = new UserDao();
            String username = userDao.getUsernameById(userId);
            if (username != null) {
                view.getUserNameLabel().setText(username);
            } else {
                view.getUserNameLabel().setText("User " + userId);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error loading user profile: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                System.out.println("Save button clicked!"); // Debug message
                
                UserProfile profile = new UserProfile();
                profile.setUserId(userId);
                profile.setFirstName(view.getFullNameField().getText().trim());
                profile.setLastName(view.getLastNameField().getText().trim());
                
                // Handle age parsing with validation
                String ageText = view.getAgeField().getText().trim();
                if (!ageText.isEmpty()) {
                    try {
                        profile.setAge(Integer.parseInt(ageText));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(view, "Please enter a valid age", 
                                                    "Invalid Age", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    profile.setAge(0); // Set default value
                }
                
                profile.setGender(view.getGenderField().getText().trim());
                profile.setCountry(view.getCountryField().getText().trim());
                profile.setContact(view.getContactField().getText().trim());
                profile.setAddress(view.getAddressField().getText().trim());
                profile.setEmail(view.getEmailField().getText().trim());

                System.out.println("Attempting to save profile for user: " + userId); // Debug
                
                boolean success = profileDao.saveOrUpdateProfile(profile);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, "Profile updated successfully!", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Reload the profile to show updated data
                    loadUserProfile();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update profile. Please try again.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error saving profile: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}