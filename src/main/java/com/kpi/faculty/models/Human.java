package com.kpi.faculty.models;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class Human {

    public enum Role{
        STUDENT,
        TEACHER
    }

    private int id;
    private String name;
    private String lastName;
    private String password;  //sha1Hex encrypted
    private String username;
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public Role getRole(){
        return role;
    }

    //Sha1Hex encryption method
    protected String encodePassword(String password){
        return DigestUtils.sha1Hex(password);
    }

    @Override
    public String toString() {
        return String.format("%s %s", getLastName(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        if (getId() != human.getId()) return false;
        if (getName() != null ? !getName().equals(human.getName()) : human.getName() != null) return false;
        if (getLastName() != null ? !getLastName().equals(human.getLastName()) : human.getLastName() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(human.getPassword()) : human.getPassword() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(human.getUsername()) : human.getUsername() != null)
            return false;
        return getRole() == human.getRole();

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }

}
