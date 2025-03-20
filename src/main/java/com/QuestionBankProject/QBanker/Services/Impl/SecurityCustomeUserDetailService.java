package com.QuestionBankProject.QBanker.Services.Impl;

import com.QuestionBankProject.QBanker.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomeUserDetailService implements UserDetailsService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //apne user ko load krana hai
        return teacherRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found exception" + username));

    }
}
