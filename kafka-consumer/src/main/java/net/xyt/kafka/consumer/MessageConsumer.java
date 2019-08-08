package net.xyt.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.xyt.kafka.domain.Message;
import net.xyt.kafka.elasticsearch.MessageRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import xyz.downgoon.snowflake.Snowflake;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @Author luolei
 * @Date 2019/8/8 10:12
 */
@Controller
public class MessageConsumer {
    @Autowired
    private MessageRepository messageRepository;

    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @KafkaListener(topics = {"first"})
    public void receiveMessage(ConsumerRecord<Object,Object> record){
        Map<String,Object> result = new HashMap<>();
        //判断是否为空
        Optional<Object> kafkaMessage = Optional.ofNullable(record.value());
        logger.info("------>record="+kafkaMessage);
        if(kafkaMessage.isPresent()){
            //获取optional实例中的值
            Object message = kafkaMessage.get();
            logger.info("消费者接收到的消息="+message);
            saveMessage(String.valueOf(message));
        }
    }

    /**
     *保存到es中
     */
    public void saveMessage(String message){
        Map<String,Object> map = new HashMap<>();
        Snowflake snowflake = new Snowflake(0,0);
        long nextId = snowflake.nextId();
        logger.info("生成的ID="+nextId);
        Map<String, Object> transMap = jsonTransMap(message);
        logger.info("message map="+transMap);
        Message msg = new Message();
        msg.setId(nextId);
        msg.setTitle(String.valueOf(transMap.get("title")));
        msg.setContent(String.valueOf(transMap.get("content")));
        Message resultMsg = messageRepository.save(msg);
        logger.info("===result==={}",resultMsg);

    }

    /**
     * JSON转为map
     * @param text
     * @return
     */
    public static  Map<String,Object> jsonTransMap(String text){
        Map<String,Object> result = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(text);
        Set<String> keySet = jsonObject.keySet();
        for(String key:keySet){
            Object value = jsonObject.get(key);
            result.put(key,value);
        }
        return result;
    }


}
