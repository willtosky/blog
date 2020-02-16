package cn.crybird.manage.controller.show;

import cn.crybird.manage.model.Article;
import cn.crybird.manage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FragmentController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/top/{N}")
    public String footerTopN(@PathVariable("N") Integer N, Model model){
        List<Article> topN = articleService.getTopNWithRecommend(N);
        model.addAttribute("topN",topN);
        return "_fragment :: topn";
    }

}
