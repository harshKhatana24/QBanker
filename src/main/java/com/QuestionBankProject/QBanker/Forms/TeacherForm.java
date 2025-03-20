package com.QuestionBankProject.QBanker.Forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class TeacherForm {

    @NotBlank(message = "username required")
    @Size(min = 3, message = "min 3 characters required")
    private String name;

    @Email(message = "email required")
    private String email;

    @NotBlank(message = "password required")
    @Size(min = 6, message = "min 6 characters required")
    private String pasword;


    //annotation create karenge jo file ko validate karenga
    //size
    //resolution
    //type
    private MultipartFile contactImg;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public MultipartFile getContactImg() {
        return contactImg;
    }

    public void setContactImg(MultipartFile contactImg) {
        this.contactImg = contactImg;
    }

    public TeacherForm(String name, String email, String pasword, MultipartFile contactImg) {
        this.name = name;
        this.email = email;
        this.pasword = pasword;
        this.contactImg = contactImg;
    }

    public TeacherForm() {
    }
}
