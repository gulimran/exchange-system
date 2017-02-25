package imran.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class ReutersInstrumentCode {

    private String code;

    public ReutersInstrumentCode() {
    }

    public ReutersInstrumentCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReutersInstrumentCode that = (ReutersInstrumentCode) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
