package com.kpi.faculty.models;

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
