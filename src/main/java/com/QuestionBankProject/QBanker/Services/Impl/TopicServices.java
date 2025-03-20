package com.QuestionBankProject.QBanker.Services.Impl;

import com.QuestionBankProject.QBanker.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServices {

    @Autowired
    private TopicRepository topicRepository;

    // Method to check if the topic name is unique
    public boolean isTopicUnique(String topicName) {
        return topicRepository.findByTopicName(topicName) == null;
    }

    // Other business logic related to topics (e.g., adding topics, etc.)
}
