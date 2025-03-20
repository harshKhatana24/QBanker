package com.QuestionBankProject.QBanker.Repository;

import com.QuestionBankProject.QBanker.Entities.Question;
import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, String > {

    //find questionBank by user
    //custom finder method
    Page<QuestionBank> findByTeacher(Teacher teacher, Pageable pageable);

    //custom query method
    @Query("select c from QuestionBank c where c.teacher.Id = :teacherId")
    List<QuestionBank> findByTeacherId(@Param("teacherId") String teacherId);

    QuestionBank getQuestionBankById(String questionBankId);;

}
