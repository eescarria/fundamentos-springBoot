package com.fundamentosPlatzi.springboot.fundamentos.caseuse;

import com.fundamentosPlatzi.springboot.fundamentos.entity.User;
import com.fundamentosPlatzi.springboot.fundamentos.service.UserService;

import java.util.List;

public class GetUserImplement implements GetUser {

    private UserService userService;

    public GetUserImplement(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> getAll() {
        return userService.getAllUsers();
    }
}
