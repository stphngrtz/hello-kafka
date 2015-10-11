package de.stphngrtz.hellokafka;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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

    public static MyDTO fromJSON(String json) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(json, MyDTO.class);
    }
}
