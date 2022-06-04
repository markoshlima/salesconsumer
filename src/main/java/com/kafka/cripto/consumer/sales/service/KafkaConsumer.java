package com.kafka.cripto.consumer.sales.service;

import com.kafka.cripto.consumer.sales.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final String MY_DEPARTMENT = "sales";

    @Autowired
    private KmsService kmsService;

    /*@KafkaListener(topics = "toopic-name", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }*/

    /**
     *   kafka-console-consumer \
     *   --topic <topic name>\
     *   --bootstrap-server <server endpoint> \
     *   --consumer-property group.id=<group consumer id> \
     *   --from-beginning \
     *   --property print.key=true \
     *   --property key.separator=“<key separator, ex: |>” \
     *   --property print.partition=true \
     *   --partition <partition number>
     *
     * @param data
     * @param partition
     */
    @KafkaListener(topics = "#{@getTopicName}", containerFactory = "dataKafkaListenerContainerFactory")
    public void processMessage(@Payload Data data,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        if(data.getDepartment().equals(MY_DEPARTMENT)) {
            System.out.println("---------------- ORIGINAL MESSAGE --------------");
            System.out.println("{\"department\": \"" + data.getDepartment() + "\", \"data\": \"" + data.getData() + "\"");
            System.out.println("---------------- DECRIPTED MESSAGE --------------");
            System.out.println("{\"department\": \"" + data.getDepartment() + "\", \"data\": \"" + kmsService.decryptData(data.getData())+ "\"");
            System.out.println("---------------- PARTITION --------------");
            System.out.println(partition);
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
        }
    }

}