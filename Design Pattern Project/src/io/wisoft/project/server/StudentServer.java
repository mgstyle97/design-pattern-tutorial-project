package io.wisoft.project.server;

import io.wisoft.project.dao.dto.relation.lecture.LectureDTO;
import io.wisoft.project.dao.dto.relation.register.RegisterDTO;
import io.wisoft.project.dao.dto.user.student.StudentDTO;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentServer extends LoggedInServer{
    @Override
    protected void additionalOption() {

        System.out.println("4. 수강신청 목록 조회");
        System.out.println("5. 수강신청");
        System.out.println("6. 수강신청 취소");

    }

    @Override
    protected void response(String command) {
        switch (command) {
            case "4":
                lectureCheck();
                break;
            case "5":
                lectureRegister();
                break;
            case "6":
                lectureCancel();
                break;
            default:
                super.response(command);
        }
    }

    private void lectureCheck() {

        StudentDTO user = (StudentDTO) getUser();
        AtomicInteger userCredit = new AtomicInteger();
        List<RegisterDTO> lectureList = registerDAO.getLectureList(user.getId());

        System.out.println(getUser().getName() + "의 수강 신청 목록");
        System.out.println("===========================");

        lectureList.forEach((registerDTO)-> {
                    LectureDTO lecture = (LectureDTO) lectureDAO.select(
                            registerDTO.getProfessorId(), registerDTO.getCourseId()
                    );
                    userCredit.addAndGet(lecture.getCredit());
                    System.out.println(lecture);
                    System.out.println();
                });
        user.setCredit(userCredit.get());

        System.out.println(user.getCredit() + "학점의 강의들을 수강 신청했습니다.");
        System.out.println();

    }

    private void lectureRegister() {

        String userId = getUser().getId();
        String professorId, courseId;
        LectureDTO lecture;
        RegisterDTO registerDTO;
        HashMap<String, LectureDTO> lectureHashMap;

        System.out.println("id\t\tname\n");
        lectureDAO.selectAll();

        System.out.println("신청할 강의의 담당 교수님 id를 입력해주세요");
        professorId = commandInput();

        lectureHashMap = lectureDAO.getLectureList(professorId);

        if(lectureHashMap.size() == 0) {
            System.out.println("존재하지 않는 교수님의 id입니다.");
            System.out.println();
            return;
        }

        System.out.println("신청할 강의의 과목 id를 입력해주세요");
        courseId = commandInput();

        if(!lectureHashMap.containsKey(courseId)) {
            System.out.println("존재하지 않는 강의입니다.");
            System.out.println();
            return;
        }

        lecture = lectureHashMap.get(courseId);
        registerDTO = new RegisterDTO(userId, lecture.getId(), lecture.getCourseId());

        registerDAO.insert(registerDTO);
        System.out.println();

    }

    private void lectureCancel() {

        String userId = getUser().getId();
        String professorId, courseId;
        RegisterDTO register;

        lectureCheck();

        System.out.println("삭제할 강의의 담당 교수님 id를 입력해주세요.");
        professorId = commandInput();

        System.out.println("삭제할 강의의 과목 id를 입력해주세요.");
        courseId = commandInput();

        if(!registerDAO.isContainData(userId, professorId, courseId)) {
            System.out.println("존재하지 않는 강의입니다.");
            System.out.println();
            return;
        }

        register = new RegisterDTO(userId, professorId, courseId);

        registerDAO.delete(register);

    }
}
