package com.kpi.faculty.models;

/**
 * Created by user on 6/26/2015.
 */
public class Teacher extends Human{

    public Teacher(){

    }

    public Teacher(String username, String password, String name, String lastName){
        setUsername(username);
        setPassword(encodePassword(password));
        setName(name);
        setLastName(lastName);
        setRole(Role.TEACHER);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
