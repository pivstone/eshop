package com.pivstone.eshop.controller;

import com.pivstone.eshop.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepo accountRepo;


    //@GetMapping("/login")
    private ModelAndView login(@RequestParam Optional<String> error,
                               @RequestParam Optional<String> logout,
                               ModelMap modeld) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "用户名或密码不正确!");
        }

        if (logout != null) {
            model.addObject("msg", "您已成功注销系统.");
        }
        model.setViewName("login");
        return model;
    }


}
