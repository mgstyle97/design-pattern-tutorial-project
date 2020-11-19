package io.wisoft.project.server;

import io.wisoft.project.common.Relation;
import io.wisoft.project.common.State;
import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.user.UserDTO;
import io.wisoft.project.factory.StateFactory;

public class UnLoggedInServer extends Server {


    @Override
    public UserDTO showPage(UserDTO user) {

        if(user.getState() != State.UNLOGGEDIN) {
            System.out.println("잘못된 접근입니다.");
            return user;
        }

        setUser(user);

        String command = null;

        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 공지사항 확인");
        System.out.println("4. 종료");

        command = commandInput();

        response(command);

        return getUser();
    }

    private void signUp() {
        Relation relation = null;
        String id = null;
        String password = null;
        String name = null;
        String dept = null;

        DAO memberDAO = null;
        UserDTO newMember = null;

        System.out.println("1. 학생 2. 교수 3. 조교");

        try {

            relation = Relation.values()[Integer.parseInt(commandInput())-1];

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("잘못된 입력입니다.");
            System.out.println();
            return;
        }

        memberDAO = getDaoFactory().create(relation);

        System.out.println("ID를 입력하시오.");
        id = commandInput();

        if (memberDAO.isContainData(id)) {
            System.out.println("이미 존재하는 ID 입니다.");
            System.out.println();
            return;
        } else if(id.length() == 0) {
            System.out.println("ID를 입력하지 않았습니다.");
            System.out.println();
            return;
        }

        System.out.println("PASSWORD를 입력하시오.");
        password = commandInput();

        if(password.length() == 0) {
            System.out.println("PASSWORD를 입력하지 않았습니다.");
            System.out.println();
            return;
        }

        System.out.println("이름을 입력하시오.");
        name = commandInput();

        if(name.length() == 0) {
            System.out.println("이름을 입력하지 않았습니다.");
            System.out.println();
            return;
        }

        System.out.println("소속 학과를 입력하시오.");
        dept = commandInput();

        if(!departmentList.contains(dept)) {
            System.out.println("존재하지 않는 학과입니다.");
            System.out.println();
            return;
        }

        newMember = new UserDTO(id, password, name, dept);

        memberDAO.insert(newMember);
    }

    private void login() {
        Relation relation = null;
        String id = null;
        String password = null;

        DAO dao = null;

        System.out.println("1. 학생 2. 교수 3. 조교");

        try {

            relation = Relation.values()[Integer.parseInt(commandInput())-1];

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("잘못된 입력입니다.");
            System.out.println();
            return;
        }

        dao = getDaoFactory().create(relation);

        System.out.println("ID를 입력하시오.");
        id = commandInput();

        if (!dao.isContainData(id)) {
            System.out.println("존재하지 않는 사용자입니다.");
            System.out.println("다시 입력해주세요!!");
            System.out.println();
            return;
        }

        setUser(getUserDTOFactory().create(relation, (UserDTO) dao.select(id)));

        System.out.println("PASSWORD를 입력하시오.");
        password = commandInput();

        if(!password.equals(getUser().getPassword())) {
            System.out.println("PASSWORD가 일치하지 않습니다.");
            System.out.println();
            setUser(null);
            return;
        }

        getUser().setState(StateFactory.create(relation));

        System.out.println("로그인 되었습니다.");
        System.out.println();
    }

    @Override
    protected void response(String command) {
        switch (command) {
            case "1":
                signUp();
                break;
            case "2":
                login();
                break;
            case "3":
                notice();
                break;
            case "4":
                getUser().setState(State.EXIT);
                break;
            default:
                System.out.println("존재하지 않는 명령입니다.");
                System.out.println();
        }
    }
}
