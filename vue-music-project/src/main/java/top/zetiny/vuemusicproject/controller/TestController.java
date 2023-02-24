package top.zetiny.vuemusicproject.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "测试路由映射： 成功返回json字符串";
    }
}
