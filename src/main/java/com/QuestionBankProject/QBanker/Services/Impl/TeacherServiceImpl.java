package com.QuestionBankProject.QBanker.Services.Impl;


import com.QuestionBankProject.QBanker.Entities.Teacher;
import com.QuestionBankProject.QBanker.Helper.AppConstants;
import com.QuestionBankProject.QBanker.Helper.ResourceNotFoundException;
import com.QuestionBankProject.QBanker.Repository.TeacherRepository;
import com.QuestionBankProject.QBanker.Services.TeacherServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherServiceImpl implements TeacherServices {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired //after doing login setting security configuration
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Teacher saveTeacher(Teacher teacher) {

        //Teacher id have to generate
        String teacherId = UUID.randomUUID().toString();
        teacher.setId(teacherId);

        //password encode
        //after setting security config
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));


        //set the user role

        teacher.setRoleList(List.of(AppConstants.ROLE_USER));//hardcode karne ke jgah app constants ka use kiya


//        logger.info(teacher.getProvider().toString());

        return teacherRepository.save(teacher);
    }

    @Override
    public Optional<Teacher> getTeacherByID(String id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Optional<Teacher> updateTeacher(Teacher teacher) {

        Teacher teacher1 = teacherRepository.findById(teacher.getId())
                .orElseThrow(()-> new ResourceNotFoundException("teacher not found"));

        teacher1.setName(teacher.getName());
        teacher1.setEmail(teacher.getEmail());
        teacher1.setPassword(teacher.getPassword());
        teacher1.setEnabled(teacher.isEnabled());
        teacher1.setEmailVerified(teacher.isEmailVerified());

        Teacher savedTeacher = teacherRepository.save(teacher1);
        return Optional.ofNullable(savedTeacher);
    }

    @Override
    public void deleteTeacher(String id) {
        Teacher teacher1 = teacherRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("teacher not found"));

        teacherRepository.delete(teacher1);
    }

    @Override
    public boolean isTeacherExists(String id) {
        Teacher teacher1 = teacherRepository.findById(id)
                .orElse(null);

        return teacher1!=null ? true : false;
    }

    @Override
    public boolean isTeacherExistsByEmail(String email) {
        Teacher teacher = teacherRepository.findByEmail(email).orElse(null);

        return teacher!=null ? true : false;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherByEmail(String email) {
        return teacherRepository.getTeacherByEmail(email);
    }


}
