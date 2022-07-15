package com.ll;

public class Board {
    int id;
    String author;
    String content;

    public Board(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String toJson() {
        return """
                {
                    "id": %d,
                    "author": "%s",
                    "content": "%s"
                },
                """.stripIndent().formatted(id, author, content);
    }
}
