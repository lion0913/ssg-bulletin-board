package com.ll;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardTable {
    private String baseDir;

    public BoardTable(String baseDir) {
        this.baseDir = baseDir;
    }

    public void save(Board board) {
        Util.mkdir("%s/board".formatted(baseDir));
        String body = board.toJson();

        Util.saveToFile("%s/board/list.json".formatted(baseDir), body);
    }

    public void save(String author, String content) {
        int id = getLastId()+1;
        Board board = new Board(id, content, author);
        save(board);
        saveLastId(id);
    }

    public int getLastId() {
        String lastId = Util.readFromFile("%s/board/lastId.txt".formatted(baseDir), "");
        if(lastId == "") {
            return 0;
        }
        return Integer.parseInt(lastId);
    }

    public void saveLastId(int id) {
        Util.saveToFile("%s/board/lastId.txt".formatted(baseDir), Integer.toString(id));
    }

    public List<Board> findAll(){
        List<Board> boardList = new ArrayList<>();
        List<Map<String, Object>> mapList = Util.json.jsonMapFromFile("board/board/list.json");
        if(mapList == null) {
            return null;
        }

        for(int i = 0; i < mapList.size()/3; i++) {
            Board board = new Board( (int)mapList.get(3*i+0).get("id"), (String)mapList.get(3*i+1).get("author"), (String) mapList.get(3*i+2).get("content"));
            boardList.add(board);
            System.out.println(board);
        }
        return boardList;
    }

}
