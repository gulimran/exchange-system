package imran.acceptance.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import imran.acceptance.data.TestData;
import imran.api.OrderService;
import imran.dao.impl.DataSource;
import imran.model.ExecutedOrder;
import imran.model.Order;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class AddOneOrderSteps {

    @Inject
    private OrderService orderService;

    private Order newOrder;

    private Optional<ExecutedOrder> executedOrders;

    @Given("^an order consists of direction, RIC, quantity, price and user$")
    public void an_order_consists_of_direction_RIC_quantity_price_and_user() throws Throwable {
        newOrder = TestData.ORDERS().get(0);
    }

    @When("^the order is added into empty database$")
    public void the_order_is_added_into_empty_database() throws Throwable {
        executedOrders = orderService.addOrder(newOrder);
    }

    @Then("^I should get no executed order$")
    public void I_should_get_no_executed_order() throws Throwable {
        assertThat(executedOrders.isPresent(), is(false));
        assertThat(DataSource.OPEN_ORDERS, hasSize(1));
    }
}
