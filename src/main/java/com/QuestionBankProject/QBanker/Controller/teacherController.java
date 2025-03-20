package com.QuestionBankProject.QBanker.Controller;


import com.QuestionBankProject.QBanker.Entities.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class teacherController {

    //user dashboard page
    @GetMapping("/dashboard")
    public String dashboard(){
        System.out.println("user dashboard");
        return "teacher/dashboardpage";
    }


    //user profile page
    @GetMapping("/profile")
    public String profile(){
        System.out.println("user profile");
        return "teacher/profile";
    }


    //my subject
    @GetMapping("/mysub")
    public String mySubject(){
        System.out.println("user dashboard");
        return "teacher/mysubject";
    }


    @GetMapping("/about")
    public String aboutUs(){
        System.out.println("user dashboard");
        return "teacher/aboutUs";
    }


    //user add question


    //user view question



    //user delete question


}
