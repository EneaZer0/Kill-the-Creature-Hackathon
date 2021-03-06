package com.example.hospitapp;

import java.io.Serializable;
/**
 * Public class, which represents the users of the app, each instance will store all the information regarding the id, username ,password and personal information of a Hospital.
 */
public class ProviderUser implements Serializable {

    /**
     * Field (int) used to store the reference of a given user in the database.
     */
    private int id;
    /**
     * Field (String) used to store the name of the user.
     */
    private String name;
    /**
     * Field (String) used to store the username of the user.
     */
    private String username;
    /**
     * Field (String) used to store the password of the user.
     */
    private String password;
    /**
     * Field (String) used to store the Street name of the user.
     */
    private String direction;
    /**
     * Field (String) used to store the email address of the user.
     */
    private String email;
    /**
     * Field (String) used to store the phone Number of the user.
     */
    private String phoneNumber;
    /**
     * Field (String) used to store the Street's address of the user.
     */
    private String numberAddress;
    /**
     * Field (String) used to store the zip code of the user.
     */
    private String zipCode;
    /**
     * Field (String) used to store the name of the city of the user.
     */
    private String city;
    /**
     * Field (String) used to store the description of the user.
     */
    private String description;
    /**
     * Public constructor of the class, used to create a user object.
     * It will update all the private fields of the class.
     * @param id reference of the user (hospital) in the database.
     * @param name name of the user.
     * @param username username of the hospital.
     * @param direction Street name of the user.
     * @param email email address of the user.
     * @param phoneNumber phone number of the user.
     * @param numberAddress number of the Street of the user.
     * @param zipCode Zip code of the user.
     * @param city name of the user's city.
     * @param description description of the user.
     */
    public ProviderUser(int id, String name, String username, String direction, String email, String phoneNumber, String numberAddress, String zipCode, String city, String description) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.direction = direction;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numberAddress = numberAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.description = description;
    }


    /*      GETTER FUNCTIONS       */
    /**
     * Public function used to make available the name of the user.
     * @return the name of the user (String).
     */
    public String getName() {
        return name;
    }
    /**
     * Public function used to make available the email address of the user.
     * @return the email of the user (String).
     */
    public String getEmail() {
        return email;
    }
    /**
     * Public function used to make available the id of the user.
     * @return the id of the user (int).
     */
    public int getId() {
        return id;
    }
    /**
     * Public function used to make available the username of the user.
     * @return the username of the user (String).
     */

    public String getUsername() {
        return username;
    }
    /**
     * Public function used to make available the name of the street of the user.
     * @return the name of the street of the user (String).
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Public function used to make available the Street number of the user.
     * @return the Street number of the user (String).
     */
    public String getNumberAddress() {
        return numberAddress;
    }
    /**
     * Public function used to make available the zip code of the user.
     * @return the zip code of the user (String).
     */

    public String getZipCode() {
        return zipCode;
    }
    /**
     * Public function used to make available the name of the city  of the user.
     * @return the  name of the city  of the user (String).
     */

    public String getCity() {
        return city;
    }
    /**
     * Public function used to make available the phone number of the user.
     * @return the the phone number of the user (String).
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Public function used to make available the description of the user.
     * @return the description of the user (String)
     */
    public String getDescription() {
        return description;
    }

    /*        SETTER FUNCTIONS       */
    /**
     * Public function used to redefine the id of the user.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Public function used to redefine the username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
