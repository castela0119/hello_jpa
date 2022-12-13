package org.example.hellojpa;

import javax.persistence.*;

@Entity
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
