package com.vskkkkkshablon.repositories;

import com.vskkkkkshablon.entities.Products;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Products, Long> {
  List<Products> findAllByAmountGreaterThanOrderByPriceDesc(int amount);

  Products findByIdAndAmountGreaterThan(Long id, int amount);

}
