package de.stphngrtz.hellokafka;

import java.io.Serializable;
import java.math.BigDecimal;

public class MyDTO implements Serializable {
    public int id;
    public String name;
    public BigDecimal value;

    @Override
    public String toString() {
        return "MyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
