package com.vskkkkkshablon.entities;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Categories> categories;
}
