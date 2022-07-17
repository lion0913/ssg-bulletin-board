package com.ll;

import java.util.List;

public class BoardService {

    private BoardRepository boardRepository;
    BoardService() {
        this.boardRepository = new BoardRepository();
    }
    public Board write(String author, String content) {
        return boardRepository.write(author, content);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(int id) {
        return boardRepository.findById(id);
    }

    public void modify(int id, String content) {
        boardRepository.modify(id, content);
        return;
    }

    public boolean delete(int id) {
        return boardRepository.delete(id);
    }
}
