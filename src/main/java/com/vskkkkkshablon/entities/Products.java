package com.vskkkkkshablon.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="t_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", length = 200)
  private String name;

  @Column(name="price")
  private int price;

  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "amount")
  private int amount;

}
