package com.clghks.graphql;

import com.clghks.graphql.dto.BoardDto;
import com.clghks.graphql.entity.BoardEntity;
import com.clghks.graphql.entity.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDto getBoard(Long id) {
        var boardEntity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return new ModelMapper().map(boardEntity, BoardDto.class);
    }

    public List<BoardDto> getBoardList() {
        List<BoardDto> boardList = new ArrayList<>();
        boardRepository.findAll().forEach(entity -> {
            boardList.add(new ModelMapper().map(entity, BoardDto.class));
        });

        return boardList;
    }

    public BoardDto create(BoardDto boardDto) {
        var entity = BoardEntity.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .build();

        var savedEntity = boardRepository.save(entity);
        return new ModelMapper().map(savedEntity, BoardDto.class);
    }

    public BoardDto update(BoardDto boardDto) {
        var entity = boardRepository.findById(boardDto.getId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        entity.setTitle(boardDto.getTitle());
        entity.setContent(boardDto.getContent());

        var savedEntity = boardRepository.save(entity);
        return new ModelMapper().map(savedEntity, BoardDto.class);
    }

    public void delete(Long id) {
        var entity = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(entity);
    }
}
