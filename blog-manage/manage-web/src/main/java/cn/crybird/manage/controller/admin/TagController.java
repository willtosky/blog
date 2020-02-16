package cn.crybird.manage.controller.admin;

import cn.crybird.manage.controller.base.BaseController;
import cn.crybird.manage.model.Tag;
import cn.crybird.manage.model.base.ResponseData;
import cn.crybird.manage.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/tags")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public String tags(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "name",required = false) String name,
                       Model model){
        Tag tag = new Tag();
        tag.setName(name);
        PageHelper.startPage(page,pageSize);
        model.addAttribute("result",ResponseData.<PageInfo<Tag>>builder().data(tagService.getList(tag,null).toPageInfo()).build());
        return "admin/tags";
    }

    @PostMapping("/create")
    public String create(Tag tag,Model model, RedirectAttributes redirectAttributes) throws Exception {
        ResponseData<Tag> result = ResponseData.<Tag>builder().data(tag).build();
        tag.setName(Strings.trimToNull(tag.getName()));
        //name是否为空
        if(tag.getName() == null){
            result.setMsg("名称不能为空");
            model.addAttribute("result", result);
            return "admin/tag-edit";
        }
        //新name与原name是否相同
        tag.setId(null);
        if(tagService.count(tag) > 0){
            result.setMsg("标签已存在");
            model.addAttribute("result",result);
            return "admin/tag-edit";
        }
        //添加成功
        if(tagService.create(tag) > 0){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","添加成功");
            //添加失败
        }else {
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","添加失败");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/update")
    public String update(Tag tag,Model model, RedirectAttributes redirectAttributes) throws Exception {
        //id是否为空
        if(tag.getId() == null){
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","修改失败，不存在的标签");
            return "redirect:/admin/tags";
        }
        ResponseData<Tag> result = ResponseData.<Tag>builder().data(tag).build();
        tag.setName(Strings.trimToNull(tag.getName()));
        //name是否为空
        if(tag.getName() == null){
            result.setMsg("名称不能为空");
            model.addAttribute("result", result);
            return "admin/tag-edit";
        }
        //根据id和name计数
        //修改后name是否与原name相同
        if(tagService.count(tag) > 0){
            result.setMsg("不能与原标签名相同");
            model.addAttribute("result",result);
            return "admin/tag-edit";
        }
        long id = tag.getId();
        tag.setId(null);
        //根据name计数
        //修改后name是否与与其他name相同
        if(tagService.count(tag) > 0){
            model.addAttribute("result",result.setMsg("存在相同的分类名"));
            tag.setId(id);
            return "admin/tag-edit";
        }
        tag.setId(id);
        //修改成功
        if(tagService.update(tag) > 0){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","修改成功");
            //修改失败
        }else {
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","修改失败，不存在的标签");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/add")
    public String getEditPage(){
        return "admin/tag-edit";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id,Model model){
        Tag tag = tagService.getById(id);
        model.addAttribute("result",ResponseData.<Tag>builder().data(tag).build());
        return "admin/tag-edit";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id,RedirectAttributes redirectAttributes){
        if(tagService.removeById(id) > 0){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","删除成功");
        }else{
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","删除失败");
        }
        return "redirect:/admin/tags";
    }

}
