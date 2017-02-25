package imran.dao.impl;

import imran.dao.ExecutedOrdersDao;
import imran.model.ExecutedOrder;
import imran.model.ReutersInstrumentCode;
import imran.model.User;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static imran.dao.impl.DataSource.EXECUTED_ORDERS;

@Named
public class ExecutedOrderDaoInMemoryImpl implements ExecutedOrdersDao {

    @Override
    public void save(ExecutedOrder order) {
        EXECUTED_ORDERS.add(order);
    }

    @Override
    public List<ExecutedOrder> findBy(ReutersInstrumentCode ric) {
        return EXECUTED_ORDERS.stream()
                .filter(executedOrders -> executedOrders.getOrders().get(0).getReutersInstrumentCode().equals(ric))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExecutedOrder> findBy(ReutersInstrumentCode ric, User user) {
        List<ExecutedOrder> resultList = new ArrayList<>();

        for (ExecutedOrder executedOrder : EXECUTED_ORDERS) {
            resultList.addAll(executedOrder.getOrders().stream()
                    .filter(order -> order.getReutersInstrumentCode().equals(ric) && order.getUser().equals(user))
                    .map(order -> executedOrder)
                    .collect(Collectors.toList()));
        }

        return resultList;
    }
}
