package edu.ut.grouper.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public List<String> getAllUsernames() {
        List<String> users = new ArrayList<String>();
        users.add("MarK");
        users.add("Ken");
        users.add("Fowafolo");
        return users;
    }

}