package com.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ems.entity.Login;
import com.ems.repository.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public Login findByUsername(String username) {
        return loginRepository.findByUsername(username);
    }
}
