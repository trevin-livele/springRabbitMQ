package com.trevin.rabitmqtwo.publisher;


import com.trevin.rabitmqtwo.config.MessangingConfig;
import com.trevin.rabitmqtwo.dto.Order;
import com.trevin.rabitmqtwo.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {

    @Autowired
    public RabbitTemplate template;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName){
        order.setOrderId(UUID.randomUUID().toString());

        //restaurant service
        // payment service

        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed successfully in" + restaurantName);
        template.convertAndSend(MessangingConfig.EXCHANGE,MessangingConfig.ROUTING_KEY, orderStatus);
        return "Success !!";

    }
}
