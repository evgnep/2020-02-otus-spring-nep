package ru.otus.home28.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.home28.domain.Order;
import ru.otus.home28.repository.OrderRepository;
import ru.otus.home28.robot.Robot;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RobotServiceImpl implements RobotService {
    private final List<Robot> robots;
    private final OrderRepository orderRepository;

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
        if (!orderRepository.getReady(order.getRobot()).isEmpty())
            throw new RuntimeException("Уже есть заказ в Готовности");
    }
}
