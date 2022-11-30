package com.vskkkkkshablon.repositories;


import com.vskkkkkshablon.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Products, Long> {
  List<Products> findAllByAmountGreaterThanOrderByPriceDesc(int amount);
  Products findByIdAndAmountGreaterThan(Long id, int amount);
}
