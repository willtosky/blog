package cn.crybird.manage.controller.show;

import cn.crybird.manage.model.Article;
import cn.crybird.manage.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String search(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                         @RequestParam("key") String key, Model model) {
        PageHelper.startPage(1, 10);
        PageInfo<Article> pageInfo = articleService.getListByKey(key).toPageInfo();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("key", key);
        return "search";
    }

}
