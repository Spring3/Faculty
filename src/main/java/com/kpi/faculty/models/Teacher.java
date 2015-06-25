package com.kpi.faculty.models;

/**
 * Created by user on 6/26/2015.
 */
public class Teacher extends Human{

    public Teacher(String name, String lastName){
        setName(name);
        setLastName(lastName);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
