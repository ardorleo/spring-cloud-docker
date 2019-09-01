package com.wei.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author weiwenfeng
 * @date 2019/9/1
 * @desc
 */
@FeignClient(name = "eureka-service-b")
public interface ServiceBFeign {

    @GetMapping("/version-b/get")
    String version();
}
