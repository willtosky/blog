package cn.crybird.manage.controller.admin;

import cn.crybird.manage.service.CommentReplayService;
import cn.crybird.manage.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentReplayService commentReplayService;

}
