package org.fullstack.verry.repository.board.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.BoardEntity;
import org.fullstack.verry.domain.QBoardEntity;
import org.fullstack.verry.domain.QBoardReplyEntity;
import org.fullstack.verry.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(BoardEntity.class);
    }

    @Override
    public Page<BoardEntity> search(Pageable pageable, String[] types, String search_keyword) {

        QBoardEntity qBoard = QBoardEntity.boardEntity;
        JPQLQuery<BoardEntity> query = from(qBoard);

        if ((types != null) && (types.length > 0) && (search_keyword != null && (search_keyword.length() > 0))) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            //  type: t - 제목, c - 내용, m - 사용자ID
            for (String type : types) {
                switch (type) {
                    case "t" :
                        booleanBuilder.or(qBoard.title.like("%" + search_keyword + "%"));
                        break;
                    case "c" :
                        booleanBuilder.or(qBoard.content.like("%" + search_keyword + "%"));
                        break;
                    case "m" :
                        booleanBuilder.or(qBoard.memberId.like("%" + search_keyword + "%"));
                        break;
                }
                query.where(booleanBuilder);
            }
        }
        query.where(qBoard.idx.gt(0));

        this.getQuerydsl().applyPagination(pageable, query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount();

        return new PageImpl<>(boards, pageable, total);
    }

}
