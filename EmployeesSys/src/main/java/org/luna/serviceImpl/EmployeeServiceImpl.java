package org.luna.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.luna.bean.EmployeeBean;
import org.luna.dao.EmployeeDao;
import org.luna.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, EmployeeBean> implements EmployeeService {
}
