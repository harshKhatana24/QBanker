package com.QuestionBankProject.QBanker.Services;


import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Entities.Teacher;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionBankService {

    //save questionBank
    QuestionBank save(QuestionBank questionBank);

    //update questionBank
    QuestionBank update(QuestionBank questionBank);


    //get questionBank
    List<QuestionBank> getAll();


    //delete questionBank
    void delete(String id);


    //search questionBank
    List<QuestionBank> search(String name, String email, String phoneNumber);


    //get questionBank by teacherID
    List<QuestionBank> getByTeacherId(String id);


    //get questionBank by teacher
    Page<QuestionBank> getByTeacher(Teacher teacher, int page, int size, String sortField, String sortDirection);


    QuestionBank getQuestionBankById(String questionBankId);

}
