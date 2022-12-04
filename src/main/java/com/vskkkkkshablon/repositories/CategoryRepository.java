package com.vskkkkkshablon.repositories;


import com.vskkkkkshablon.entities.Categories;
import com.vskkkkkshablon.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
