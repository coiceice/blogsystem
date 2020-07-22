package com.itheima.dao;

import java.util.List;

import com.itheima.model.domain.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
@Repository
@Mapper
public interface UserMapper {
/*     @Insert("insert into t_user(username,password,email,created,valid)"+ 
            " values(#{username},#{password},#{email},#{created},#{valid});"+
            " insert into t_user_authority(user_id,authority_id) values(select id from )") */
    int addUser(User user);
    
    int addAuthority(@Param("user")User user,@Param("authority_id")int authority_id);

    @Select("select * from t_user u,t_user_authority ua,t_authority au where u.id=ua.user_id and ua.authority_id=au.id")
    List<User> queryUser();

    @Select("select username,password,valid,authority from t_user u,t_user_authority ua,t_authority au where u.id=ua.user_id and ua.authority_id=au.id and username = #{username}")
    User queryUserByName(String username);

    @Delete("delete from t_user where id=#{id}; delete from t_user_authority where id=#{id}")
    int deleteUser(int id);

    @Update("update t_user set username=#{user.username},email=#{user.email},valid=#{user.valid} where id=#{user.id};update t_user_authority set authority_id = #{authority_id} where authority_id=#{user.id}")
    int updateUser(@Param("user")User user,@Param("authority_id")int authority_id);


}