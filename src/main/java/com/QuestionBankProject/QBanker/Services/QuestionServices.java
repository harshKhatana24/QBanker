package com.QuestionBankProject.QBanker.Services;


import com.QuestionBankProject.QBanker.Entities.Question;
import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Repository.QuestionBankRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionServices {

    public Question saveQuestion(Question question);

    public Optional<Question> getQuestionById(String id);

    Page<Question> getByQuestionBank(QuestionBank questionBank, int page, int size, String sortBy, String sortDirection);

    public List<Question> searchQuestionsByTopic(String topic);

    public void deleteQuestion(Question question);
    public Question updateQuestion();
    public void saveQuestionsToDatabase(MultipartFile file, String questionBankId);

}
