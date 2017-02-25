package imran.matcher;

import imran.model.Order;
import org.junit.Before;
import org.junit.Test;

import static imran.builder.OrderBuilder.order;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DirectionMatcherTest {

    private DirectionMatcher matcher;

    @Before
    public void setup() {
        matcher = new DirectionMatcher();
    }

    @Test
    public void shouldReturnTrue_WhenOrdersHaveOpposingDirections() {
        Order order1 = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        Order order2 = order("SELL", "TEST_CODE", "TEST_USER", 20.22, 50);

        assertThat(matcher.matches(order1, order2), is(true));
    }

    @Test
    public void shouldReturnFalse_WhenOrdersHaveSameDirections() {
        Order order1 = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        Order order2 = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);

        assertThat(matcher.matches(order1, order2), is(false));
    }

}