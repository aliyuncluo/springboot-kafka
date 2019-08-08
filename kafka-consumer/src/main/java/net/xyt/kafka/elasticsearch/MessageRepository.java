package net.xyt.kafka.elasticsearch;

import net.xyt.kafka.domain.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author luolei
 * @Date 2019/8/8 10:31
 */
@Component
public interface MessageRepository extends ElasticsearchRepository<Message,Long> {


}
