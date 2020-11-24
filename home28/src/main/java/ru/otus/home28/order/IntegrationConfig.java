package ru.otus.home28.order;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.home28.domain.Order;
import ru.otus.home28.domain.OrderState;

@Configuration
@RequiredArgsConstructor
class IntegrationConfig {
    private final OrderService orderService;
    private final RobotService robotService;

    @Bean
    IntegrationFlow createFlow() {
        return f -> f
                .<Order>handle((o, h) -> {
                    orderService.checkNew(o);
                    return o;
                })
                .<Order>handle((o, h) -> {
                    robotService.checkNewOrder(o);
                    return o;
                })
                .<Order>handle((o, h) -> {
                    orderService.checkTimeSlot(o, o.getPlannedDate());
                    return o;
                })
                .<Order>handle((o, h) -> orderService.saveOrder(o.copy().setState(OrderState.CREATE, EntryPoint.getSource(h)), o.getState()));
    }

    @Bean
    IntegrationFlow readyFlow() {
        return f -> f
                .<Order>handle((o, h) -> {
                    robotService.checkReadyOrder(o);
                    return o;
                })
                .<Order>handle((o, h) -> orderService.saveOrder(o.copy().setState(OrderState.READY, EntryPoint.getSource(h)), o.getState()));
    }

    @Bean
    PublishSubscribeChannel orderStateChangedChannel() {
        return MessageChannels.publishSubscribe().get();
    }
}
