package com.example.vertonix.loginrequery.database;


import io.requery.Entity;
import io.requery.Key;

@Entity
public interface UserDetails {
    @Key
    public String getUserId();

    public String getFirstName();

    public String getLastName();

    public String getEmail();

    public String getPassword();

    public String getConfirmPassword();

}

