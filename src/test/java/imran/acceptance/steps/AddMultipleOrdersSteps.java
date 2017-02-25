package imran.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import imran.acceptance.data.TestData;
import imran.api.OrderService;
import imran.dao.impl.DataSource;
import imran.model.*;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class AddMultipleOrdersSteps {

    @Inject
    private OrderService orderService;

    private List<Optional<ExecutedOrder>> executedOrders = new ArrayList<>(7);
    private List<Optional<OpenInterest>> openInterestBuys = new ArrayList<>(7);
    private List<Optional<OpenInterest>> openInterestSells = new ArrayList<>(7);
    private List<Optional<AverageExecutionPrice>> averageExecutionPrices = new ArrayList<>(7);
    private List<Optional<ExecutedQuantity>> executedQuantitiesUser1 = new ArrayList<>(7);
    private List<Optional<ExecutedQuantity>> executedQuantitiesUser2 = new ArrayList<>(7);

    @Given("^multiple orders consists of direction, RIC, quantity, price and user$")
    public void multiple_orders_consists_of_direction_RIC_quantity_price_and_user() throws Throwable {
        DataSource.OPEN_ORDERS.clear();
        DataSource.EXECUTED_ORDERS.clear();
    }

    @When("^the orders are added into empty database$")
    public void the_orders_are_added_into_empty_database() throws Throwable {
        executedOrders.add(orderService.addOrder(TestData.ORDERS().get(0)));
        openInterestBuys.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.BUY));
        openInterestSells.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.SELL));
        averageExecutionPrices.add(orderService.getAverageExecutionPrice(new ReutersInstrumentCode("VOD.L")));
        executedQuantitiesUser1.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User1")));
        executedQuantitiesUser2.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User2")));

        executedOrders.add(orderService.addOrder(TestData.ORDERS().get(1)));
        openInterestBuys.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.BUY));
        openInterestSells.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.SELL));
        averageExecutionPrices.add(orderService.getAverageExecutionPrice(new ReutersInstrumentCode("VOD.L")));
        executedQuantitiesUser1.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User1")));
        executedQuantitiesUser2.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User2")));

        executedOrders.add(orderService.addOrder(TestData.ORDERS().get(2)));
        openInterestBuys.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.BUY));
        openInterestSells.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.SELL));
        averageExecutionPrices.add(orderService.getAverageExecutionPrice(new ReutersInstrumentCode("VOD.L")));
        executedQuantitiesUser1.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User1")));
        executedQuantitiesUser2.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User2")));

        executedOrders.add(orderService.addOrder(TestData.ORDERS().get(3)));
        openInterestBuys.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.BUY));
        openInterestSells.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.SELL));
        averageExecutionPrices.add(orderService.getAverageExecutionPrice(new ReutersInstrumentCode("VOD.L")));
        executedQuantitiesUser1.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User1")));
        executedQuantitiesUser2.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User2")));

        executedOrders.add(orderService.addOrder(TestData.ORDERS().get(4)));
        openInterestBuys.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.BUY));
        openInterestSells.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.SELL));
        averageExecutionPrices.add(orderService.getAverageExecutionPrice(new ReutersInstrumentCode("VOD.L")));
        executedQuantitiesUser1.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User1")));
        executedQuantitiesUser2.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User2")));

        executedOrders.add(orderService.addOrder(TestData.ORDERS().get(5)));
        openInterestBuys.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.BUY));
        openInterestSells.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.SELL));
        averageExecutionPrices.add(orderService.getAverageExecutionPrice(new ReutersInstrumentCode("VOD.L")));
        executedQuantitiesUser1.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User1")));
        executedQuantitiesUser2.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User2")));

        executedOrders.add(orderService.addOrder(TestData.ORDERS().get(6)));
        openInterestBuys.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.BUY));
        openInterestSells.add(orderService.getOpenInterest(new ReutersInstrumentCode("VOD.L"), Direction.SELL));
        averageExecutionPrices.add(orderService.getAverageExecutionPrice(new ReutersInstrumentCode("VOD.L")));
        executedQuantitiesUser1.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User1")));
        executedQuantitiesUser2.add(orderService.getExecutedQuantity(new ReutersInstrumentCode("VOD.L"), new User("User2")));
    }

    @Then("^I should get executed orders with expected execution price$")
    public void I_should_get_executed_orders_with_expected_execution_price() throws Throwable {
        assertThat(executedOrders.get(0).isPresent(), is(false));
        assertThat(executedOrders.get(1).get().getExecutionPrice().getAmount(), is(100.2));
        assertThat(executedOrders.get(2).isPresent(), is(false));
        assertThat(executedOrders.get(3).isPresent(), is(false));
        assertThat(executedOrders.get(4).isPresent(), is(false));
        assertThat(executedOrders.get(5).get().getExecutionPrice().getAmount(), is(103.0));
        assertThat(executedOrders.get(6).get().getExecutionPrice().getAmount(), is(98.0));

        assertThat(DataSource.OPEN_ORDERS, hasSize(1));
        assertThat(DataSource.EXECUTED_ORDERS, hasSize(3));
    }

    @And("^verify open interest after each order$")
    public void verify_open_interest_after_each_order() throws Throwable {
        assertThat(openInterestBuys.get(0).isPresent(), is(false));
        assertThat(openInterestSells.get(0).get().getInterests().get(100.2), is(1000));

        assertThat(openInterestBuys.get(1).isPresent(), is(false));
        assertThat(openInterestSells.get(1).isPresent(), is(false));

        assertThat(openInterestBuys.get(2).get().getInterests().get(99.0), is(1000));
        assertThat(openInterestSells.get(2).isPresent(), is(false));

        assertThat(openInterestBuys.get(3).get().getInterests().get(99.0), is(1000));
        assertThat(openInterestBuys.get(3).get().getInterests().get(101.0), is(1000));
        assertThat(openInterestSells.get(3).isPresent(), is(false));

        assertThat(openInterestBuys.get(4).get().getInterests().get(99.0), is(1000));
        assertThat(openInterestBuys.get(4).get().getInterests().get(101.0), is(1000));
        assertThat(openInterestSells.get(4).get().getInterests().get(102.0), is(500));

        assertThat(openInterestBuys.get(5).get().getInterests().get(99.0), is(1000));
        assertThat(openInterestBuys.get(5).get().getInterests().get(101.0), is(1000));
        assertThat(openInterestSells.get(5).isPresent(), is(false));

        assertThat(openInterestBuys.get(6).get().getInterests().get(99.0), is(1000));
        assertThat(openInterestSells.get(6).isPresent(), is(false));

    }

    @And("^verify average execution price after each order$")
    public void verify_average_execution_price_after_each_order() throws Throwable {
        assertThat(averageExecutionPrices.get(0).isPresent(), is(false));
        assertThat(averageExecutionPrices.get(1).get().getAverage(), is(100.2));
        assertThat(averageExecutionPrices.get(2).get().getAverage(), is(100.2));
        assertThat(averageExecutionPrices.get(3).get().getAverage(), is(100.2));
        assertThat(averageExecutionPrices.get(4).get().getAverage(), is(100.2));
        assertThat(averageExecutionPrices.get(5).get().getAverage(), is(101.6));
        assertThat(averageExecutionPrices.get(6).get().getAverage(), is(100.39999999999999));
    }

    @And("^verify executed quantity for each user after each order$")
    public void verify_executed_quantity_for_each_user_after_each_order() throws Throwable {
        assertThat(executedQuantitiesUser1.get(0).isPresent(), is(false));
        assertThat(executedQuantitiesUser2.get(0).isPresent(), is(false));

        assertThat(executedQuantitiesUser1.get(1).get().getQuantity(), is(-1000));
        assertThat(executedQuantitiesUser2.get(1).get().getQuantity(), is(1000));

        assertThat(executedQuantitiesUser1.get(2).get().getQuantity(), is(-1000));
        assertThat(executedQuantitiesUser2.get(2).get().getQuantity(), is(1000));

        assertThat(executedQuantitiesUser1.get(3).get().getQuantity(), is(-1000));
        assertThat(executedQuantitiesUser2.get(3).get().getQuantity(), is(1000));

        assertThat(executedQuantitiesUser1.get(4).get().getQuantity(), is(-1000));
        assertThat(executedQuantitiesUser2.get(4).get().getQuantity(), is(1000));

        assertThat(executedQuantitiesUser1.get(5).get().getQuantity(), is(-500));
        assertThat(executedQuantitiesUser2.get(5).get().getQuantity(), is(500));

        assertThat(executedQuantitiesUser1.get(6).get().getQuantity(), is(500));
        assertThat(executedQuantitiesUser2.get(6).get().getQuantity(), is(-500));
    }
}
