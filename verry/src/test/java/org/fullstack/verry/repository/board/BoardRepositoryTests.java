package org.fullstack.verry.repository.board;

import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.BoardEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testGetNow() {
        String now = boardRepository.getNow();
        log.info("===============");
        log.info("now : {}", now);
        log.info("===============");
    }

    @Test
    public void testRegist() {
        BoardEntity bbs = BoardEntity.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .memberId("test")
                .boardType("b")
                .build();
        BoardEntity bbsResult = boardRepository.save(bbs);
        log.info("result : {}" + bbsResult);
    }
}
