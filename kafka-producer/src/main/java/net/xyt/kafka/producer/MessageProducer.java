package net.xyt.kafka.producer;

import com.alibaba.fastjson.JSON;
import net.xyt.kafka.domain.Message;
import net.xyt.kafka.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author luolei
 * @Date 2019/8/8 9:43
 */
@RestController
public class MessageProducer {
    private Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    @Autowired
    KafkaTemplate kafkaTemplate;

    /**
     * 发送消息
     * @return
     */
    @PostMapping("/send/message")
    public Map<String,Object> sendMessage(Message message){
        Map<String,Object> result = new HashMap<>();
        try {
            logger.info("发送的消息内容为="+message);
            kafkaTemplate.send("first", JSON.toJSONString(message));
            result.put("exec","S");
            result.put("code","10000");
            result.put("msg","数据发送成功");
        } catch (Exception e) {
            logger.info("数据发送失败");
            result.put("exec","F");
            result.put("code","10001");
            result.put("msg","数据发送失败");
        }
        return result;
    }
    /**
     * 发送数据
     * @return
     */
    @GetMapping("/test/send")
    public Map<String,Object> sendData(){
        Map<String,Object> result = new HashMap<>();
        try {
            User user = new User();
            user.setId(1L);
            user.setName("张三");
            user.setAge(20);
            user.setPhone("13212345678");
            user.setAddress("湖北武汉");
            kafkaTemplate.send("first", JSON.toJSONString(user));
            result.put("exec","S");
            result.put("code","10000");
            result.put("msg","数据发送成功");
        } catch (Exception e) {
            logger.info("数据发送失败");
            result.put("exec","F");
            result.put("code","10001");
            result.put("msg","数据发送失败");
        }
        return result;

    }
}
