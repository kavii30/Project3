package com.telusko.studentmanagement2.service.impl;

import com.telusko.studentmanagement2.model.Users;
import com.telusko.studentmanagement2.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserDetailsService {

    private UsersRepository usersRepository;

    public UserServiceimpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=usersRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("invalid username"));
        return User.withUsername(username)
                .password(users.getPassword())
                .disabled(!users.getActive())
                .build();
    }
}
