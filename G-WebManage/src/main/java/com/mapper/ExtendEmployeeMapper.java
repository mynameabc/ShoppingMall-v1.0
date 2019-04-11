package com.mapper;

import model.dto.manage.EmployeeDTO;
import model.entity.Employee;
import model.entity.extend.EmployeeExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtendEmployeeMapper {

    List<EmployeeExtend> getEmployeeList(@Param("employeeDTO") EmployeeDTO employeeDTO, @Param("officeIDSList") List<String> officeIDSList);
}
