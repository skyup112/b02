package com.example.b01.repository;

import com.example.b01.domain.Board;
import com.example.b01.repository.rearch.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

//    @Query("select  b from Board b where b.title like concat('%', :keyword,'%') order by b.bno desc")
//    Page<Board> findByKeyword(String keyword, Pageable pageable);

}
