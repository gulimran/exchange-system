package imran.matcher;

import imran.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static imran.builder.OrderBuilder.order;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class OrderMatcherTest {

    @InjectMocks
    private OrderMatcher orderMatcher = new OrderMatcher();

    @Mock
    private DirectionMatcher directionMatcher;

    @Mock
    private ReutersInstrumentCodeMatcher reutersInstrumentCodeMatcher;

    @Mock
    private QuantityMatcher quantityMatcher;

    @Mock
    private PriceMatcher priceMatcher;

    @Test
    public void shouldNotMatchOrder_WhenNoExistingOpenOrders() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);

        Optional<Order> result = orderMatcher.matchOrder(order, Collections.<Order>emptyList());

        assertThat(result.isPresent(), is((false)));
    }

    @Test
    public void shouldMatchOrder() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);

        given(reutersInstrumentCodeMatcher.matches(any(Order.class), any(Order.class))).willReturn(true);
        given(directionMatcher.matches(any(Order.class), any(Order.class))).willReturn(true);
        given(quantityMatcher.matches(any(Order.class), any(Order.class))).willReturn(true);
        given(priceMatcher.matches(any(Order.class), any(Order.class))).willReturn(true);

        Optional<Order> result = orderMatcher.matchOrder(order, Collections.singletonList(order));

        assertThat(result.get(), is(order));
    }

    @Test
    public void shouldNotMatchOrder_WhenReutersInstrumentCodeMatcherReturnsFalse() {
        Order order = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);

        given(reutersInstrumentCodeMatcher.matches(any(Order.class), any(Order.class))).willReturn(false);
        given(directionMatcher.matches(any(Order.class), any(Order.class))).willReturn(true);
        given(quantityMatcher.matches(any(Order.class), any(Order.class))).willReturn(true);
        given(priceMatcher.matches(any(Order.class), any(Order.class))).willReturn(true);

        Optional<Order> result = orderMatcher.matchOrder(order, Collections.singletonList(order));

        assertThat(result.isPresent(), is(false));
    }

}