package imran.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExecutedOrder {

    private List<Order> orders = new ArrayList<>();
    private Price executionPrice;

    public ExecutedOrder(List<Order> orders, Price executionPrice) {
        this.orders = orders;
        this.executionPrice = executionPrice;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Price getExecutionPrice() {
        return executionPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutedOrder that = (ExecutedOrder) o;
        return Objects.equals(orders, that.orders) &&
                Objects.equals(executionPrice, that.executionPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, executionPrice);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
