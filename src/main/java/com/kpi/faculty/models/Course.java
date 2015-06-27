package com.kpi.faculty.models;

public class Course {

    private int id;
    private String name;
    private Human teacher;

    public Course(){

    }

    public Course(String name, Human teacher){
        setName(name);
        setTeacher(teacher);
    }

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

    public Human getTeacher() {
        return teacher;
    }

    public void setTeacher(Human teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (getId() != course.getId()) return false;
        if (getName() != null ? !getName().equals(course.getName()) : course.getName() != null) return false;
        return !(getTeacher() != null ? !getTeacher().equals(course.getTeacher()) : course.getTeacher() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        return result;
    }
}
