package com.ll;

import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
    private List<Board> boardList;
    private int lastId;

    BoardRepository() {
        boardList = new ArrayList<>();
        lastId = 0;
    }

    public Board write(String author, String content) {
        Board board = new Board(++lastId, author, content);
        boardList.add(board);

        return board;
    }

    public boolean delete(int id) {
        Board board = findById(id);
        if(board == null) {
            return false;
        }
        else {
            boardList.remove(board);
            return true;
        }
    }

    public void modify(int id, String content) {
        Board board = findById(id);
        board.content = content;
    }

    public Board findById(int id) {
        for(Board board : boardList) {
            if(board.id == id) {
                return board;
            }
        }
        return null;
    }

    public List<Board> findAll() {
        return boardList;
    }
}
