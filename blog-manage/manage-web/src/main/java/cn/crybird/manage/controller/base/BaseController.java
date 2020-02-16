package cn.crybird.manage.controller.base;

import cn.crybird.manage.model.base.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    private HttpServletRequest request;

    public HttpServletRequest getRequest(){
        return this.request;
    }

    @ModelAttribute
    public void result(Model model){
        model.addAttribute("result", ResponseData.builder().build());
    }

}
