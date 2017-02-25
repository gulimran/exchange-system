package imran.service.impl;

import imran.api.OrderService;
import imran.dao.ExecutedOrdersDao;
import imran.dao.OpenOrdersDao;
import imran.matcher.OrderMatcher;
import imran.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
public class OrderServiceImpl implements OrderService {

    @Inject
    private OpenOrdersDao openOrdersDao;

    @Inject
    private ExecutedOrdersDao executedOrdersDao;

    @Inject
    private OrderMatcher orderMatcher;

    @Override
    public Optional<ExecutedOrder> addOrder(Order order) {
        List<Order> openOrders = openOrdersDao.findBy(order.getReutersInstrumentCode());
        Optional<Order> matchedOrder = orderMatcher.matchOrder(order, openOrders);

        if (matchedOrder.isPresent()) {
            openOrdersDao.remove(matchedOrder.get());
            List<Order> ordersToExecute = Arrays.asList(order, matchedOrder.get());
            ExecutedOrder executedOrder = new ExecutedOrder(ordersToExecute, order.getPrice());
            executedOrdersDao.save(executedOrder);
            return Optional.of(executedOrder);
        } else {
            openOrdersDao.save(order);
            return Optional.empty();
        }
    }

    @Override
    public Optional<OpenInterest> getOpenInterest(ReutersInstrumentCode reutersInstrumentCode, Direction direction) {
        List<Order> openOrders = openOrdersDao.findBy(reutersInstrumentCode, direction);

        if (openOrders.isEmpty()) {
            return Optional.empty();
        }

        Map<Double, Integer> interests = new HashMap<>();
        openOrders.forEach(order -> interests.put(order.getPrice().getAmount(), order.getQuantity()));

        return Optional.of(new OpenInterest(interests));
    }

    @Override
    public Optional<AverageExecutionPrice> getAverageExecutionPrice(ReutersInstrumentCode reutersInstrumentCode) {
        List<ExecutedOrder> executedOrders = executedOrdersDao.findBy(reutersInstrumentCode);

        if (executedOrders.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new AverageExecutionPrice(executedOrders.stream()
                .mapToDouble(o -> o.getExecutionPrice().getAmount()).average().orElse(0.0)));
    }

    @Override
    public Optional<ExecutedQuantity> getExecutedQuantity(ReutersInstrumentCode reutersInstrumentCode, User user) {
        List<ExecutedOrder> executedOrders = executedOrdersDao.findBy(reutersInstrumentCode, user);

        if (executedOrders.isEmpty()) {
            return Optional.empty();
        }

        Integer quantity = 0;

        for (ExecutedOrder executedOrder : executedOrders) {
            for (Order order : executedOrder.getOrders()) {
                if (order.getUser().equals(user)) {
                    if (order.getDirection().equals(Direction.BUY)) {
                        quantity += order.getQuantity();
                    } else {
                        quantity -= order.getQuantity();
                    }
                }
            }
        }

        return Optional.of(new ExecutedQuantity(quantity));
    }
}
