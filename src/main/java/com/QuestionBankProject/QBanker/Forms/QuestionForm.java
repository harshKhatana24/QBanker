package com.QuestionBankProject.QBanker.Forms;


import com.QuestionBankProject.QBanker.Entities.QuestionOptions;
import com.QuestionBankProject.QBanker.Helper.Options;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class QuestionForm {

    @NotBlank
    private String questionText;
    @Column(nullable = false)
    private boolean isMCQ;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @NotBlank
    private String topic;
    @NotBlank
    private String solution;
    private MultipartFile questionImg;
    private String marks;
    private List<String> correctMCQAnswer;

    public List<String> getCorrectMCQAnswer() {
        return correctMCQAnswer;
    }

    public void setCorrectMCQAnswer(List<String> correctMCQAnswer) {
        this.correctMCQAnswer = correctMCQAnswer;
    }

    public MultipartFile getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(MultipartFile questionImg) {
        this.questionImg = questionImg;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public @NotBlank String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(@NotBlank String questionText) {
        this.questionText = questionText;
    }



    public @NotBlank String getTopic() {
        return topic;
    }

    public void setTopic(@NotBlank String topic) {
        this.topic = topic;
    }

    public @NotBlank String getSolution() {
        return solution;
    }

    public void setSolution(@NotBlank String solution) {
        this.solution = solution;
    }


    public boolean isMCQ() {
        return isMCQ;
    }

    public void setMCQ(@NotBlank boolean MCQ) {
        isMCQ = MCQ;
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
        return "QuestionForm{" +
                "questionText='" + questionText + '\'' +
                ", correctAnswer='" + '\'' +
                ", topic='" + topic + '\'' +
                ", solution='" + solution + '\'' +
                '}';
    }
}
