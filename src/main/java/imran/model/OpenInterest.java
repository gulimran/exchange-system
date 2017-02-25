package imran.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

public class OpenInterest {

    private Map<Double, Integer> interests = new HashMap<>();

    public OpenInterest(Map<Double, Integer> interests) {
        this.interests = interests;
    }

    public Map<Double, Integer> getInterests() {
        return interests;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
