package com.QuestionBankProject.QBanker.Controller;

import com.QuestionBankProject.QBanker.Entities.Teacher;
import com.QuestionBankProject.QBanker.Helper.Helper;
import com.QuestionBankProject.QBanker.Helper.ResourceNotFoundException;
import com.QuestionBankProject.QBanker.Services.TeacherServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice //iske method har ek request ke liye excute hoge
public class RootController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TeacherServices teacherServices;

    //hum jab bhi koi user ka profile, dashboard call kare tu yea call ho jaye
    //agar model attribute likh diya tu yea controller ke has method se phele chalega
    @ModelAttribute
    public Teacher addLoggedInUserInformation(Model model, Authentication authentication){
        //agar login  nhi hai tu phir execute nhi karega means koi info add he nhi karega
        if (authentication == null){
            System.out.println();
            System.out.println("Teacher not found using RootController");
            System.out.println();
            return null;
        }


        System.out.println("adding logged in user information");

        String username= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in : {}",username);
        //database se data ko fetch kar sakte hai
//        User user=userServices.getUserByEmail(username);
        Teacher teacher = teacherServices.getTeacherByEmail(username);
        System.out.println(teacher);
        model.addAttribute("loggedInUser",teacher);
        System.out.println(teacher.getName());
        System.out.println(teacher.getEmail());

        return teacher;
    }

}
