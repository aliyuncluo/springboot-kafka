package net.xyt.kafka;

import net.xyt.kafka.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaConsumerApplicationTests {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void contextLoads() {
        elasticsearchTemplate.createIndex(Message.class);
    }

}
