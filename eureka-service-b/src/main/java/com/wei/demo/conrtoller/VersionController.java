package com.wei.demo.conrtoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiwenfeng
 * @date 2019/9/1
 * @desc
 */
@RestController
@RequestMapping("/version-b")
public class VersionController {

    @GetMapping("/get")
    public String version(){
        return "欢迎访问[service-b]服务，版本是v1.0";
    }
}
