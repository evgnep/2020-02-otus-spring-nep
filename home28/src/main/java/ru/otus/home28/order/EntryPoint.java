package ru.otus.home28.order;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import ru.otus.home28.domain.Order;
import ru.otus.home28.domain.OrderState;

import java.util.UUID;

@MessagingGateway
interface EntryPoint {
    String SOURCE = "source";
    String PREV_STATE = "prevState";

    static String getSource(MessageHeaders headers) {
        return headers.get(SOURCE, String.class);
    }

    @Gateway(requestChannel = "createFlow.input")
    UUID create(Order order, @Header(SOURCE) String source);

    @Gateway(requestChannel = "readyFlow.input")
    void ready(Order order, @Header(SOURCE) String source);

    @Gateway(requestChannel = "orderStateChangedChannel")
    void stateChanged(Order order, @Header(PREV_STATE) OrderState prevState);
}
