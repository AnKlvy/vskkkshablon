package com.vskkkkkshablon.repositories;

import com.vskkkkkshablon.entities.Categories;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
