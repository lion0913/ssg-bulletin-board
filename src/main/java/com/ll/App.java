package com.ll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BoardController boardController = new BoardController(br);

        System.out.println("[bulletin-board]");


        outer:
        while(true) {
            System.out.print("명령> ");
            String cmd = br.readLine().trim();

            Request rq = new Request(cmd);
//            System.out.println(rq.toString());
            switch(rq.path) {
                case "등록":
                    boardController.write(rq);
                    break;
                case "삭제":
                    boardController.delete(rq);
                    break;
                case "목록":
                    boardController.list(rq);
                    break;
                case "수정":
                    boardController.modify(rq);
                    break;
                case "종료":
                    break outer;
            }
        }
    }
}
