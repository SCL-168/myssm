package com.scl.crm.settings.service;

import com.scl.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User queryUserByLoginActAndPwd(Map<String,Object>map);

    //查询所有用户
    List<User>queryAllUsers();
}
