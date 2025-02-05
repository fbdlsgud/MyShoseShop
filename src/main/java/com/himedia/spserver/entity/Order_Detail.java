package com.himedia.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
public class Order_Detail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int odseq;

    @ManyToOne
    @JoinColumn(name = "orders_oseq")
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_pseq")
    Product product;

    @Column( nullable = false )
    private int quantity;

    @Column( nullable = false )
    @ColumnDefault("1")
    private int result;
}
