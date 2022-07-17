package com.ll;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardTable {
    private String baseDir;

    public BoardTable(String baseDir) {
        this.baseDir = baseDir;
    }

    public void save(Board board) {
        Util.mkdir("%s/board".formatted(baseDir));
        String body = board.toJson();

        Util.saveFile("%s/board/list.json".formatted(baseDir), body, true);
    }

    public void save(List<Board> boardList) {
        Util.mkdir("%s/board".formatted(baseDir));
        String body = "";
        for(Board board : boardList) {
            body += board.toJson();
        }

        Util.saveFile("%s/board/list.json".formatted(baseDir), body, false);
    }

    public Board save(String author, String content) {
        int id = getLastId()+1;
        Board board = new Board(id, content, author);
        save(board);
        saveLastId(id);

        return board;
    }

    public int getLastId() {
        String lastId = Util.readFromFile("%s/board/lastId.txt".formatted(baseDir), "");
        if(lastId == "") {
            return 0;
        }
        return Integer.parseInt(lastId);
    }

    public void saveLastId(int id) {
        Util.saveFile("%s/board/lastId.txt".formatted(baseDir), Integer.toString(id), false);
    }

    public List<Board> findAll(){
        List<Board> boardList = new ArrayList<>();
        List<Map<String, Object>> mapList = Util.json.jsonMapFromFile("prod_data/board/list.json");
        if(mapList == null) {
            return null;
        }

        for(int i = 0; i < mapList.size()/3; i++) {
            Board board = new Board( (int)mapList.get(3*i+0).get("id"), (String)mapList.get(3*i+1).get("author"), (String) mapList.get(3*i+2).get("content"));
            boardList.add(board);
        }
        return boardList;
    }

    public Board findById(int id) {
        String json = Util.readFromFile("prod_data/board/list.json", "");

        if (json.isEmpty()) {
            return null;
        }

        final String[] jsonBits = json
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .split(",");

        final List<Object> bits = Stream.of(jsonBits)
                .map(String::trim)
                .flatMap(bit -> Arrays.stream(bit.split(":")))
                .map(String::trim)
                .map(s -> s.startsWith("\"") ? s.substring(1, s.length() - 1) : Integer.parseInt(s))
                .collect(Collectors.toList());


        for(int i = 0; i < bits.size(); i++) {
            if(bits.get(i).equals("id") && bits.get(i+1).equals(id)) {
                Board board = new Board((int) bits.get(i+1), (String)bits.get(i+3), (String)bits.get(i+5));
                return board;
            }
        }

        return null;
    }

}
