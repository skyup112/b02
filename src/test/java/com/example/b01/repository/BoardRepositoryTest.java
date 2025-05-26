package com.example.b01.repository;

import com.example.b01.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
@Log4j2
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("writer" + i)
                    .build();
            Board result = boardRepository.save(board);
            log.info("Bno"+result.getBno());
        });
    }
    @Test
    public void testSelect() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(board);
    }
    @Test
    public void testUpdate() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.Change("update..title 100","update..content 100");
        boardRepository.save(board);
        log.info(board);

    }
    @Test
    public void testDelete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }
    @Test
    public void testPaging() {
//        1 page oder by bno desc
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageRequest);
        log.info("total count"+result.getTotalElements());
        log.info("total page"+result.getTotalPages());
        log.info("page number"+result.getNumber());
        log.info("page size"+result.getSize());

        List<Board> boards = result.getContent();
        boards.forEach(board -> log.info(board));
    }
    @Test
    public void testSearch1() {
        PageRequest pageable = PageRequest.of(1, 10,Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }
    @Test
    public void testSearchAll(){
        String[] types={"t","c","w"};
        String keyword="2";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);
        log.info("total page:"+result.getTotalPages());
        log.info("total size"+result.getSize());
        log.info("page number"+result.getNumber());
        log.info(result.hasPrevious()+":"+result.hasNext());
        result.getContent().forEach(board -> log.info(board));
    }

}
