package com.back.mapper;

import com.back.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author campus
 * @since 2023-03-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
