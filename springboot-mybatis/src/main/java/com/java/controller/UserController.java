package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.entity.User;
import com.java.mapper.UserMapper;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 
     * @Description: 根据id查询用户信息
     * @param id
     * @return
     * @return: User
     */
    @GetMapping("/queryUserById")
    @ResponseBody
    public User queryUserById(@RequestParam("id") Integer id) {
        return userMapper.queryUserById(id);
    }

    /**
     * 
     * @Description: 查询所有用户
     * @return
     * @return: List<User>
     */
    @GetMapping("/queryUsers")
    @ResponseBody
    public List<User> queryUsers() {
        return userMapper.queryUsers();
    }

    /**
     * 
     * @Description: 保存用户信息
     * @return
     * @return: int
     */
    @GetMapping("/save")
    @ResponseBody
    public int save() {
        User user = new User();
        user.setUserName("1234");
        user.setPassword("1234");
        return userMapper.save(user);
    }

    /**
     * 
     * @Description: 更新用户信息
     * @return
     * @return: int
     */
    @GetMapping("/update")
    @ResponseBody
    public int update() {
        User user = new User();
        user.setId(12);
        user.setUserName("wqk122");
        user.setPassword("wqk122");
        return userMapper.update(user);
    }

    /**
     * 
     * @Description: 删除用户信息
     * @param id
     * @return
     * @return: int
     */
    @GetMapping("/deleteUserById")
    @ResponseBody
    public int deleteUserById(@RequestParam("id") Integer id) {
        return userMapper.deleteUserById(id);
    }

}
