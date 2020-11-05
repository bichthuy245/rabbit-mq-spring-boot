package rabbitmqtraining.consumer.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id",
                  scope = ShoppingOnline.class)
@Getter
@Setter
public class ShoppingOnline {

    private String id;
    private String name;
    private String groupName;
    private double price;

    @Override
    public String toString () {
        return "Shopping Online [Name=" + name + ", id=" + id + ", groupName=" + groupName + ", price=" + price + "]";
    }
}
