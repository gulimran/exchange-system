package imran.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Price {

    private Double amount;

    public Price(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(amount, price.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
