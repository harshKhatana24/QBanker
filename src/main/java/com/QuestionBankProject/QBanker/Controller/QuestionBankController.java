package com.QuestionBankProject.QBanker.Controller;


import com.QuestionBankProject.QBanker.Entities.*;
import com.QuestionBankProject.QBanker.Forms.*;
import com.QuestionBankProject.QBanker.Helper.*;
import com.QuestionBankProject.QBanker.Repository.QuestionBankRepository;
import com.QuestionBankProject.QBanker.Repository.QuestionRepository;
import com.QuestionBankProject.QBanker.Repository.TopicRepository;
import com.QuestionBankProject.QBanker.Services.Impl.PdfGeneratorService;
import com.QuestionBankProject.QBanker.Services.QuestionBankService;
import com.QuestionBankProject.QBanker.Services.QuestionServices;
import com.QuestionBankProject.QBanker.Services.TeacherServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.SignStyle;
import java.util.*;

@Controller
@RequestMapping("/teacher/QB")
public class QuestionBankController {

        private final TeacherServices teacherServices;
        private final QuestionBankService questionBankService;
        private final QuestionServices questionServices;
        private final TopicRepository topicRepository;
        private final QuestionRepository questionRepository;

        private static final String UPLOAD_DIR = "src/main/resources/static/images";
        private final QuestionBankRepository questionBankRepository;

        public QuestionBankController(TeacherServices teacherServices, QuestionBankService questionBankService,
                                      QuestionServices questionServices, QuestionBankRepository questionBankRepository,
                                      TopicRepository topicRepository, QuestionRepository questionRepository){
                this.questionBankService = questionBankService;
                this.teacherServices = teacherServices;
                this.questionServices = questionServices;
                this.questionBankRepository = questionBankRepository;
                this.topicRepository = topicRepository;
            this.questionRepository = questionRepository;
        }

        //add question bank page : handler

        @RequestMapping("/add")
        public String addQuestionBank(Model model){

                QuestionBankForm form = new QuestionBankForm();
//                form.setName("xyz");
//                form.setTeacherName("pqr");
                model.addAttribute("questionBankForm", form);
                List<QuestionBank> listQuestionBank=questionBankRepository.findAll();
                List<Question> listQuestion=questionRepository.findAll();
                model.addAttribute("NoOfQuestions",listQuestion.size());
                model.addAttribute("NoOfQuestionBanks",listQuestionBank.size());
                Question question1=listQuestion.get(0);
                String id1=question1.getQuestionBank().getId();
                Question question2=listQuestion.get(1
                );
                String id2=question1.getQuestionBank().getId();

                model.addAttribute("question1",question1);
                model.addAttribute("id1",id1);
                model.addAttribute("question2",question2);
                model.addAttribute("id2",id2);
                System.out.println("check");

                return "teacher/mysubject";
        }


        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public String saveQuestionBank(@Valid @ModelAttribute QuestionBankForm questionBankForm,
                                       BindingResult bindingResult,
                                       Authentication authentication,
                                       HttpSession httpSession){

                System.out.println("check 1");
                //validation
                if (bindingResult.hasErrors()){
                        bindingResult.getAllErrors().forEach(errors -> System.out.println(errors));

                        Message message = new Message();
                        message.setType(MessageType.red);
                        message.setContent("Please correct the following errors");

                        httpSession.setAttribute("message",message);
                        return "teacher/mysubject";
                }
                System.out.println("check 2");

                //process the form data
                String username = Helper.getEmailOfLoggedInUser(authentication);

                Teacher teacher=teacherServices.getTeacherByEmail(username);

                QuestionBank bank = new QuestionBank();
                bank.setName(questionBankForm.getName());
                bank.setTeacherName(questionBankForm.getTeacherName());

                bank.setTeacher(teacher);

                questionBankService.save(bank);
                System.out.println(questionBankForm);

                Message message1 = new Message();
                message1.setType(MessageType.green);
                message1.setContent("Contact added successfully !");

                httpSession.setAttribute("message", message1);

                return "redirect:/teacher/QB/add";
        }


        //view question bank

        @RequestMapping("/view")
        public String viewQuestionBank(
                @RequestParam(value = "page", defaultValue = "0") int page,
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
                model.addAttribute("previousPage",questionBank.getNumber()-1);
                model.addAttribute("nextPage",questionBank.getNumber()+1);
                model.addAttribute("pageSize",AppConstants.CONTACT_PAZE_SIZE);
                model.addAttribute("question_bank_id");

                // Return the correct view for 'mysubject' page
                return "teacher/temp"; // This should render 'mysubject.html' where you want to display the question banks
        }



        //view particular questionbank
        @GetMapping("/view/{question_bank_id}")
        public String viewQuestionBank(@PathVariable("question_bank_id") String questionBankId,
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

                return "teacher/questionBank";
        }



