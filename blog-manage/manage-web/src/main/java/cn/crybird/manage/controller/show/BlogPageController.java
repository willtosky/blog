package cn.crybird.manage.controller.show;

import cn.crybird.manage.model.Article;
import cn.crybird.manage.model.Comment;
import cn.crybird.manage.model.CommentReplay;
import cn.crybird.manage.service.ArticleService;
import cn.crybird.manage.service.CommentReplayService;
import cn.crybird.manage.service.CommentService;
import cn.crybird.manage.service.impl.ArticleServiceImpl;
import cn.crybird.manage.util.MarkdownUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/blog")
public class BlogPageController{

    private static String EMAIL_REG = "^[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.[0-9a-zA-Z]+$";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentReplayService commentReplayService;

    @GetMapping("/{id}")
    public String blog(@PathVariable("id") Long id, Model model, HttpServletRequest request) throws Exception {
        /**
         * 访问次数+1
         */
        if(articleService.incBrowseCount(id) < 1){
            throw new Exception("文章未找到");
        }
        Article article = articleService.getById(id);
        String content = ((ArticleServiceImpl) articleService).getMarkdownContent(article.getMdPath());
        model.addAttribute("article",article);
        model.addAttribute("md", MarkdownUtils.markdownToHtmlExtensions(content));
        return "blog";
    }

    @GetMapping("/{id}/comment")
    public String comment(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                          @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                          @PathVariable("id") Long id,
                          Model model){
        model.addAttribute("comments",commentService.getCommentAndReplayListByArticleId(id));
        return "blog :: comments";
    }

    @PostMapping("/comment")
    public String comment(Comment comment,Model model) throws Exception {
        List<String> msgs = new ArrayList<>();
        if(comment.getArticleId() == null || articleService.countById(comment.getArticleId()) < 1){
            msgs.add("无法为不存在的文章评论");
        }
        String nickname = Strings.trimToNull(comment.getNickname());
        if(nickname == null){
            msgs.add("用户昵称不能为空");
            comment.setNickname(nickname);
        }
        String email = Strings.trimToNull(comment.getEmail());
        if(email == null || !Pattern.matches(EMAIL_REG,email)){
            msgs.add("邮箱格式不正确");
        }else {
            comment.setEmail(email);
        }
        comment.setId(null);
        if(msgs.size() == 0){
            comment.setAvatar("/images/avatar.jpg");
            commentService.create(comment);
            if(comment.getId() == null){
                msgs.add("评论发布失败");
                model.addAttribute("msg",msgs);
            }
        }else{
            model.addAttribute("msg",msgs);
        }
        model.addAttribute("comments",commentService.getCommentAndReplayListByArticleId(comment.getArticleId()));
        return "blog :: comments";
    }

    @PostMapping("/replay")
    public String comment(CommentReplay commentReplay,@RequestParam("articleId") Long articleId,Model model) throws Exception {
        List<String> msgs = new ArrayList<>();
        if(commentReplay.getCommentId() == null || commentService.countById(commentReplay.getCommentId()) < 1){
            msgs.add("无法为不存在的评论回复");
        }
        String nickname = Strings.trimToNull(commentReplay.getNickname());
        if(nickname == null){
            msgs.add("用户昵称不能为空");
            commentReplay.setNickname(nickname);
        }
        String email = Strings.trimToNull(commentReplay.getEmail());
        if(email == null || !Pattern.matches(EMAIL_REG,email)){
            msgs.add("邮箱格式不正确");
        }else {
            commentReplay.setEmail(email);
        }
        commentReplay.setId(null);
        if(msgs.size() == 0){
            commentReplay.setAvatar("/images/avatar.jpg");
            commentReplayService.create(commentReplay);
            if(commentReplay.getId() == null){
                msgs.add("评论回复失败");
                model.addAttribute("msg",msgs);
            }
        }else{
            model.addAttribute("msg",msgs);
        }
        model.addAttribute("gotoId",commentReplay.getCommentId());
        model.addAttribute("comments",commentService.getCommentAndReplayListByArticleId(articleId));
        return "blog :: comments";
    }

}
