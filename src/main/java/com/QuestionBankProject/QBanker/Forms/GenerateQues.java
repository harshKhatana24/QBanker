package com.QuestionBankProject.QBanker.Forms;

import com.QuestionBankProject.QBanker.Entities.Question;

import java.util.ArrayList;
import java.util.List;


public class GenerateQues {

    private List<String> listOfQuestion=new ArrayList<>();

    private int noOfSets;

    public List<String> getListOfQuestion() {
        return listOfQuestion;
    }

    public void setListOfQuestion(List<String> listOfQuestion) {
        this.listOfQuestion = listOfQuestion;
    }

    public int getNoOfSets() {
        return noOfSets;
    }

    public void setNoOfSets(int noOfSets) {
        this.noOfSets = noOfSets;
    }
}
