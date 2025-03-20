package com.QuestionBankProject.QBanker.Services.Impl;

import com.QuestionBankProject.QBanker.Entities.Question;
import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Repository.QuestionBankRepository;
import com.QuestionBankProject.QBanker.Repository.QuestionRepository;
import com.QuestionBankProject.QBanker.Services.QuestionBankService;
import com.QuestionBankProject.QBanker.Services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionServices {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private QuestionBankService questionBankService;

    @Override
    public Question saveQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Override
    public Optional<Question> getQuestionById(String id) {
        return questionRepo.findById(id);
    }

    @Override
    public Page<Question> getByQuestionBank(QuestionBank questionBank, int page, int size, String sortBy, String sortDirection) {
        // Ensure valid sorting direction
        if (!Arrays.asList("asc", "desc").contains(sortDirection)) {
            sortDirection = "asc"; // Default to ascending order if invalid
        }

        // Ensure valid sort field
        if (!Arrays.asList("questionText", "topic", "correctAnswer").contains(sortBy)) {
            sortBy = "questionText"; // Default to sorting by questionText
        }

        // Create Sort object based on the direction and field
        Sort sort = "desc".equalsIgnoreCase(sortDirection) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        // Create Pageable object with the given page, size, and sort
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch the questions associated with the given questionBank using pagination
        return questionRepo.findByQuestionBank(questionBank, pageable);
    }

    @Override
    public List<Question> searchQuestionsByTopic(String topic) {
        return questionRepo.getQuestionsByTopic(topic);
    }

    @Override
    public void deleteQuestion(Question question) {
        System.out.println("Question is deleted");
        questionRepo.delete(question);
    }

    @Override
    public Question updateQuestion() {
        throw new UnsupportedOperationException("Update operation is not implemented yet");
    }

    // Excel to database save data
    public void saveQuestionsToDatabase(MultipartFile file, String questionBankId) {
        QuestionBank questionBank = questionBankService.getQuestionBankById(questionBankId);
        if (ExcelUploadService.isValidExcelFile(file)) {
            try {
                List<Question> questionList = ExcelUploadService.getQuestionsDataFromExcel(file.getInputStream(), questionBank);
                this.questionRepo.saveAll(questionList);
            } catch (Exception e) {
                throw new IllegalArgumentException("The file is not a valid Excel file");
            }
        }
    }
}
