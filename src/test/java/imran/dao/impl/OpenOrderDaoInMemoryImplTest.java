package imran.dao.impl;

import imran.dao.OpenOrdersDao;
import imran.model.Direction;
import imran.model.Order;
import imran.model.ReutersInstrumentCode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static imran.builder.OrderBuilder.order;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OpenOrderDaoInMemoryImplTest {

    private OpenOrdersDao openOrderDao;

    @Before
    public void setup() {
        openOrderDao = new OpenOrderDaoInMemoryImpl();
        DataSource.OPEN_ORDERS.clear();
    }

    @Test
    public void shouldSaveNewOrder() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);

        openOrderDao.save(order);

        assertThat(DataSource.OPEN_ORDERS, containsInAnyOrder(order));
    }

    @Test
    public void shouldSaveMultipleNewOrders() {
        Order order1 = order("BUY", "TEST_CODE_1", "TEST_USER_1", 20.22, 50);
        Order order2 = order("SELL", "TEST_CODE_2", "TEST_USER_2", 20.22, 50);

        openOrderDao.save(order1);
        openOrderDao.save(order2);

        assertThat(DataSource.OPEN_ORDERS.size(), is(2));
        assertThat(DataSource.OPEN_ORDERS, containsInAnyOrder(order1, order2));
    }

    @Test
    public void shouldNotSaveNewOrder_WhenItMatchesWithExistingOrder() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);

        openOrderDao.save(order);
        openOrderDao.save(order);

        assertThat(DataSource.OPEN_ORDERS.size(), is(1));
    }

    @Test
    public void shouldGetOrder_ByReutersInformationCode() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        openOrderDao.save(order);

        List<Order> result = openOrderDao.findBy(new ReutersInstrumentCode("TEST_CODE"));

        assertThat(result, contains(order));
    }

    @Test
    public void shouldNotGetOrder_ByNonMatchingReutersInformationCode() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        openOrderDao.save(order);

        List<Order> result = openOrderDao.findBy(new ReutersInstrumentCode("NON_MATCHING_CODE"));

        assertThat(result.size(), is(0));
    }

    @Test
    public void shouldGetOrder_ByReutersInformationCodeAndDirection() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        openOrderDao.save(order);

        List<Order> result = openOrderDao.findBy(new ReutersInstrumentCode("TEST_CODE"), Direction.BUY);

        assertThat(result, contains(order));
    }

    @Test
    public void shouldNotGetOrder_ByNonMatchingReutersInformationCodeAndDrection() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        openOrderDao.save(order);

        List<Order> result = openOrderDao.findBy(new ReutersInstrumentCode("NON_MATCHING_CODE"), Direction.SELL);

        assertThat(result.size(), is(0));
    }

}