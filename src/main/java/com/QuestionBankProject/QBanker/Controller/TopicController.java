package com.QuestionBankProject.QBanker.Controller;


import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Forms.TopicForm;
import com.QuestionBankProject.QBanker.Services.Impl.TopicServices;
import com.QuestionBankProject.QBanker.Repository.TopicRepository;
import com.QuestionBankProject.QBanker.Entities.Topics;
import com.QuestionBankProject.QBanker.Services.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/teacher/QB")
public class TopicController {

    @Autowired
    private TopicServices topicServices;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionBankService questionBankService;


    @RequestMapping(value = "/addQuestion/{question_bank_id}/addTopic", method = RequestMethod.POST)
    public String addTopic(
            @ModelAttribute("topicForm") TopicForm topicForm,
            @PathVariable String question_bank_id,
            Model model,
            BindingResult result,
            HttpSession session) {


        model.addAttribute("question_bank_id", question_bank_id);

        QuestionBank questionBank=questionBankService.getQuestionBankById(question_bank_id);

        // Check if the topic name is unique using the service method
        if (!topicServices.isTopicUnique(topicForm.getTopicName())) {
            // Add an error if the topic is not unique
            result.rejectValue("topicName", "error.topicForm", "Topic Name is already in the list");
            return "teacher/topicForm"; // Return to the form page with the error
        }

        // Save the new topic if it is unique
        Topics topic = new Topics();
        topic.setTopicsId(UUID.randomUUID().toString());
        topic.setTopicName(topicForm.getTopicName());
        topic.setQuestionBank(questionBank);
        // Add additional logic to link the topic to the question bank if needed
        topicRepository.save(topic);

        session.setAttribute("message", "Topic added successfully!");
        return "redirect:/teacher/QB/addQuestion/" + question_bank_id + "/addTopic";
    }






}
