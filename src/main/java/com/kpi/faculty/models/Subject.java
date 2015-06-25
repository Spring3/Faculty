package com.kpi.faculty.models;

/**
 * Created by user on 6/26/2015.
 */
public class Subject {

    private int id;
    private String name;
    private Teacher teacher;


    public Subject(String name, Teacher teacher){
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (getId() != subject.getId()) return false;
        if (!getName().equals(subject.getName())) return false;
        return getTeacher().equals(subject.getTeacher());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getTeacher().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }
}
