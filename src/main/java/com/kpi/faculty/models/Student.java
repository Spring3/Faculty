package com.kpi.faculty.models;


/**
 * Created by user on 6/26/2015.
 */
public class Student extends Human {

    public Student(){

    }

    public Student (String username, String password, String name, String lastName){
        setUsername(username);
        setPassword(encodePassword(password));
        setName(name);
        setLastName(lastName);
        setRole(Role.STUDENT);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
