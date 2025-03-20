package com.QuestionBankProject.QBanker.Annotations;


import com.QuestionBankProject.QBanker.Repository.TopicRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueTopicNameValidator implements ConstraintValidator<UniqueTopicName, String> {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public boolean isValid(String topicName, ConstraintValidatorContext context) {
        // Check if the topic name already exists in the database
        return topicRepository.findByTopicName(topicName)==null;
    }
}
