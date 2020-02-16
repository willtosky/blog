package cn.crybird.manage.controller.show;

import cn.crybird.manage.model.Article;
import cn.crybird.manage.model.TagCount;
import cn.crybird.manage.model.TypeCount;
import cn.crybird.manage.service.ArticleService;
import cn.crybird.manage.service.TagService;
import cn.crybird.manage.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tags")
public class TagsPageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping({"","/{id}"})
    public String listArticle(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                              @PathVariable(value = "id",required = false) Long id,
                              Model model){
        Map<String, Object> result = new HashMap<>();
        List<TagCount> tagCounts = tagService.getTagCountList();
        result.put("tagCounts",tagCounts);

        PageHelper.startPage(page,pageSize);
        Article article = new Article();
        if(id != null){
            article.setTagIds(String.valueOf(id));
        }
        PageInfo<Article> pageInfo = articleService.getListForShow(article).toPageInfo();
        result.put("pageInfo",pageInfo);

        result.put("activeId",id);
        model.addAttribute("result", result);
        return "tags";
    }

}
