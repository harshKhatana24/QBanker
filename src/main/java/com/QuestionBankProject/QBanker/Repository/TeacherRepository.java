package com.QuestionBankProject.QBanker.Repository;

import com.QuestionBankProject.QBanker.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    //extra methods db related
    //custom finder methods
    boolean findTeachersByEmail(String email);
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByEmailAndPassword(String email, String password);

    Teacher getTeacherByEmail(String email);



}
