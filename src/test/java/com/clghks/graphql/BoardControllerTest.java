package com.clghks.graphql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureGraphQlTester
class BoardControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @DisplayName("1. 게시글 1개 가져오기")
    @Test
    void test_1() {
        this.graphQlTester.documentName("board")
                .variable("id", 1L)
                .execute()
                .path("board.title")
                .entity(String.class)
                .isEqualTo("제목1");
    }

    @DisplayName("2. 개시글 목록 가져오기")
    @Test
    void test_2() {
        this.graphQlTester.documentName("boardList")
                .execute()
                .path("boardList[*].title")
                .entityList(String.class)
                .contains("제목1", "제목2", "제목3");
    }

    @DisplayName("3. 게시글 생성")
    @Test
    void test_3() {
        Map<String, Object> dto = new HashMap<>();
        dto.put("title", "제목5");
        dto.put("content", "내용5");

        this.graphQlTester.documentName("create")
                .variable("input", dto)
                .execute()
                .path("create.title")
                .entity(String.class)
                .isEqualTo("제목5");
    }

    @DisplayName("4. 게시글 수정")
    @Test
    void test_4() {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", 4);
        dto.put("title", "제목4가 6으로");
        dto.put("content", "내용6");

        this.graphQlTester.documentName("update")
                .variable("input", dto)
                .execute()
                .path("update.title")
                .entity(String.class)
                .isEqualTo("제목4가 6으로");
    }

    @DisplayName("5. 게시글 삭제")
    @Test
    void test_5() {
        this.graphQlTester.documentName("delete")
                .variable("id", 4)
                .executeAndVerify();

        this.graphQlTester.documentName("board")
                .variable("id", 4)
                .execute()
                .errors();
    }

    @DisplayName("6. Dynamic Field Resolver 테스트")
    @Test
    void test_6() {
        this.graphQlTester.documentName("board")
                .variable("id", 1)
                .execute()
                .path("board.author")
                .entity(String.class)
                .isEqualTo("author return");
    }
}