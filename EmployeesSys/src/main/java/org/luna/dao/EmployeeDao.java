package org.luna.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.luna.bean.EmployeeBean;

@Mapper
public interface EmployeeDao extends BaseMapper<EmployeeBean> {
}