        @GetMapping("/addQuestion/{question_bank_id}")
        public String showAddQuestionForm(@PathVariable("question_bank_id") String questionBankId, Model model) {
                // Make sure question_bank_id is added to the model
                model.addAttribute("question_bank_id", questionBankId);
                QuestionBank qb=questionBankService.getQuestionBankById(questionBankId);
                List<Topics> topicList=topicRepository.findByQuestionBank(qb);
                model.addAttribute("newTopicList",topicList);

                // Add a new empty questionForm object to the model
                model.addAttribute("question", new QuestionForm());

                model.addAttribute("topicForm",new TopicForm());

                return "teacher/questionForm";  // Thymeleaf view name
        }


        @RequestMapping(value = "/addQuestion/{question_bank_id}", method = RequestMethod.POST)
        public String addQuestionToDB(@PathVariable("question_bank_id") String questionBankId,
                                      @ModelAttribute QuestionForm questionForm,
                                      Model model) throws IOException {


                System.out.println(questionBankId);

                model.addAttribute("question_bank_id",questionBankId);



                //topic Selection
                QuestionBank questionBank=questionBankService.getQuestionBankById(questionBankId);
                List<Topics> listTopics=topicRepository.findByQuestionBank(questionBank);
                model.addAttribute("topicList",listTopics);







                Question question = new Question();
                question.setQuestionText(questionForm.getQuestionText());
//                question.setCorrectAnswer(questionForm.getCorrectAnswer());
                question.setTopic(questionForm.getTopic());
                question.setSolution(questionForm.getSolution());

                question.setQuestionId(UUID.randomUUID().toString());
                question.setQuestionBank(questionBank);
                question.setMarks(questionForm.getMarks());
                question.setMCQ(questionForm.isMCQ());




                if (questionForm.isMCQ()) {
                        // Save all four options
                        question.setOption1(questionForm.getOption1());
                        question.setOption2(questionForm.getOption2());
                        question.setOption3(questionForm.getOption3());
                        question.setOption4(questionForm.getOption4());

                        System.out.println(questionForm.getOption1());
                        System.out.println(questionForm.getOption2());
                        System.out.println(questionForm.getOption3());
                        System.out.println(questionForm.getOption4());

                        System.out.println(questionForm.getCorrectMCQAnswer());

                        question.setCorrectMCQAns(questionForm.getCorrectMCQAnswer().toString());

                }

                System.out.println();
//                System.out.println(questionForm.getFormOptions().toString());
                System.out.println();



                //Question Photo
                MultipartFile imageFile = questionForm.getQuestionImg();
                if (imageFile != null && !imageFile.isEmpty()) {
                        // Generate unique file name
                        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                        fileName.replace(" ","%20");

                        // Path to save the image in static/images folder
                        Path path = Paths.get("static/images", fileName);
                        Files.createDirectories(path.getParent());

                        // Save the file to disk
                        Files.copy(imageFile.getInputStream(), path);

                        // Set the relative URL to the image
                        question.setQuestionImage("/images/" + fileName); // Store only the relative URL in the database
                }

                System.out.println();
                System.out.println(question);
                System.out.println();

                questionServices.saveQuestion(question);




                return "redirect:/teacher/QB/view/" + questionBankId;

        }


        @GetMapping("/hello")
        public String getAddQ(){
                return "teacher/subjectMatarial";
        }


        @GetMapping("/addQuestion/{question_bank_id}/addTopic")
        public String addtopic(@PathVariable("question_bank_id") String questionBankId, Model model){
                // Make sure question_bank_id is added to the model
                model.addAttribute("question_bank_id", questionBankId);
                model.addAttribute("topicForm",new TopicForm());
                return "teacher/topicForm";
        }

        @GetMapping("/{question_bank_id}/upload-questions-data")
        public String uploadQuestionTemplate(@PathVariable("question_bank_id") String questionBankId, Model model){
                model.addAttribute("question-bank-id",questionBankId);
                return "teacher/excel-data";
        }

        @RequestMapping(method = RequestMethod.POST, value = "/{question_bank_id}/upload-questions-data")
        public String uploadQuestionData(@PathVariable("question_bank_id") String questionBankId, @RequestParam("file")MultipartFile file){
                QuestionBank questionBank=questionBankRepository.getQuestionBankById(questionBankId);
                System.out.println(questionBank.getId());
                //save questions to the above question bank
                this.questionServices.saveQuestionsToDatabase(file,questionBankId);

                return "redirect:/teacher/QB/view/" + questionBankId;

        }


        //delete
//        @PostMapping("/{questionId}/delete")
//        public String deleteQuestion(@PathVariable("question_id")String questionId){
//                return "";
//        }


