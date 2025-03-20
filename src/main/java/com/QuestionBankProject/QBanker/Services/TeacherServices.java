package com.QuestionBankProject.QBanker.Services;


import com.QuestionBankProject.QBanker.Entities.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TeacherServices {

    Teacher saveTeacher(Teacher teacher);

    Optional<Teacher> getTeacherByID(String id);

    Optional<Teacher> updateTeacher(Teacher teacher);

    void deleteTeacher(String id);

    boolean isTeacherExists(String id);

    boolean isTeacherExistsByEmail(String email);

    List<Teacher> getAllTeachers();

    Teacher getTeacherByEmail(String email);

    //add more methods here related to user service[logic]


}
