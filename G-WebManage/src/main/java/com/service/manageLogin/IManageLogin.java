package com.service.manageLogin;

import communal.Result;
import model.dto.manage.ManageLoginDTO;

public interface IManageLogin {

    Result login(ManageLoginDTO manageLoginDTO);
}
