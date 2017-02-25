package imran.matcher;

import imran.model.Order;
import org.junit.Before;
import org.junit.Test;

import static imran.builder.OrderBuilder.order;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PriceMatcherTest {

    private PriceMatcher matcher;

    @Before
    public void setup() {
        matcher = new PriceMatcher();
    }

    @Test
    public void shouldReturnTrue_WhenNewOderSellPriceIsLessThanBuyPrice() {
        Order newOrder = order("SELL", "TEST_CODE", "TEST_USER", 20.22, 50);
        Order existingOrder = order("BUY", "TEST_CODE", "TEST_USER", 30.00, 50);

        assertThat(matcher.matches(newOrder, existingOrder), is(true));
    }

    @Test
    public void shouldReturnFalse_WhenNewOderSellPriceIsGreaterThanBuyPrice() {
        Order newOrder = order("SELL", "TEST_CODE", "TEST_USER", 20.22, 50);
        Order existingOrder = order("BUY", "TEST_CODE", "TEST_USER", 10.22, 50);

        assertThat(matcher.matches(newOrder, existingOrder), is(false));
    }

    @Test
    public void shouldReturnTrue_WhenExistingOderSellPriceIsEqualToBuyPrice() {
        Order newOrder = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        Order existingOrder = order("SELL", "TEST_CODE", "TEST_USER", 20.22, 50);

        assertThat(matcher.matches(newOrder, existingOrder), is(true));
    }

    @Test
    public void shouldReturnFalse_WhenExistingOderSellPriceIsGreaterThanBuyPrice() {
        Order newOrder = order("BUY", "TEST_CODE", "TEST_USER", 20.22, 50);
        Order existingOrder = order("BUY", "TEST_CODE", "TEST_USER", 60.22, 50);

        assertThat(matcher.matches(newOrder, existingOrder), is(false));
    }

}