package ru.otus.home28.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.home28.domain.Order;
import ru.otus.home28.domain.OrderState;
import ru.otus.home28.repository.OrderRepository;
import ru.otus.home28.robot.Robot;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
class RobotServiceImpl implements RobotService {
    private final List<Robot> robots;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    public void checkNewOrder(Order order) {
        var robotNo = order.getRobot();
        if (robotNo < 0 || robotNo >= robots.size())
            throw new RuntimeException("Неизвестный робот");

        var robot = robots.get(robotNo);
        robot.checkOrder(order);
    }

    @Override
    public void checkReadyOrder(Order order) {
    }

    @Scheduled(fixedDelay = 1000)
    public void startSetOrder() {
        log.info("startSetOrder");
        for (var order : orderRepository.getReady(LocalDateTime.now())) {
            robots.get(order.getRobot()).setOrder(order);
            orderService.saveOrder(order.setState(OrderState.WASH, "Задан на робота"), OrderState.READY);
        }
    }
}
