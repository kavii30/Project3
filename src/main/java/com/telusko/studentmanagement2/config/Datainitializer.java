package com.telusko.studentmanagement2.config;

import com.telusko.studentmanagement2.model.Users;
import com.telusko.studentmanagement2.repository.UsersRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Datainitializer {
    //for default user
    @Bean
    CommandLineRunner loadSampleData(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {

        return args->{
            if(!usersRepository.existsByUsername("Admin")) {
                Users users = new Users();
                users.setUsername("admin");
                users.setPassword(passwordEncoder.encode("admin@123"));// ye pass toh koi bhi deakh lega isliye hum use krenge Spring Security ka encrypt
                //karne ke liye  SpringConfig Class bnaai hai encoding ke liye ab ye pass encode krke save hoga
                users.setActive(true);
                usersRepository.save(users);
                // jab jab project run hoga ye user create krega but humko sirf ek hi baar run karana hai isliye exists method ka use
            }

        };
    }
}
