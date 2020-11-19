package io.wisoft.project.server;

import io.wisoft.project.dao.dto.user.UserDTO;
import io.wisoft.project.dao.dto.course.CourseDAO;
import io.wisoft.project.factory.UserDTOFactory;
import io.wisoft.project.dao.dto.relation.lecture.LectureDAO;
import io.wisoft.project.dao.dto.notice.NoticeDAO;
import io.wisoft.project.factory.UserDAOFactory;
import io.wisoft.project.singleton.UserDAOFactorySingleton;
import io.wisoft.project.singleton.ScannerSingleton;
import io.wisoft.project.dao.dto.notice.NoticeDTO;
import io.wisoft.project.dao.dto.relation.register.RegisterDAO;
import io.wisoft.project.singleton.UserDTOFactorySingleton;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Server {

    public final Scanner scanner = ScannerSingleton.getInstance();
    public final NoticeDAO noticeDAO = new NoticeDAO();
    public final CourseDAO courseDAO = new CourseDAO();
    public final LectureDAO lectureDAO = new LectureDAO();
    public final RegisterDAO registerDAO = new RegisterDAO();
    public final List<String> departmentList = Arrays.asList(
            "정보통신공학과", "전자제어공학과", "컴퓨터공학과", "도시공학과"
    );
    private final UserDAOFactory userDaoFactory = UserDAOFactorySingleton.getInstance();
    private final UserDTOFactory userDTOFactory = UserDTOFactorySingleton.getInstance();
    private UserDTO user = null;

    public String commandInput() {

        String command = null;

        System.out.print(">> ");
        command = scanner.nextLine();

        return command;
    }

    public UserDTO getUser() {
        return this.user;
    }

    public UserDAOFactory getDaoFactory() { return this.userDaoFactory; }

    public UserDTOFactory getUserDTOFactory() { return this.userDTOFactory; }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    protected void notice() {
        String notice_no;

        System.out.println("--------------------------------------------------------------");
        noticeDAO.selectAll();


        try {
            System.out.println("\n확인할 공지사항의 번호를 입력해주세요");
            notice_no = commandInput();

            Integer.parseInt(notice_no);
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력했습니다.");
            return;
        }

        NoticeDTO notice = (NoticeDTO) noticeDAO.select(notice_no);

        if (notice == null) {
            return;
        }

        System.out.println(notice);
        System.out.println();
    }

    public abstract UserDTO showPage(UserDTO user);

    protected abstract void response(String command);
}
