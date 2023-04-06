package com.dailycodebuffer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;



/*productId for which particular product this order belongs to
 * How many quantity's of this particular product is belongs this order*/

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "PRODUCT_ID")
    private long productId;

    @Column(name = "QUANTITY")
    private long quantity;

    @Column(name = "ORDER_DATE")
    private Instant orderDate;

    @Column(name = "STATUS")
    private String orderStatus;

    @Column(name = "TOTAL_AMOUNT")
    private long amount;

}
