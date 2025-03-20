package com.QuestionBankProject.QBanker.Controller;


import com.QuestionBankProject.QBanker.Entities.Question;
import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Forms.QuestionForm;
import com.QuestionBankProject.QBanker.Repository.QuestionBankRepository;
import com.QuestionBankProject.QBanker.Repository.QuestionRepository;
import com.QuestionBankProject.QBanker.Services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionServices questionServices;

    @Autowired
    private QuestionBankRepository questionBankRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/add")
    public String showAddQuestionForm(Model model){
        model.addAttribute("question",new QuestionForm());
        return "teacher/questions/questionForm";
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute QuestionForm questionForm){

        System.out.println(questionForm);

        Question question = new Question();
        question.setQuestionText(questionForm.getQuestionText());
//        question.setCorrectAnswer(questionForm.getCorrectAnswer());
        question.setTopic(questionForm.getTopic());
        question.setSolution(questionForm.getSolution());


//        questionServices.saveQuestion(question);


        return "redirect:/questions/add";
    }




}
