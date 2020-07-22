package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Comment;

/**
 * @Classname ICommentService
 * @Description 文章评论业务处理接口
 * @Date 2019-3-14 10:13
 * @Created by CrazyStone
 */
public interface ICommentService {
    // 获取文章下的评论
    public PageInfo<Comment> getComments(Integer aid, int page, int count);

    // 用户发表评论
    public void pushComment(Comment comment);

    // 获取所有评论
    PageInfo<Comment> getAllComments(int page, int count);

    // 删除某个评论
    void deleteComment(int id, int articleId);

    // 修改评论状态（approved / denied）
    void updateCommentStatus(int id, int articleId);
}

