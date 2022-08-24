package com.sparta.clonecoding_unite_00.favorite.repository;

import com.sparta.clonecoding_unite_00.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

    Boolean existsByVideo_IdAndMember_Id(Long videoId,Long memberId);

    void deleteByVideo_IdAndMember_Id(Long videoId,Long memberId);

    List<Favorite> findAllByMember_Id(Long memberId);


}
