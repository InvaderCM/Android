package edu.niit.myapplication.service.impl;


import edu.niit.myapplication.entity.User;

public interface UserService {

User get(String username);
void save(User user);
void modify(User user);




}
