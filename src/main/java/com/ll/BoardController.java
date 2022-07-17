package com.ll;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class BoardController {
    private BufferedReader br;

    private BoardService boardService;

    BoardController(BufferedReader br) {
        this.br = br;
        this.boardService = new BoardService();
    }
    void write(Request rq) throws IOException {
        System.out.print("작성자명:");
        String author = br.readLine().trim();
        System.out.print("내용: ");
        String content = br.readLine().trim();

        Board board = boardService.write(author, content);
        System.out.printf("%d번 게시글이 등록되었습니다.\n", board.id);
    }

    void delete(Request rq) {
        int id = rq.getIntParam("id", 0);
        boolean result = boardService.delete(id);
//        boolean result = boardRepository.delete(id);
        if(result) {
            System.out.println("정상적으로 삭제됐습니다.");
        } else {
            System.out.println("삭제할 게시글 번호가 존재하지 않습니다.");
        }
    }

    void list(Request rq) {
        List<Board> boardList = boardService.findAll();
        System.out.println("=============목록================");
        for(Board board : boardList) {
            System.out.println("[ "+board.id+" ]");
            System.out.println("작성자 : "+board.author);
            System.out.println("내용 : "+board.content);
            System.out.println("-------------------------------");
        }
        System.out.println("");
    }

    void modify(Request rq) throws IOException {
        int id = rq.getIntParam("id", -1);
        if(id == -1) {
            System.out.printf("수정할 글 번호를 입력하세요 :");
            id = Integer.parseInt(br.readLine());
        }
        Board board = boardService.findById(id);
        System.out.println("수정할 내용을 입력해주세요 :");
        String content = br.readLine().trim();

        board.content = content;
        boardService.modify(id, content);
        System.out.println("수정완료됐습니다");

    }
}
