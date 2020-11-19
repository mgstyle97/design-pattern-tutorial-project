package io.wisoft.project.server;

import io.wisoft.project.dao.dto.course.CourseDTO;
import io.wisoft.project.dao.dto.relation.lecture.LectureDTO;
import io.wisoft.project.dao.dto.user.professor.ProfessorDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProfessorServer extends LoggedInServer {

    private final List<String> dayList = Arrays.asList(
            "월요일", "화요일", "수요일", "목요일", "금요일"
    );

    @Override
    protected void additionalOption() {

        System.out.println("4. 강의 목록 조회");
        System.out.println("5. 강의 등록");
        System.out.println("6. 강의 삭제");

    }

    private void lectureListLookup() {
        ProfessorDTO user = (ProfessorDTO) getUser();
        HashMap<String, LectureDTO> lectureList = lectureDAO.getLectureList(user.getId());
        Set<String> courseIds = lectureList.keySet();
        user.setNumOfLecture(courseIds.size());

        System.out.println(getUser().getName() + "\n");
        courseIds.forEach((courseId)-> {
                    CourseDTO course = (CourseDTO) courseDAO.select(courseId);
                    LectureDTO lecture = lectureList.get(courseId);

                    System.out.print("\t" + course.getId());
                    System.out.print("\t" + course.getName());
                    System.out.print("\t" + lecture.getDayOfWeek());
                    System.out.println("\t" + lecture.getTime() + "\n");

                });

        System.out.println(user.getNumOfLecture() + "개의 강의를 등록하셨습니다.");
        System.out.println();

    }

    private void lectureRegister() {

        String courseId;
        String dayOfWeek;
        String time;
        CourseDTO course;
        LectureDTO lecture;

        System.out.println("과목 리스트");
        courseDAO.selectAll();

        System.out.println("등록할 과목의 id를 입력하세요.");
        courseId = commandInput();

        course = (CourseDTO) courseDAO.select(courseId);

        if(course == null) {
            System.out.println("존재하지 않는 과목입니다.");
            System.out.println();
            return;
        }

        System.out.println("강의 요일을 입력해주세요.");
        dayOfWeek = commandInput();

        if(!dayList.contains(dayOfWeek)) {
            System.out.println("잘못된 요일을 입력했습니다.");
            System.out.println();
            return;
        }

        System.out.println("강의 시간을 입력해주세요(AM, PM).");
        time = commandInput();

        lecture = new LectureDTO(getUser().getId(), courseId, dayOfWeek, LectureDTO.Time.valueOf(time), course.getCredit());

        lectureDAO.insert(lecture);

    }

    private void lectureDelete() {

        String courseId;
        String userId = getUser().getId();
        LectureDTO deleteLecture;
        HashMap<String, LectureDTO> lectureList = lectureDAO.getLectureList(userId);

        lectureListLookup();

        System.out.println("삭제할 과목의 id를 입력해주세요.");
        courseId = commandInput();

        if(!lectureList.containsKey(courseId)) {
            System.out.println("존재하지 않는 과목의 id입니다.");
            System.out.println();
            return;
        }

        deleteLecture = lectureList.get(courseId);

        lectureDAO.delete(deleteLecture);

    }

    @Override
    protected void response(String command) {
        switch (command) {
            case "4":
                lectureListLookup();
                break;
            case "5":
                lectureRegister();
                break;
            case "6":
                lectureDelete();
                break;
            default:
                super.response(command);
        }
    }

}
