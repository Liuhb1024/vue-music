package top.zetiny.vuemusicproject.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zetiny.vuemusicproject.bean.Consumer;
import top.zetiny.vuemusicproject.service.ConsumerService;
import top.zetiny.vuemusicproject.utils.CustomMsgUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private CustomMsgUtils customMsgUtils;

    /**
     * @param phoneNumOrUsername
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/checkIfPresent")
    public Object checkIfPresent(@RequestParam String phoneNumOrUsername) throws JsonProcessingException {
        if (find(phoneNumOrUsername)) {
            return customMsgUtils.getCustomMessage(1, "手机号已被注册");
        }
        return customMsgUtils.getCustomMessage(0, "手机号可用");
    }

    /**
     * 判断手机号是否已经存在
     *
     * @param phoneNumOrUsername
     * @return
     */
    public boolean find(String phoneNumOrUsername) {
        phoneNumOrUsername = phoneNumOrUsername.trim();
        List<Consumer> consumerList = consumerService.queryAllConsumers();
        for (Consumer c : consumerList) {
            if (c.getPhoneNum().equals(phoneNumOrUsername) || c.getUsername().equals(phoneNumOrUsername)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 手机号未被占用，则注册新用户
     *
     * @param consumer
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/register")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Object register(@RequestBody Consumer consumer) throws JsonProcessingException {
        try {
            System.out.println(consumer);
            int flag = consumerService.addConsumer(consumer);
            if (flag > 0) {
                return customMsgUtils.getCustomMessage(1, "注册成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customMsgUtils.getCustomMessage(0, "注册失败,请联系管理员。");
    }

    @GetMapping("/query")
    public Object queryConsumerById(@RequestParam String consumerId) throws JsonProcessingException {
        Consumer consumer = null;
        try {
            consumer = consumerService.queryConsumerById(Integer.valueOf(consumerId)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customMsgUtils.getJSONString(consumer);
    }

    @GetMapping("/query-all")
    public Object queryAllConsumers() throws JsonProcessingException {
        List<Consumer> consumerList = null;
        try {
            consumerList = consumerService.queryAllConsumers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customMsgUtils.getJSONString(consumerList);
    }

    @PostMapping("/add")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Object addConsumer(@RequestBody Consumer consumer) throws JsonProcessingException {
        int flag = 0;
        try {
            flag = consumerService.addConsumer(consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            return customMsgUtils.getCustomMessage(1, "保存用户信息成功");
        }
        return customMsgUtils.getCustomMessage(0, "保存用户信息失败");
    }

    @PutMapping("/modify")
    public Object modifyConsumer(@RequestBody Consumer consumer) throws JsonProcessingException {
        int flag = 0;
        String email = "";
        String phoneNum = "";
        try {
            email = consumerService.queryConsumerById(consumer.getId()).get(0).getEmail();
            phoneNum = consumerService.queryConsumerById(consumer.getId()).get(0).getPhoneNum();
        } catch (Exception e) {
            e.printStackTrace();
            return customMsgUtils.getCustomMessage(0, "更新用户信息失败:无法检索已存在用户信息");
        }
        if (consumer.getUsername() == null || "".equals(consumer.getUsername())) {
            return customMsgUtils.getCustomMessage(0, "更新用户信息失败，用户名不能为空");
        }
        if (consumer.getEmail() == null || "".equals(consumer.getEmail()) || email.equals(consumer.getEmail())) {
            return customMsgUtils.getCustomMessage(0, "更新用户信息失败，邮箱为空或不唯一");
        }
        if (consumer.getPhoneNum() == null || "".equals(consumer.getPhoneNum()) || phoneNum.equals(consumer.getPhoneNum())) {
            return customMsgUtils.getCustomMessage(0, "更新用户信息失败，手机号为空或不唯一");
        }
        try {
            flag = consumerService.modifyConsumer(consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            return customMsgUtils.getCustomMessage(1, "更新用户信息成功");
        }
        return customMsgUtils.getCustomMessage(0, "更新用户信息失败");
    }

    @DeleteMapping("/remove/{consumerId}")
    public Object removeConsumer(@PathVariable("consumerId") String consumerId) throws JsonProcessingException {
        int flag = 0;
        try {
            flag = consumerService.removeConsumer(Integer.valueOf(consumerId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            return customMsgUtils.getCustomMessage(1, "移除用户成功");
        }
        return customMsgUtils.getCustomMessage(0, "移除用户失败");
    }

    @PostMapping("/verify")
    public Object verifyPassword(@RequestBody Consumer consumer, HttpSession session) throws JsonProcessingException {
        Consumer res = null;
        try {
            res = consumerService.verifyPassword(consumer.getUsername(), consumer.getPhoneNum(), consumer.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res != null) {
            session.setAttribute("username", res.getUsername());
        }
        return customMsgUtils.getJSONString(res);
    }

    @PostMapping("/update-consumer-img")
    public Object updateConsumerImg(@RequestParam("id") String id, @RequestParam("consumer-img-file") MultipartFile
            imgFile) throws JsonProcessingException {
        if (imgFile.isEmpty()) {
            return customMsgUtils.getCustomMessage(0, "更新用户头像失败");
        }
        String consumerImgFileName = imgFile.getOriginalFilename();
        String filePathTargetDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "consumer-img";
        File file = new File(filePathTargetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File destFile = new File(filePathTargetDir + System.getProperty("file.separator") + consumerImgFileName);
        String avatarPath = "/img/consumer-img/" + consumerImgFileName;
        int flag = 0;
        try {
            imgFile.transferTo(destFile);
            Consumer consumer = consumerService.queryConsumerById(Integer.parseInt(id)).get(0);
            consumer.setAvatar(avatarPath);
            flag = consumerService.modifyConsumer(consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            return customMsgUtils.getCustomMessage(1, "更新用户图片成功", "avatar#" + avatarPath);
        }
        return customMsgUtils.getCustomMessage(0, "更新用户图片失败");
    }
}
