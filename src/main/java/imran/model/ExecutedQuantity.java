package imran.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ExecutedQuantity {

    private Integer quantity = 0;

    public ExecutedQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
