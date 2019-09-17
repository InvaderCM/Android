package edu.niit.myapplication.service.impl;

import android.content.Context;

import edu.niit.myapplication.dao.UserInfoDao;
import edu.niit.myapplication.entity.User;


public class UserServiceImpl implements UserService {
    private UserInfoDao dao;
    public UserServiceImpl(Context context){
        dao=new UserInfoDaoImpl(context);
    }
    @Override
    public User get(String username) {
        return dao.select(username);
    }

    @Override
    public void save(User user) {
    dao.insert(user);
    }

    @Override
    public void modify(User user) {
    dao.update(user);
    }
}
