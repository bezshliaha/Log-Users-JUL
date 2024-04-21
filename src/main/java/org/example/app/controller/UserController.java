package org.example.app.controller;

import org.example.app.service.UserService;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Constants;
import org.example.app.view.UserView;

import java.util.Map;

public class UserController {

    UserView view;
    UserService service;

    public UserController(UserService service, UserView view) {
        this.service = service;
        this.view = view;
    }

    public void createUser() {
        Map<String, String> data = view.getCreateData();
        String res = service.create(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }

    public void readUsers() {
        String res = service.read();
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput("\nUSERS:\n" + res);
            AppStarter.startApp();
        }
    }

    public void readUserById() {
        Map<String, String> data = view.getByIDData();
        String res = service.readById(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput("\nUSER BY ID:\n" + res);
            AppStarter.startApp();
        }
    }

    public void updateUser() {
        Map<String, String> data = view.getUpdateData();
        String res = service.update(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }

    public void deleteUser() {
        Map<String, String> data = view.getDeleteData();
        String res = service.delete(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }
}
