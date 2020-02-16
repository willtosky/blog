package cn.crybird.manage.controller.show;

import cn.crybird.manage.model.Article;
import cn.crybird.manage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/archives")
public class ArchivesPageController {

    @Autowired
    private ArticleService articleService;

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.YEAR));
    }

    @GetMapping
    public String archives(Model model) {
        List<Article> articles = articleService.getArchives(2);
        List<List<Article>> yearsList = new ArrayList<>();
        if(articles.size() > 0){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(articles.get(0).getPublishTime());
            int YEAR = calendar.get(Calendar.YEAR);
            List<Article> yearList = new ArrayList<>();
            for (Article article : articles) {
                calendar.setTime(article.getPublishTime());
                int year = calendar.get(Calendar.YEAR);
                if(YEAR == year){
                    yearList.add(article);
                }else{
                    yearsList.add(yearList);
                    yearList = new ArrayList<>();
                    YEAR = year;
                    yearList.add(article);
                }
            }
            yearsList.add(yearList);
        }

        model.addAttribute("years", yearsList);
        model.addAttribute("count", articles.size());
        return "archives";
    }

}
