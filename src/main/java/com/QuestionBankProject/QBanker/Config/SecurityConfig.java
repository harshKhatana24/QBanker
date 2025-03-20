package com.QuestionBankProject.QBanker.Config;



import com.QuestionBankProject.QBanker.Services.Impl.SecurityCustomeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //user create and login using java code with in memory service
    //user detail service ko use karta hai kam krne ke liya
    //is service ko use karta hai aur uske pass ek method hota hai loadUserByUsername
    //loadUserByUsername ko call krega user ko load krane ke liye
    //aur jo loaded user hai & jo hmara user hai un do ke password match krega

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("admin123")
//                .password("admin123")
//                .roles("ADMIN","USER")
//                .build();
//
//
//
//        var inMemoryUserDetailsManager =new InMemoryUserDetailsManager(user1); //user detail service ki implementation hai
//        return inMemoryUserDetailsManager;
//
//
//    }


    @Autowired
    private SecurityCustomeUserDetailService customeUserDetailService;


    //Configuration of authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //user detail service ka object
        //users ko utha ke layegi database vale
        //User ki details ko rep. karne ke liye springboot mea userdetails nam se ek interface hai
        //hum User (entity) usko implement kra denge iss interface ko
        daoAuthenticationProvider.setUserDetailsService(customeUserDetailService);
        //password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //configuration of security filter chain
    //Post method not supported aaye tu ye done
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //urls configuration
        //order specific hai
        httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/teacher/**").authenticated();
                    authorize.anyRequest().permitAll();
                })

                .formLogin(formLogin -> {
                    formLogin.loginPage("/login") // Custom login page endpoint
                            .loginProcessingUrl("/authenticate") // Endpoint for login form submission
                            .defaultSuccessUrl("/teacher/QB/add", true) // Redirect to dashboard on success
                            .failureUrl("/login?error=true") // Redirect to login page with error on failure
                            .usernameParameter("email") // Customize parameter names
                            .passwordParameter("password");
                });


        httpSecurity.csrf(AbstractHttpConfigurer::disable); //csrf disable hai puri website mea
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });



        return httpSecurity.build();

    }


}
