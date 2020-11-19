package io.wisoft.project.dao.dto.notice;

import io.wisoft.project.dao.dto.DTO;

public class NoticeDTO extends DTO {

    private String title, content, writer;

    public NoticeDTO(String id, String title, String content, String writer) {
        super(id);
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public String getTitle() { return this.title; }

    public String getContent() { return this.content; }

    public String getWriter() { return this.writer; }

    public void setTitle(String title) { this.title = title; }

    public void setContent(String content) { this.content = content; }

    public void setWriter(String writer) { this.writer = writer; }

    @Override
    public String toString() {
        String str = "제목: " + this.title;
        str += "\n내용: " + this.content;
        str += "\n작성자: " + this.writer;

        return str;
    }

}
