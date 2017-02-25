package imran.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AverageExecutionPrice {

    private Double average;

    public AverageExecutionPrice(Double average) {
        this.average = average;
    }

    public Double getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
