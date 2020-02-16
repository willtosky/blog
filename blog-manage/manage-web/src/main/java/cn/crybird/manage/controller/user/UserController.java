package cn.crybird.manage.controller.user;

import cn.crybird.manage.constant.HttpMethod;
import cn.crybird.manage.model.base.ResponseData;
import cn.crybird.manage.model.User;
import cn.crybird.manage.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

}
