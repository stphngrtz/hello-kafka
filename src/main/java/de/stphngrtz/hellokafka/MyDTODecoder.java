package de.stphngrtz.hellokafka;

import kafka.serializer.Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MyDTODecoder implements Decoder<MyDTO> {

    @Override
    public MyDTO fromBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            try {
                ois = new ObjectInputStream(bais);
                Object o = ois.readObject();
                return (MyDTO) o;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
