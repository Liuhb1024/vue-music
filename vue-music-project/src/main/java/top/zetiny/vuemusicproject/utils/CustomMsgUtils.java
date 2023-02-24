package top.zetiny.vuemusicproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CustomMsgUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public String getJSONString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public final String getCustomMessage(Integer code, String msg, String... args) throws JsonProcessingException {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Consts.LOGIN_STATUS_CODE_NAME, code);
        hashMap.put(Consts.LOGIN_STATUS_MSG_NAME, msg);
        for (String str: args) {
            String[] split = str.split("#");
            String key = split[0];
            Object val = split[1];
            hashMap.put(key, val);
        }
        return objectMapper.writeValueAsString(hashMap);
    }
}
