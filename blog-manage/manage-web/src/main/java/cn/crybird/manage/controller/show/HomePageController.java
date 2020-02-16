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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomePageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping({"/","/home"})
    public String listArticle(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                              Model model){
        Map<String, Object> result = new HashMap<>();

        List<TypeCount> typeCounts = typeService.getTopN(10);
        result.put("typeCounts",typeCounts);

        List<TagCount> tagCounts = tagService.getTopN(10);
        result.put("tagCounts",tagCounts);

        List<Article> recommendArticles = articleService.getTopNWithRecommend(10);
        result.put("recommendArticles",recommendArticles);

        PageHelper.startPage(page,pageSize);
        PageInfo<Article> pageInfo = articleService.getListForShow(new Article()).toPageInfo();
        result.put("pageInfo",pageInfo);

        model.addAttribute("result", result);
        return "home";
    }

}
