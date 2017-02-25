package imran.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Order {

    private Direction direction;
    private ReutersInstrumentCode reutersInstrumentCode;
    private User user;
    private Price price;
    private Integer quantity;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ReutersInstrumentCode getReutersInstrumentCode() {
        return reutersInstrumentCode;
    }

    public void setReutersInstrumentCode(ReutersInstrumentCode reutersInstrumentCode) {
        this.reutersInstrumentCode = reutersInstrumentCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(direction, order.direction) &&
                Objects.equals(reutersInstrumentCode, order.reutersInstrumentCode) &&
                Objects.equals(user, order.user) &&
                Objects.equals(price, order.price) &&
                Objects.equals(quantity, order.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, reutersInstrumentCode, user, price, quantity);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
