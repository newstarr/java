package com.newstar.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Quick2Controller {

    @Value("${name}")
    private String name;

    @Value("${person.address}")
    private String address;

    @ResponseBody
    @RequestMapping("/quick2")
    public String quick2() {
        return "name:" + name + ",address:" + address;
    }
}
