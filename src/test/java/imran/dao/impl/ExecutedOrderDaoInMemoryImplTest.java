package imran.dao.impl;

import imran.dao.ExecutedOrdersDao;
import imran.model.ExecutedOrder;
import imran.model.Order;
import imran.model.ReutersInstrumentCode;
import imran.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static imran.builder.OrderBuilder.order;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExecutedOrderDaoInMemoryImplTest {

    private ExecutedOrdersDao executedOrdersDao;

    @Before
    public void setup() {
        executedOrdersDao = new ExecutedOrderDaoInMemoryImpl();
        DataSource.EXECUTED_ORDERS.clear();
    }

    @Test
    public void shouldSaveNewOrder() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        ExecutedOrder executedOrder = new ExecutedOrder(Collections.singletonList(order), order.getPrice());

        executedOrdersDao.save(executedOrder);

        assertThat(DataSource.EXECUTED_ORDERS, containsInAnyOrder(executedOrder));
    }

    @Test
    public void shouldNotSaveNewOrder_WhenItMatchesWithExistingOrder() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        ExecutedOrder executedOrder = new ExecutedOrder(Collections.singletonList(order), order.getPrice());

        executedOrdersDao.save(executedOrder);
        executedOrdersDao.save(executedOrder);

        assertThat(DataSource.EXECUTED_ORDERS.size(), is(1));
    }

    @Test
    public void shouldGetOrder_ByReutersInformationCode() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        ExecutedOrder executedOrder = new ExecutedOrder(Collections.singletonList(order), order.getPrice());

        executedOrdersDao.save(executedOrder);

        List<ExecutedOrder> result = executedOrdersDao.findBy(new ReutersInstrumentCode("TEST_CODE"));

        assertThat(result, contains(executedOrder));
    }

    @Test
    public void shouldGetOrder_ByReutersInformationCodeAndUser() {
        User user = new User("TEST_USER");
        Order order = order("BUY", "TEST_CODE", user.getName(), 20.22, 50);
        ExecutedOrder executedOrder = new ExecutedOrder(Collections.singletonList(order), order.getPrice());

        executedOrdersDao.save(executedOrder);

        List<ExecutedOrder> result = executedOrdersDao.findBy(new ReutersInstrumentCode("TEST_CODE"), user);

        assertThat(result, contains(executedOrder));
    }


}