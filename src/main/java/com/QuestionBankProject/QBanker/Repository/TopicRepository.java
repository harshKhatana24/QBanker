package com.QuestionBankProject.QBanker.Repository;

import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Entities.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topics,String> {

    Topics findByTopicName(String topicName);

    List<Topics> findByQuestionBank(QuestionBank questionBank);
}
