package io.wisoft.project.server;

import io.wisoft.project.common.State;
import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.user.UserDTO;

public abstract class LoggedInServer extends Server {

    @Override
    public UserDTO showPage(UserDTO user) {

        if(user.getState() == State.UNLOGGEDIN || user.getState() == State.EXIT) {
            System.out.println("잘못된 접근입니다.");
            return user;
        }

        setUser(user);

        String command = null;

        System.out.println("1. 개인정보조회");
        System.out.println("2. 개인정보수정");
        System.out.println("3. 공지사항 확인");
        additionalOption();
        System.out.println("로그아웃");

        command = commandInput();

        response(command);

        return getUser();
    }

    public void userData() {
        System.out.println(getUser());
        System.out.println();
    }

    public void userModify() {
        String attribute = null;
        String newData = null;
        DAO dao = getDaoFactory().create(getUser().getState());

        System.out.println("수정할 정보를 입력해주세요.");
        System.out.println("1. 비밀번호 2. 이름 3. 학과");

        attribute = convertAttribute(commandInput());

        if(attribute == null) {
            System.out.println("존재하지 않는 정보입니다.");
            System.out.println();
            return;
        }

        System.out.println("새로운 데이터를 입력해주세요.");
        newData = commandInput();

        if(attribute.equals("dept") && !departmentList.contains(newData)) {
            System.out.println("존재하지 않는 학과입니다.");
            return;
        }

        userDTOUpadte(attribute, newData);
        dao.update(attribute, newData, getUser());

        System.out.println("데이터가 수정되었습니다.");
        System.out.println();

    }

    public void logout() {
        System.out.println("로그아웃되었습니다.");
        getUser().setState(State.UNLOGGEDIN);
        System.out.println();
    }

    @Override
    protected void response(String command) {
        switch (command) {
            case "1":
                userData();
                break;
            case "2":
                userModify();
                break;
            case "3":
                notice();
                break;
            case "로그아웃":
                logout();
                break;
            default:
                System.out.println("잘못된 키워드를 입력했습니다.");
        }
    }

    protected String convertAttribute(String command) {
        switch (command) {
            case "1":
                return "password";
            case "2":
                return "name";
            case "3":
                return "dept";
            default:
                return null;
        }
    }

    protected void userDTOUpadte(String attribute, String newData) {

        switch (attribute) {
            case "password":
                getUser().setPassword(newData);
                break;
            case "name":
                getUser().setName(newData);
                break;
            case "dept":
                getUser().setDept(newData);
                break;
            default:
        }
    }

    protected abstract void additionalOption();
}
