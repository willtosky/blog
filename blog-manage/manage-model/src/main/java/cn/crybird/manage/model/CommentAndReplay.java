package cn.crybird.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CommentAndReplay extends Comment {

    private List<CommentReplay> replaies = new ArrayList<>();

}
