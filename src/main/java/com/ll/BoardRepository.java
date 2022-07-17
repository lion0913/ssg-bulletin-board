package com.ll;

import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
    private List<Board> boardList;
    private int lastId;
    private BoardTable boardTable;

    BoardRepository() {
        boardList = new ArrayList<>();
        lastId = 0;
        this.boardTable = new BoardTable("prod_data");
    }

    public Board write(String author, String content) {
        Board board = boardTable.save(author, content);

        return board;
    }

    public void write(List<Board> list) {
        boardTable.save(list);
    }

    public boolean delete(int id) {
        List<Board> boardList = findAll();
        for(Board board : boardList) {
            if(board.id == id) {
                boardList.remove(board);
                write(boardList);
                return true;
            }
        }
        return false;
    }

    public void modify(int id, String content) {
        List<Board> boardList = findAll();
        for(Board board : boardList) {
            if(board.id == id) {
                board.content = content;
            }
        }
        write(boardList);
    }

    public Board findById(int id) {
        return boardTable.findById(id);
    }

    public List<Board> findAll() {
        return boardTable.findAll();
    }
}
