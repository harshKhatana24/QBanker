package com.QuestionBankProject.QBanker.Services.Impl;


import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Entities.Teacher;
import com.QuestionBankProject.QBanker.Repository.QuestionBankRepository;
import com.QuestionBankProject.QBanker.Services.QuestionBankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionBankServiceImpl implements QuestionBankService {

    private QuestionBankRepository questionBankRepository;

    QuestionBankServiceImpl(QuestionBankRepository questionBankRepository){
        this.questionBankRepository = questionBankRepository;
    }


    @Override
    public QuestionBank save(QuestionBank questionBank) {

        String questionBankId = UUID.randomUUID().toString();
        questionBank.setId(questionBankId);
        return questionBankRepository.save(questionBank);

    }

    @Override
    public QuestionBank update(QuestionBank questionBank) {
        return null;
    }

    @Override
    public List<QuestionBank> getAll() {
        return questionBankRepository.findAll();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<QuestionBank> search(String name, String email, String phoneNumber) {
        return List.of();
    }

    @Override
    public List<QuestionBank> getByTeacherId(String id) {
        return questionBankRepository.findByTeacherId(id);
    }

    @Override
    public Page<QuestionBank> getByTeacher(Teacher teacher, int page, int size, String sortBy, String sortDirection) {

        //ensure a valid sort direction
        Sort sort = "desc".equalsIgnoreCase(sortDirection) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        //create a pageable instance
        var pageable = PageRequest.of(page, size, sort);

        //fetch the questionBanks by teacher with pagination
        return questionBankRepository.findByTeacher(teacher, pageable);

    }

    @Override
    public QuestionBank getQuestionBankById(String questionBankId) {
        return questionBankRepository.getQuestionBankById(questionBankId);
    }
}