        @RequestMapping(value = "view/{question_bank_id}/{question_id}/view",method = RequestMethod.GET)
        public String view(@PathVariable("question_bank_id")String questionBankId,
                           @PathVariable("question_id")String questionId,Model model){
                QuestionBank questionBank=questionBankRepository.getQuestionBankById(questionBankId);
                Optional<Question> question=questionServices.getQuestionById(questionId);

                if (!question.isEmpty()){
                        model.addAttribute("question",question.get());
                }

                model.addAttribute("questionBankId",questionBankId);
                model.addAttribute("questionId",questionId);
                return "teacher/viewQuestion";
        }


        @RequestMapping("/{question_bank_id}/{question_id}/delete")
        public String delete(@PathVariable("question_bank_id")String questionBankId,
                           @PathVariable("question_id")String questionId,Model model){
                QuestionBank questionBank=questionBankRepository.getQuestionBankById(questionBankId);
                Optional<Question> question=questionServices.getQuestionById(questionId);
                questionServices.deleteQuestion(question.get());

                model.addAttribute("questionBankId",questionBankId);
                model.addAttribute("questionId",questionId);
                return "redirect:/teacher/QB/view/"+questionBankId;
        }


        // Update Question
        @GetMapping("/view/{question_bank_id}/{question_id}/update")
        public String updateQuestion(@PathVariable("question_bank_id") String questionBankId,
                                     @PathVariable("question_id") String questionId,
                                     Model model) {
                Question question = questionServices.getQuestionById(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

                QuestionForm questionForm = new QuestionForm();
                questionForm.setQuestionText(question.getQuestionText());
                questionForm.setMarks(question.getMarks());
                questionForm.setMCQ(question.getMCQ());
                questionForm.setSolution(question.getSolution());
                questionForm.setTopic(question.getTopic());

                if (question.getMCQ()) {
                        questionForm.setOption1(question.getOption1());
                        questionForm.setOption2(question.getOption2());
                        questionForm.setOption3(question.getOption3());
                        questionForm.setOption4(question.getOption4());
                        questionForm.setCorrectMCQAnswer(Arrays.asList(question.getCorrectMCQAns().split(",")));
                }

                model.addAttribute("image", question.getQuestionImage());
                model.addAttribute("questionForm", questionForm);
                model.addAttribute("question_bank_id", questionBankId);
                model.addAttribute("question_id", questionId);
                model.addAttribute("topics", topicRepository.findAll());

                return "teacher/editQuestion";
        }


        // Update Question Data
        @RequestMapping(value = "/view/{question_bank_id}/{question_id}/update",method = RequestMethod.POST)
        public String updateQuestionData(@PathVariable("question_bank_id") String questionBankId,
                                         @PathVariable("question_id") String questionId,
                                         @ModelAttribute QuestionForm questionForm) {
                try {
                        Question question = questionServices.getQuestionById(questionId)
                                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

                        // Handle Image Upload
                        if (questionForm.getQuestionImg() != null && !questionForm.getQuestionImg().isEmpty()) {
                                MultipartFile imageFile = questionForm.getQuestionImg();
                                String originalFileName = Paths.get(imageFile.getOriginalFilename()).getFileName().toString();
                                String fileName = UUID.randomUUID() + "_" + originalFileName;

                                Path uploadPath = Paths.get("static/images");
                                Files.createDirectories(uploadPath);

                                Path filePath = uploadPath.resolve(fileName);
                                try (InputStream inputStream = imageFile.getInputStream()) {
                                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                                }

                                question.setQuestionImage("/images/" + fileName);
                        }

                        question.setQuestionText(questionForm.getQuestionText());
                        question.setQuestionBank(questionBankRepository.getQuestionBankById(questionBankId));
                        question.setSolution(questionForm.getSolution());
                        question.setTopic(questionForm.getTopic());
                        question.setMarks(questionForm.getMarks());

                        if (questionForm.isMCQ()) {
                                question.setMCQ(true);
                                question.setOption1(questionForm.getOption1());
                                question.setOption2(questionForm.getOption2());
                                question.setOption3(questionForm.getOption3());
                                question.setOption4(questionForm.getOption4());

                                if (questionForm.getCorrectMCQAnswer() != null) {
                                        question.setCorrectMCQAns(String.join(",", questionForm.getCorrectMCQAnswer()));
                                }
                        } else {
                                question.setMCQ(false);
                                question.setOption1(null);
                                question.setOption2(null);
                                question.setOption3(null);
                                question.setOption4(null);
                                question.setCorrectMCQAns(null);
                        }

                        questionServices.saveQuestion(question);
                } catch (IOException e) {
                        throw new ResourceNotFoundException("Error while saving image file");
                } catch (Exception e) {
                        throw new RuntimeException("Error updating question", e);
                }

                return "redirect:/teacher/QB/view/" + questionBankId;
        }



}
