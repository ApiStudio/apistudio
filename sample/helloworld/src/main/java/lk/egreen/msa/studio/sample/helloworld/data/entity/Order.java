package lk.egreen.msa.studio.sample.helloworld.data.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Index;
import java.util.UUID;

/**
 * Created by dewmal on 8/4/16.
 */
@Table(name = "Orders",
        indexes = {
                @Index(name = "entity_customer_idx", columnList = "customer"),
                @Index(name = "entity_name_idx", columnList = "name")
        })
public class Order {
    @Id
    private java.util.UUID code;

    @Column(name = "customer")
    private String customer;
    private String name;
    private double amount;


    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
