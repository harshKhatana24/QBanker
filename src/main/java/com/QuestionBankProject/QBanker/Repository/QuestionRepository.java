package com.QuestionBankProject.QBanker.Repository;

import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.QuestionBankProject.QBanker.Entities.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,String > {

    List<Question> getQuestionsByTopic(String topic);


    Page<Question> findByQuestionBank(QuestionBank questionBank, Pageable pageable);

    //custom query method
    @Query("select c from Question c where c.questionBank.id = :questionBankId")
    List<Question> findByQuestionBankId(@Param("questionBankId") String questionBankId);

//     //custom query method
//    @Query("select c from QuestionBank c where c.teacher.Id = :teacherId")
//    List<QuestionBank> findByTeacherId(@Param("teacherId") String teacherId);


    List<Question> findAllByQuestionBank(QuestionBank questionBank);

}
