package com.QuestionBankProject.QBanker.Forms;

import jakarta.validation.constraints.NotBlank;

public class QuestionBankForm {

    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "teacher name is required")
    private String teacherName;

    public @NotBlank(message = "name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "teacher name is required") String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(@NotBlank(message = "teacher name is required") String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "QuestionBankForm{" +
                "name='" + name + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }

    public QuestionBankForm(String name, String teacherName) {
        this.name = name;
        this.teacherName = teacherName;
    }

    public QuestionBankForm() {
    }

}
