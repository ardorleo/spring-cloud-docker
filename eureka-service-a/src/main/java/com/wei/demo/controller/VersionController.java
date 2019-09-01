package com.wei.demo.controller;

import com.wei.demo.feign.ServiceBFeign;
import com.wei.demo.feign.ServiceCFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author weiwenfeng
 * @date 2019/9/1
 * @desc
 */
@RestController
@RequestMapping("/version-a")
public class VersionController {

    @Resource
    private ServiceBFeign serviceBFeign;

    @Resource
    private ServiceCFeign serviceCFeign;

    @GetMapping("/get")
    public String version() {
        return "欢迎访问[service-a]服务，版本是v1.0";
    }

    @GetMapping("/getService")
    public String getService() {

        StringBuffer stringBuffer = new StringBuffer();

        String serviceBVsersion = serviceBFeign.version();
        stringBuffer.append("调用service-b返回的是：[" + serviceBVsersion).append("]");
        stringBuffer.append("；");
        String serviceCVersion = serviceCFeign.version();
        stringBuffer.append("调用service-c返回的是：[" + serviceCVersion).append("]");

        return stringBuffer.toString();
    }
}
