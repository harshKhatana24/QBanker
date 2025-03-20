package com.QuestionBankProject.QBanker.Entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class QuestionBank {

    @Id
    private String id;

    private String name;

    private String teacherName;


    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topics> topics;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Question> questions;  // List of questions for this question bank

    public List<Topics> getTopics() {
        return topics;
    }

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public QuestionBank(String id, String name, String teacherName, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        this.teacher = teacher;
    }

    public QuestionBank() {
    }
}
