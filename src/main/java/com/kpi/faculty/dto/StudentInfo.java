package com.kpi.faculty.dto;

import com.kpi.faculty.models.Course;

public class StudentInfo {

    private String name;
    private String lastName;
    private String username;
    private String feedback;
    private String mark;
    private Course course;

    public StudentInfo(String name, String lastName, String username, String feedback, String mark, Course course){
        setName(name);
        setLastName(lastName);
        setUsername(username);
        setFeedback(feedback);
        setMark(mark);
        setCourse(course);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public Course getCourse(){
        return course;
    }




}
