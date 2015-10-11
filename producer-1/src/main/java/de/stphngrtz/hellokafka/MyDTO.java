package de.stphngrtz.hellokafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class MyDTO {
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

    public String toJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);
    }
}
