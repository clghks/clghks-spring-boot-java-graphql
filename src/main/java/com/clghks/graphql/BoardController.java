package com.clghks.graphql;

import com.clghks.graphql.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @QueryMapping
    public BoardDto board(@Argument Long id) {
        return boardService.getBoard(id);
    }

    @QueryMapping
    public List<BoardDto> boardList() {
        return boardService.getBoardList();
    }

    @MutationMapping
    public BoardDto create(@Argument BoardDto boardInput) {
        return boardService.create(boardInput);
    }

    @MutationMapping
    public BoardDto update(@Argument BoardDto boardInput) {
        return boardService.update(boardInput);
    }

    @MutationMapping
    public Boolean delete(@Argument Long id) {
        boardService.delete(id);
        return true;
    }

    @SchemaMapping(typeName = "Board")
    public String author() {
        return "author return";
    }

}
