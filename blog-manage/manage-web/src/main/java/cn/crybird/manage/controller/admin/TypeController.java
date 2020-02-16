package cn.crybird.manage.controller.admin;

import cn.crybird.manage.controller.base.BaseController;
import cn.crybird.manage.model.Type;
import cn.crybird.manage.model.base.ResponseData;
import cn.crybird.manage.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/types")
public class TypeController extends BaseController {

    @Autowired
    private TypeService typeService;

    @GetMapping
    public String types(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                        @RequestParam(value = "name",required = false) String name,
                        Model model){
        Type type = new Type();
        type.setName(name);
        PageHelper.startPage(page,pageSize);
        model.addAttribute("result",ResponseData.<PageInfo<Type>>builder().data(typeService.getList(type,null).toPageInfo()).build());
        return "admin/types";
    }

    @PostMapping("/create")
    public String create(Type type,Model model, RedirectAttributes redirectAttributes) throws Exception {
        ResponseData<Type> result = ResponseData.<Type>builder().data(type).build();
        type.setName(Strings.trimToNull(type.getName()));
        //name是否为空
        if(type.getName() == null){
            result.setMsg("名称不能为空");
            model.addAttribute("result", result);
            return "admin/type-edit";
        }
        //新name与原name是否相同
        type.setId(null);
        if(typeService.count(type) > 0){
            result.setMsg("类名已存在");
            model.addAttribute("result",result);
            return "admin/type-edit";
        }
        //添加成功
        if(typeService.create(type) > 0){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","添加成功");
        //添加失败
        }else {
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","添加失败");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/update")
    public String update(Type type,Model model, RedirectAttributes redirectAttributes) throws Exception {
        //id是否为空
        if(type.getId() == null){
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","修改失败，不存在的分类");
            return "redirect:/admin/types";
        }
        ResponseData<Type> result = ResponseData.<Type>builder().data(type).build();
        type.setName(Strings.trimToNull(type.getName()));
        //name是否为空
        if(type.getName() == null){
            result.setMsg("名称不能为空");
            model.addAttribute("result", result);
            return "admin/type-edit";
        }
        //根据id和name计数
        //修改后name是否与原name相同
        if(typeService.count(type) > 0){
            result.setMsg("不能与原分类名相同");
            model.addAttribute("result",result);
            return "admin/type-edit";
        }
        long id = type.getId();
        type.setId(null);
        //根据name计数
        //修改后name是否与与其他name相同
        if(typeService.count(type) > 0){
            model.addAttribute("result",result.setMsg("存在相同的分类名"));
            type.setId(id);
            return "admin/type-edit";
        }
        type.setId(id);
        //修改成功
        if(typeService.update(type) > 0){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","修改成功");
        //修改失败
        }else {
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","修改失败，不存在的分类");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/add")
    public String getEditPage(){
        return "admin/type-edit";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id,Model model){
        Type type = typeService.getById(id);
        model.addAttribute("result",ResponseData.<Type>builder().data(type).build());
        return "admin/type-edit";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id,RedirectAttributes redirectAttributes){
        if(typeService.removeById(id) > 0){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","删除成功");
        }else{
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","删除失败");
        }
        return "redirect:/admin/types";
    }

}
