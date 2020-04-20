package com.java.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.java.entity.User;

/**
 * 
 * @Description: 操作数据库接口
 * @author: wqk
 * @date: 2020下午8:46:55
 * @version: 1.0
 */
@Mapper
public interface UserMapper {

    public User queryUserById(Integer id);

    public List<User> queryUsers();

    public int save(User user);

    public int update(User user);

    public int deleteUserById(Integer id);
}
