package imran.service.impl;

import com.google.common.collect.ImmutableList;
import imran.api.OrderService;
import imran.dao.ExecutedOrdersDao;
import imran.dao.OpenOrdersDao;
import imran.matcher.OrderMatcher;
import imran.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static imran.builder.OrderBuilder.order;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    @Mock
    private OpenOrdersDao openOrdersDao;

    @Mock
    private ExecutedOrdersDao executedOrdersDao;

    @Mock
    private OrderMatcher orderMatcher;

    @Test
    public void shouldAddFirstOpenOrder_ThenNoOrderIsExecuted() {
        // given
        Order newOrder = order("SELL", "TEST_CODE", "TEST_USER", 20.22, 50);
        List<Order> openOrders = ImmutableList.of();

        given(openOrdersDao.findBy(newOrder.getReutersInstrumentCode())).willReturn(openOrders);
        given(orderMatcher.matchOrder(newOrder, openOrders)).willReturn(Optional.<Order>empty());

        // when
        Optional<ExecutedOrder> executedOrder = orderService.addOrder(newOrder);

        // then
        assertThat(executedOrder.isPresent(), is(false));
        verify(openOrdersDao).save(newOrder);
        verifyZeroInteractions(executedOrdersDao);
    }

    @Test
    public void shouldAddNonMatchingOpenOrder_ThenNoOrderIsExecuted() {
        // given
        Order newOrder = order("SELL", "TEST_CODE", "TEST_USER", 20.22, 50);
        Order openOrder = order("SELL", "TEST_CODE_2", "TEST_USER_2", 20.22, 50);
        List<Order> openOrders = ImmutableList.of(openOrder);

        given(openOrdersDao.findBy(newOrder.getReutersInstrumentCode())).willReturn(openOrders);
        given(orderMatcher.matchOrder(newOrder, openOrders)).willReturn(Optional.<Order>empty());

        // when
        Optional<ExecutedOrder> executedOrder = orderService.addOrder(newOrder);

        // then
        assertThat(executedOrder.isPresent(), is(false));
        verify(openOrdersDao).save(newOrder);
        verifyZeroInteractions(executedOrdersDao);
    }

    @Test
    public void shouldRemoveMatchingOpenOrder_AndOrdersAreExecuted() {
        // given
        Order newOrder = order("SELL", "TEST_CODE", "TEST_USER", 19.22, 50);
        Order openOrder = order("BUY", "TEST_CODE", "TEST_USER_2", 20.22, 50);
        List<Order> openOrders = ImmutableList.of(openOrder);

        given(openOrdersDao.findBy(newOrder.getReutersInstrumentCode())).willReturn(openOrders);
        given(orderMatcher.matchOrder(newOrder, openOrders)).willReturn(Optional.of(openOrder));

        // when
        Optional<ExecutedOrder> executedOrder = orderService.addOrder(newOrder);

        // then
        assertThat(executedOrder.isPresent(), is(true));
        assertThat(executedOrder.get().getOrders(), containsInAnyOrder(newOrder, openOrder));
        assertThat(executedOrder.get().getExecutionPrice().getAmount(), is(19.22));
        verify(openOrdersDao).remove(openOrder);
        verify(executedOrdersDao).save(any(ExecutedOrder.class));
    }

    @Test
    public void shouldReportNoOpenInterest_ForGivenReutersInformationCodeAndDirecion() {
        // given
        ReutersInstrumentCode reutersInstrumentCode = new ReutersInstrumentCode("TEST_CODE");
        Direction buy = Direction.BUY;
        given(openOrdersDao.findBy(reutersInstrumentCode, buy)).willReturn(ImmutableList.of());

        // when
        Optional<OpenInterest> openInterest = orderService.getOpenInterest(reutersInstrumentCode, buy);

        // then
        assertThat(openInterest.isPresent(), is(false));
    }

    @Test
    public void shouldReportOpenInterest_ForGivenReutersInformationCodeAndDirection() {
        // given
        ReutersInstrumentCode reutersInstrumentCode = new ReutersInstrumentCode("TEST_CODE");
        Direction buy = Direction.BUY;
        Integer quantity = 50;
        Double price = 20.22;
        Order openOrder = order("BUY", "TEST_CODE", "TEST_USER_2", price, quantity);
        given(openOrdersDao.findBy(reutersInstrumentCode, buy)).willReturn(ImmutableList.of(openOrder));

        // when
        Optional<OpenInterest> openInterest = orderService.getOpenInterest(reutersInstrumentCode, buy);

        // then
        assertThat(openInterest.isPresent(), is(true));
        assertThat(openInterest.get().getInterests().get(price), is(quantity));
    }

    @Test
    public void shouldNotGetAverageExecutionPrice_WhenNoExecutedOrders() {
        // given
        ReutersInstrumentCode reutersInstrumentCode = new ReutersInstrumentCode("TEST_CODE");
        given(executedOrdersDao.findBy(reutersInstrumentCode)).willReturn(ImmutableList.of());

        // when
        Optional<AverageExecutionPrice> averageExecutionPrice = orderService.getAverageExecutionPrice(reutersInstrumentCode);

        // then
        assertThat(averageExecutionPrice.isPresent(), is(false));
    }

    @Test
    public void shouldGetAverageExecutionPrice_ForSingleExecutedOrder() {
        // given
        Order order1 = order("SELL", "TEST_CODE", "TEST_USER_1", 19.22, 50);
        Order order2 = order("BUY", "TEST_CODE", "TEST_USER_2", 20.00, 50);
        ReutersInstrumentCode reutersInstrumentCode = new ReutersInstrumentCode("TEST_CODE");
        ExecutedOrder executedOrder = new ExecutedOrder(ImmutableList.of(order1, order2), new Price(20.00));
        given(executedOrdersDao.findBy(reutersInstrumentCode)).willReturn(ImmutableList.of(executedOrder));

        // when
        Optional<AverageExecutionPrice> averageExecutionPrice = orderService.getAverageExecutionPrice(reutersInstrumentCode);

        // then
        assertThat(averageExecutionPrice.isPresent(), is(true));
        assertThat(averageExecutionPrice.get().getAverage(), is(20.00));
    }

    @Test
    public void shouldGetAverageExecutionPrice_ForMultipleExecutedOrders() {
        // given
        Order order1 = order("SELL", "TEST_CODE", "TEST_USER_1", 10.00, 50);
        Order order2 = order("BUY", "TEST_CODE", "TEST_USER_2", 20.00, 50);
        ReutersInstrumentCode reutersInstrumentCode = new ReutersInstrumentCode("TEST_CODE");
        ExecutedOrder executedOrder1 = new ExecutedOrder(ImmutableList.of(order1, order2), new Price(20.00));
        ExecutedOrder executedOrder2 = new ExecutedOrder(ImmutableList.of(order1, order2), new Price(10.00));
        given(executedOrdersDao.findBy(reutersInstrumentCode)).willReturn(ImmutableList.of(executedOrder1, executedOrder2));

        // when
        Optional<AverageExecutionPrice> averageExecutionPrice = orderService.getAverageExecutionPrice(reutersInstrumentCode);

        // then
        assertThat(averageExecutionPrice.isPresent(), is(true));
        assertThat(averageExecutionPrice.get().getAverage(), is(15.00));
    }

    @Test
    public void shouldNotGetExecutionQuantity_ForGivenReutersInformationCodeAndUser() {
        // given
        ReutersInstrumentCode reutersInstrumentCode = new ReutersInstrumentCode("TEST_CODE");
        User user = new User("TEST_USER");
        given(executedOrdersDao.findBy(reutersInstrumentCode, user)).willReturn(ImmutableList.of());

        // when
        Optional<ExecutedQuantity> executedQuantity = orderService.getExecutedQuantity(reutersInstrumentCode, user);

        // then
        assertThat(executedQuantity.isPresent(), is(false));
    }

    @Test
    public void shouldGetExecutionQuantity_ForGivenReutersInformationCodeAndUser() {
        // given
        ReutersInstrumentCode reutersInstrumentCode = new ReutersInstrumentCode("TEST_CODE");
        Order order1 = order("SELL", "TEST_CODE", "TEST_USER_1", 19.22, 50);
        Order order2 = order("BUY", "TEST_CODE", "TEST_USER_2", 20.00, 50);
        ExecutedOrder executedOrder = new ExecutedOrder(ImmutableList.of(order1, order2), new Price(20.00));
        User user1 = new User("TEST_USER_1");
        User user2 = new User("TEST_USER_2");
        given(executedOrdersDao.findBy(reutersInstrumentCode, user1)).willReturn(ImmutableList.of(executedOrder));
        given(executedOrdersDao.findBy(reutersInstrumentCode, user2)).willReturn(ImmutableList.of(executedOrder));

        // when
        Optional<ExecutedQuantity> executedQuantity1 = orderService.getExecutedQuantity(reutersInstrumentCode, user1);
        Optional<ExecutedQuantity> executedQuantity2 = orderService.getExecutedQuantity(reutersInstrumentCode, user2);

        // then
        assertThat(executedQuantity1.isPresent(), is(true));
        assertThat(executedQuantity2.isPresent(), is(true));
        assertThat(executedQuantity1.get().getQuantity(), is(-50));
        assertThat(executedQuantity2.get().getQuantity(), is(50));
    }

}