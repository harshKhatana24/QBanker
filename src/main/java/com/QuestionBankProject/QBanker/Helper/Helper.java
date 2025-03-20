package com.QuestionBankProject.QBanker.Helper;

import org.springframework.security.core.Authentication;

public class Helper {



    public static String getEmailOfLoggedInUser(Authentication authentication) {

        System.out.println("Getting data from local database");
        return authentication.getName();

    }




}
