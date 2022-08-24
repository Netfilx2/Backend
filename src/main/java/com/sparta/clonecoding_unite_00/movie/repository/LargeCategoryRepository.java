package com.sparta.clonecoding_unite_00.movie.repository;



import com.sparta.clonecoding_unite_00.movie.domain.LargeCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LargeCategoryRepository extends JpaRepository<LargeCategory, Long> {
    public LargeCategory findByLargeCategoryName(String largeCategoryName);
}
