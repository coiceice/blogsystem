package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.CommentMapper;
import com.itheima.dao.StatisticMapper;
import com.itheima.model.domain.Comment;
import com.itheima.model.domain.Statistic;
import com.itheima.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname CommentServiceImpl
 * @Description TODO
 * @Date 2019-3-14 10:15
 * @Created by CrazyStone
 */

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    // 根据文章id分页查询评论
    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        List<Comment> commentList = redisTemplate.opsForList().range("commentOf_" + aid + ":" + page + ":" + count, 0, -1);
        if(commentList == null || commentList.size() == 0) {
            PageHelper.startPage(page,count);
            commentList = commentMapper.selectCommentWithPage(aid);
            redisTemplate.opsForList().leftPushAll("commentOf_" + aid + ":" + page + ":" + count, commentList);
        }
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }

    // 用户发表评论
    @Override
    public void pushComment(Comment comment) {
        commentMapper.pushComment(comment);
        //模糊查询，删除该文章的缓存
        redisTemplate.delete(redisTemplate.keys("commentOf_" + comment.getArticleId() + "*"));
        // 更新文章评论数据量
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum() + 1);
        statisticMapper.updateArticleCommentsWithId(statistic);
    }

    @Override
    public PageInfo<Comment> getAllComments(int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> commentList = commentMapper.selectAllComment();
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }

    @Override
    public void deleteComment(int id, int articleId) {
        commentMapper.deleteCommentById(id);
        //模糊查询，删除该文章的缓存
        redisTemplate.delete(redisTemplate.keys("commentOf_" + articleId + "*"));
    }

    @Override
    public void updateCommentStatus(int id, int articleId) {
        commentMapper.changeStatus(id);
        //模糊查询，删除该文章的缓存
        redisTemplate.delete(redisTemplate.keys("commentOf_" + articleId + "*"));
    }
}