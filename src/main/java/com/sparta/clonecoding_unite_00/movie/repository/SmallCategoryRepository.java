package com.sparta.clonecoding_unite_00.movie.repository;



import com.sparta.clonecoding_unite_00.movie.domain.SmallCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SmallCategoryRepository extends JpaRepository<SmallCategory, Long> {
    public SmallCategory findBySmallCategoryName(String SmallCategory);
    public List<SmallCategory> findAllBySmallCategoryName(String SmallCategory);


}
