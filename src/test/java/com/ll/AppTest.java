package com.ll;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    void fileSaveTest() {
        Util.mkdir("board");
        Util.saveToFile("board/test.txt", "테스트");

        String content = Util.readFromFile("board/test.txt","");
        assertEquals("테스트", content);
    }

    @Test
    void boardTableTest() {
        BoardTable boardTable = new BoardTable("board");
        Board board = new Board(1, "박다정", "ㅋㅋ 아 배고파");
        Util.saveFile("board/board/list.json", board.toJson(), true);

        board = new Board(2, "김수진", "ㅋㅋ나도");
        Util.saveFile("board/board/list.json", board.toJson(), true);

        assertTrue(new File("board/board/list.json").exists());

    }
}
