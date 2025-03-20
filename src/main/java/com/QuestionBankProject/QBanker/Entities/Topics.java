package com.QuestionBankProject.QBanker.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Topics {

    @Id
    private String topicsId;
    @Column(unique = true)
    private String topicName;

    @ManyToOne
    private QuestionBank questionBank;

    public String getTopicsId() {
        return topicsId;
    }

    public void setTopicsId(String topicsId) {
        this.topicsId = topicsId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    @Override
    public String toString() {
        return "Topics{" +
                "topicsId='" + topicsId + '\'' +
                ", topicName='" + topicName + '\'' +
                ", questionBank=" + questionBank +
                '}';
    }
}
