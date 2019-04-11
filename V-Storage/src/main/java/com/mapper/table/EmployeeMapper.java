package com.mapper.table;

import com.MyMapper;
import model.entity.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper extends MyMapper<Employee> {

    void deleteForIDS(@Param("IDS") Long [] IDS);
}
