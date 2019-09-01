package com.wei.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author weiwenfeng
 * @date 2019/9/1
 * @desc
 */
@FeignClient(name = "eureka-service-c")
public interface ServiceCFeign {

    @GetMapping("/version-c/get")
    String version();
}
