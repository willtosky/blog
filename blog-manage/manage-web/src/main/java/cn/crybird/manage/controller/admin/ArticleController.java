package cn.crybird.manage.controller.admin;

import cn.crybird.manage.controller.base.BaseController;
import cn.crybird.manage.model.Article;
import cn.crybird.manage.model.Tag;
import cn.crybird.manage.model.Type;
import cn.crybird.manage.model.base.ResponseData;
import cn.crybird.manage.service.ArticleService;
import cn.crybird.manage.service.TagService;
import cn.crybird.manage.service.TypeService;
import cn.crybird.manage.service.impl.ArticleServiceImpl;
import cn.crybird.manage.util.FileTaker;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/blogs")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public String listArticle(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                              Model model){
        List<Type> types = typeService.getTypes();
        model.addAttribute("types",types);
        PageHelper.startPage(page,pageSize);
        PageInfo<Article> pageInfo = articleService.getList(new Article(), null).toPageInfo();
        model.addAttribute("result",ResponseData.<PageInfo<Article>>builder().data(pageInfo).build());
        return "admin/blogs";
    }

    @PostMapping
    public String listArticle(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                              Article article,
                              Model model){
        article.setTitle(Strings.trimToNull(article.getTitle()));
        List<Type> types = typeService.getTypes();
        model.addAttribute("types",types);
        PageHelper.startPage(page,pageSize);
        PageInfo<Article> pageInfo = articleService.getList(article, null).toPageInfo();
        model.addAttribute("result",ResponseData.<PageInfo<Article>>builder().data(pageInfo).build());
        return "admin/blogs";
    }

    @PostMapping("/save")
    public String save(Article article, Model model, RedirectAttributes redirectAttributes) throws Exception {
        //设置最近一次编辑时间
        article.setEditedTime(new Date());
        if(article.getStatus() != null && article.getStatus() == 1){
            //设置发布时间
            article.setPublishTime(article.getEditedTime());
        }else{
            article.setStatus(null);
        }

        ResponseData<Object> result = ResponseData.builder().data(article).build();
        ArrayList<String> msgs = new ArrayList<>();

        if(Strings.trimToNull(article.getTitle()) == null){
            msgs.add("标题不能为空");
        }

        if(article.getTypeId() == null || article.getTypeId() < 1){
            msgs.add("请选择文章类型");
        }else{
            Type type = typeService.getById(article.getTypeId());
            if (type == null){
                msgs.add("文章类型不存在，请重新选择");
            }else{
                article.setType(type.getName());
            }
        }

        String[] tagIdsStr = article.getTagIds().split(",");
        List<Integer> tagIds = new ArrayList<>(tagIdsStr.length);
        for (String id : tagIdsStr){
            id = Strings.trimToNull(id);
            if(id != null && StringUtils.isNumber(id)){
                tagIds.add(Integer.valueOf(id));
            }
        }
        if(tagIds.size() > 0){
            List<Tag> tagsList = tagService.getListByIds(tagIds);
            if(tagsList.size() == 1){
                article.setTags(tagsList.get(0).getName());
            }else if(tagsList.size() > 1){
                StringBuilder sb = new StringBuilder();
                for (Tag tag : tagsList){
                    sb.append(tag.getName());
                    sb.append(',');
                }
                sb.deleteCharAt(sb.length()-1);
                article.setTags(sb.toString());
            }
        }

        if(Strings.trimToNull(article.getImagePath()) == null){
            msgs.add("首图不能为空");
        }

        if(msgs.size() > 0){
            result.setMsgs(msgs);
        }

        model.addAttribute("result",result);
        model.addAttribute("types",typeService.getTypes());
        model.addAttribute("tags",tagService.getTags());

        if(article.getId() == null){
            if(articleService.count(article.getTitle()) > 0){
                msgs.add("标题已存在");
                return "admin/blog-edit";
            }
            if(articleService.create(article) > 0){
                msgs.add("新建成功");
            }else{
                msgs.add("新建失败");
                return "admin/blog-edit";
            }
        }else{
            if(articleService.update(article) > 0){
                msgs.add("保存成功");
            }else {
                msgs.add("保存失败");
                return "admin/blog-edit";
            }
        }

        if(article.getStatus() != null && article.getStatus() == 1){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","文章发布成功");
            return "redirect:/admin/blogs";
        }

        return "admin/blog-edit";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("types",typeService.getTypes());
        model.addAttribute("tags",tagService.getTags());
        //添加一个空的Article，方便动态渲染页面
        Article article = new Article();
        article.setRecommendFlag(1);
        article.setMark(1);
        model.addAttribute("result",ResponseData.<Article>builder().data(article).build());
        return "admin/blog-edit";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model){
        Article article = articleService.getById(id);
        if(article == null){
            model.addAttribute("msg","文章获取失败");
            return "admin/blogs";
        }
        try {
            String content = ((ArticleServiceImpl)articleService).getMarkdownContent(article.getMdPath());
            article.setMdPath(content);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg","文章获取失败");
            return "admin/blogs";
        }

        model.addAttribute("types",typeService.getTypes());
        model.addAttribute("tags",tagService.getTags());
        model.addAttribute("result",ResponseData.<Article>builder().data(article).build());
        return "admin/blog-edit";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id,RedirectAttributes redirectAttributes){
        if(articleService.removeById(id) > 0){
            redirectAttributes.addFlashAttribute("success","true");
            redirectAttributes.addFlashAttribute("msg","删除成功");
        }else{
            redirectAttributes.addFlashAttribute("success","false");
            redirectAttributes.addFlashAttribute("msg","删除失败");
        }
        return "redirect:/admin/blogs";
    }

}
