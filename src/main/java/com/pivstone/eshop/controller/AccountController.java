package com.pivstone.eshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RestController
@RequestMapping("/account")
public class AccountController {


    @GetMapping("/current")
    private Principal current(Principal principal) {

        return principal;
    }


}
