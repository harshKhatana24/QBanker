package com.QuestionBankProject.QBanker.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    private String questionId; // Question ID (primary key)

    @Column(nullable = false)
    private String questionText; // The question text (renamed for clarity)

    @Column(nullable = false)
    private Boolean isMCQ; // Flag to identify if the question is MCQ or not

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctMCQAns;

    @Column
    private String topic; // Topic of the question

    @Column
    private String solution; // The solution to the question

    @Column
    private String marks;

    @Column
    private String questionImage;

    @ManyToOne
    @JoinColumn(name = "question_bank_id") // Explicitly naming the foreign key column
    private QuestionBank questionBank;

    // Getters and Setters


    public String getCorrectMCQAns() {
        return correctMCQAns;
    }

    public void setCorrectMCQAns(String correctMCQAns) {
        this.correctMCQAns = correctMCQAns;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean getMCQ() {
        return isMCQ;
    }

    public void setMCQ(Boolean MCQ) {
        this.isMCQ = MCQ;
    }



    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", isMCQ=" + isMCQ +
                ", correctAnswer='" +  '\'' +
                ", topic='" + topic + '\'' +
                ", solution='" + solution + '\'' +
                ", questionImage='" + questionImage + '\'' +
                ", questionBank=" + questionBank +
                '}';
    }
}
