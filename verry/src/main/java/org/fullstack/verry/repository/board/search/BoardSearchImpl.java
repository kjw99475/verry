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
        log.info("=========================================");
        log.info("BoardSearchImpl >> search START");

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
        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount();

        log.info("boards : {}", boards);
        log.info("total : {}", total);


        log.info("BoardSearchImpl >> search END");
        log.info("=========================================");
        return new PageImpl<>(boards, pageable, total);
    }

//    @Override
//    public Page<BoardDTO> searchWithReplyCnt(Pageable pageable, String[] types, String search_keyword) {
//        log.info("=====================================");
//        log.info("BoardSearchImpl >> searchWithReplyCnt START");
//
//        QBoardEntity qBoard = QBoardEntity.boardEntity;
//        QBoardReplyEntity replyQ = QBoardReplyEntity.boardReplyEntity;
//
//        JPQLQuery<BoardEntity> query = from(qBoard);
//        query.leftJoin(replyQ).on(replyQ.board.eq(qBoard));
//        query.groupBy(qBoard);
//
//        if ( (types != null && types.length > 0)
//                && (search_keyword != null && search_keyword.length() > 0)) {
//            BooleanBuilder booleanBuilder = new BooleanBuilder();
//            //type:t-제목, c:내용, w:사용자아이디
//            for ( String type : types){
//                switch (type){
//                    case "t":
//                        booleanBuilder.or(qBoard.title.like("%"+search_keyword+"%"));
//                        break;
//                    case "c":
//                        booleanBuilder.or(qBoard.content.like("%"+search_keyword+"%"));
//                        break;
//                    case "u":
//                        booleanBuilder.or(qBoard.memberId.like("%"+search_keyword+"%"));
//                        break;
//                }
//            }
//            query.where(booleanBuilder);
//        }
//        query.where(qBoard.idx.gt(0));
//
//        JPQLQuery<BoardDTO> dtoQuery = query.select(
//                Projections.bean(BoardDTO.class,
//                        qBoard.idx,
//                        qBoard.memberId,
//                        qBoard.title,
//                        qBoard.content,
//                        qBoard.regDate,
//                        qBoard.modifyDate,
//                        replyQ.count().as("reply_cnt")
//                ));
//
//        this.getQuerydsl().applyPagination(pageable, dtoQuery);
//        List<BoardDTO> dtoList = dtoQuery.fetch();
//        int count = (int)dtoQuery.fetchCount();
//
//        log.info("=====================================");
//        log.info("BoardSearchImpl >> searchWithReplyCnt START");
//        log.info("dtoQuery : {}", dtoQuery);
//        log.info("dtoList : {}", dtoList);
//        log.info("count : {}", count);
//        log.info("BoardSearchImpl >> searchWithReplyCnt END");
//        log.info("=====================================");
//        return new PageImpl<>(dtoList, pageable, count);
//    }
}
