package com.ll;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void BoardTableSaveTest() {
        BoardTable boardTable = new BoardTable("board");
        boardTable.save("박다정","배고파");
        boardTable.save("나의 죽음을 적들에게 알리지 마라.", "이순신");
        assertTrue(new File("board/board/list.json").exists());
    }

    @Test
    void fileTest() {
        BoardTable boardTable = new BoardTable("board");
        Board board = new Board(3,"테스트", "박다정");
        String body = board.toJson();
        Util.saveFile("board/board/list.json", body);

    }

    @Test
    void findJsonfileTest() {
        BoardTable boardTable = new BoardTable("board");
        boardTable.findAll();

    }
}
