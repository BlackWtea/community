package com.mashen.community.mapper;

import com.mashen.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,token,name,gmt_created,gmt_modified) values(#{accountId},#{token},#{name},#{gmtCreated},#{gmtModified})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User findUserByToken(String token);
}
