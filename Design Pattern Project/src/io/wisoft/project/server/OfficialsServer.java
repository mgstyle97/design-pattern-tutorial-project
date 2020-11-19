package io.wisoft.project.server;

import io.wisoft.project.dao.dto.notice.NoticeDTO;
import io.wisoft.project.dao.dto.user.officials.OfficialsDTO;

public class OfficialsServer extends LoggedInServer {

    @Override
    protected void additionalOption() {

        System.out.println("4. 공지사항 등록");
        System.out.println("5. 공지사항 수정");

    }

    @Override
    protected void response(String command) {
        switch (command) {
            case "4":
                noticeRegister();
                break;
            case "5":
                noticeModify();
                break;
            default:
                super.response(command);
        }
    }

    private void noticeRegister() {
        String title;
        String content;
        OfficialsDTO user = (OfficialsDTO) getUser();
        NoticeDTO notice;

        noticeDAO.selectAll();

        System.out.println("등록할 공지사항의 제목을 입력해주세요");
        title = commandInput();

        if(noticeDAO.isContainData(title)) {
            System.out.println("해당 제목의 공지사항이 이미 존재합니다.");
            System.out.println();
            return;
        }

        System.out.println("등록할 공지사항의 내용을 입력해주세요");
        content = commandInput();

        notice = new NoticeDTO(null, title, content, getUser().getId());

        noticeDAO.insert(notice);
        user.setNumOfNotice(user.getNumOfNotice() + 1);
    }

    private void noticeModify() {
        String notice_no;
        String content;
        NoticeDTO notice;


        System.out.println("수정할 공지사항의 번호를 입력해주세요");
        notice_no = commandInput();

        System.out.println("공지사항의 수정할 내용을 입력해주세요");
        content = commandInput();

        notice = new NoticeDTO(notice_no, null, content, getUser().getId());

        noticeDAO.update(notice_no, content, notice);
    }

}
