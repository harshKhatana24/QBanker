package com.QuestionBankProject.QBanker.Controller;


import com.QuestionBankProject.QBanker.Forms.GenerateQues;
import com.QuestionBankProject.QBanker.Entities.Question;
import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Entities.Teacher;
import com.QuestionBankProject.QBanker.Helper.AppConstants;
import com.QuestionBankProject.QBanker.Helper.Helper;
import com.QuestionBankProject.QBanker.Repository.QuestionBankRepository;
import com.QuestionBankProject.QBanker.Repository.QuestionRepository;
import com.QuestionBankProject.QBanker.Repository.TopicRepository;
import com.QuestionBankProject.QBanker.Services.QuestionBankService;
import com.QuestionBankProject.QBanker.Services.QuestionServices;
import com.QuestionBankProject.QBanker.Services.TeacherServices;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher/QB")
public class QuestionGenerationController {


    private final TeacherServices teacherServices;
    private final QuestionBankService questionBankService;
    private final QuestionServices questionServices;
    private final TopicRepository topicRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images";
    private final QuestionBankRepository questionBankRepository;
    private final QuestionRepository questionRepository;

    public QuestionGenerationController(TeacherServices teacherServices, QuestionBankService questionBankService,
                                        QuestionServices questionServices, QuestionBankRepository questionBankRepository,
                                        TopicRepository topicRepository, QuestionRepository questionRepository){
        this.questionBankService = questionBankService;
        this.teacherServices = teacherServices;
        this.questionServices = questionServices;
        this.questionBankRepository = questionBankRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    //generate Question Paper
    @GetMapping("/generate/{question_bank_id}")
    public String viewGenerate(@PathVariable("question_bank_id") String questionBankId,
                               Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = AppConstants.CONTACT_PAZE_SIZE) int size,
                               @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                               @RequestParam(value = "direction", defaultValue = "asc") String direction){

        System.out.println(questionBankId);

        QuestionBank questionBank = questionBankService.getQuestionBankById(questionBankId);

        model.addAttribute("questionBank",questionBank);
        model.addAttribute("questionBankId",questionBankId);


        Page<Question> questionsPage=questionServices.getByQuestionBank(questionBank,page,size,sortBy,direction);

        model.addAttribute("questionPage",questionsPage);
        model.addAttribute("previousPage",questionsPage.getNumber()-1);
        model.addAttribute("nextPage",questionsPage.getNumber()+1);
        model.addAttribute("pageSize",AppConstants.CONTACT_PAZE_SIZE);
        model.addAttribute("generateQuesList",new GenerateQues());


        List<Question> questionList=new ArrayList<>();
        questionList=questionRepository.findAllByQuestionBank(questionBank);

        GenerateQues requiredList=new GenerateQues();
        model.addAttribute("requiredList",requiredList);

        model.addAttribute("questionsList",questionList);

        return "teacher/generateQues";
    }


    @GetMapping("/generate")
    public String generate(@RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = AppConstants.CONTACT_PAZE_SIZE) int size,
                           @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                           @RequestParam(value = "direction", defaultValue = "asc") String direction,
                           Authentication authentication, Model model) {

        // Load question bank
        String username = Helper.getEmailOfLoggedInUser(authentication);
        Teacher teacher = teacherServices.getTeacherByEmail(username);

        // Get paginated question bank data
        Page<QuestionBank> questionBank = questionBankService.getByTeacher(teacher, page, size, sortBy, direction);

        // Add the question bank data to the model
        model.addAttribute("questionBank", questionBank);
        model.addAttribute("pageSize", AppConstants.CONTACT_PAZE_SIZE);
        model.addAttribute("question_bank_id");


        //list yha se bhaj denge jisme questions receive karenge
        model.addAttribute("generateQuesList",new GenerateQues());


        return "teacher/generateQues";
    }

    @RequestMapping(value = "/generate/{question_bank_id}",method = RequestMethod.POST)
    public String generateQuestionPaper(@PathVariable("question_bank_id") String questionBankId,
                                        @ModelAttribute GenerateQues generateQuesList,
                                        BindingResult bindingResult,
                                        Model model){

        if (bindingResult.hasErrors()){
            return "redirect:/teacher/QB/generate/" + questionBankId;
        }

        //aab mere ko yha par list of question Id's mil rhi hai aab inko ek naye template pe phuchate hai
        List<String> listOfQuesIds=generateQuesList.getListOfQuestion();

        List<Question> pdfQuestList=new ArrayList<>();
        for (String i:listOfQuesIds){
            Question question=questionServices.getQuestionById(i).orElse(null);
            if (question != null){
                pdfQuestList.add(question);
            }
        }

        model.addAttribute("pdfQuesList",pdfQuestList);
        model.addAttribute("noOfSets",generateQuesList.getNoOfSets());

        System.out.println();
        System.out.println(listOfQuesIds);
        System.out.println();

//        return "redirect:/teacher/QB/view/" + questionBankId;
        return "teacher/helper";
    }

}
