package top.zetiny.vuemusicproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zetiny.vuemusicproject.service.AdminService;
import top.zetiny.vuemusicproject.utils.Consts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/login/status")
    public Object loginStatus(HttpServletRequest request, HttpSession session) throws JsonProcessingException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isValid = adminService.verifyPassword(username, password);
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isValid) {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 1);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "登录成功");
            session.setAttribute("username", username);
            System.out.println("登录成功");
        } else {
            hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, 0);
            hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, "登录失败,用户名或密码错误");
            System.out.println("登录失败");
        }
        return objectMapper.writeValueAsString(hashMap);
    }

}
