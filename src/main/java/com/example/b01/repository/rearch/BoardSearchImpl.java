package com.example.b01.repository.rearch;

import com.example.b01.domain.Board;
import com.example.b01.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = this.from(board);

        BooleanBuilder builderBuilder = new BooleanBuilder();

        builderBuilder.or(board.title.contains("11"));
        builderBuilder.or(board.content.contains("11"));

        query.where(new Predicate[]{builderBuilder});
        query.where(new Predicate[]{board.bno.gt(0L)});

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();
        return new PageImpl(list,pageable, count);
    }
    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = this.from(board);
        if(types != null && types.length > 0 && keyword != null) {
            BooleanBuilder builderBuilder = new BooleanBuilder();
            for(String type : types) {
                switch (type) {
                    case "t":
                        builderBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        builderBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        builderBuilder.or(board.writer.contains(keyword));
                        break;
                }

            }
            query.where(builderBuilder);

        }
        query.where(board.bno.gt(0L));

        //paging 처리
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list,pageable, count);
    }
}