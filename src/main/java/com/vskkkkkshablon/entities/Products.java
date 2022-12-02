package com.vskkkkkshablon.entities;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="t_products")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Products products = (Products) o;
    return id != null && Objects.equals(id, products.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
