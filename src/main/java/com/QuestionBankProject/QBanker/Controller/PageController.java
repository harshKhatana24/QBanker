package com.QuestionBankProject.QBanker.Controller;


import com.QuestionBankProject.QBanker.Entities.Teacher;
import com.QuestionBankProject.QBanker.Forms.TeacherForm;
import com.QuestionBankProject.QBanker.Helper.Helper;
import com.QuestionBankProject.QBanker.Helper.Message;
import com.QuestionBankProject.QBanker.Helper.MessageType;
//import com.QuestionBankProject.QBanker.Services.ImageService;
import com.QuestionBankProject.QBanker.Services.TeacherServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class PageController {

    @Autowired
    private TeacherServices teacherServices;

//    @Autowired
//    private ImageService imageService;


    @GetMapping("/login")
    public String getIndexPage(Model model){

        TeacherForm teacher = new TeacherForm();
        //teacher.setName("Ravi Bhandari"); //default value bhaj dega form
        model.addAttribute("teacherForm",teacher);

        return "login";
    }


//    @GetMapping("/login")
//    public String getLoginPage(){
//        return "login";
//    }




    //processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute TeacherForm teacherForm,
                                  BindingResult rbindingresult,
                                  Authentication authentication,
                                  HttpSession session) throws IOException {

        System.out.println("processing registration");

        //fetch the form data

        System.out.println(teacherForm);

        //validate the form data
        if (rbindingresult.hasErrors()){

            rbindingresult.getAllErrors().forEach(errors -> System.out.println(errors));

            Message message = new Message();
            message.setType(MessageType.red);
            message.setContent("Please correct the following errors");

            session.setAttribute("message",message);

            return "login";
        }


        //process the data
//        String username = Helper.getEmailOfLoggedInUser(authentication);





        //save to database
        Teacher teacher = new Teacher();
        teacher.setName(teacherForm.getName());
        teacher.setEmail(teacherForm.getEmail());
        teacher.setPassword(teacherForm.getPasword());
        teacher.setEnabled(true);
        //Handle image upload
        MultipartFile imageFile=teacherForm.getContactImg();
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
            teacher.setPicture("/images/" + fileName); // Store only the relative URL in the database
        }
//        teacher.setPicture(fileURL);

        Teacher savedTeacher = teacherServices.saveTeacher(teacher);

        System.out.println("Teacher saved : "+savedTeacher);

        //message = "Registration Successful"

        Message message = new Message();
        message.setContent("Registration Successful !!!");
        message.setType(MessageType.green);

        session.setAttribute("message",message);


        //redirect to login page

        return "redirect:/login";
    }





}
