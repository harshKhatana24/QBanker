package com.QuestionBankProject.QBanker.Forms;

import com.QuestionBankProject.QBanker.Annotations.UniqueTopicName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

public class TopicForm {

    @UniqueTopicName
    private String topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
