package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Article;

import java.util.List;
import java.util.Map;

/**
 * @Classname IArticleService
 * @Description TODO
 * @Date 2019-3-14 9:46
 * @Created by CrazyStone
 */

public interface IArticleService {
    // 分页查询文章列表
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count);

    // 统计前10的热度文章信息
    public List<Article> getHeatArticles();

    // 根据文章id查询单个文章详情
    public Article selectArticleWithId(Integer id);

    // 发布文章
    public void publish(Article article);

    // 根据主键更新文章
    public void updateArticleWithId(Article article);

    // 根据主键删除文章
    public void deleteArticleWithId(int id);

    // 获取所有已存在的文章分类
    List<String> getAllExistsCategories();

    // 获取所有标签以及出现次数
    Map<String, Integer> getAllTagsAndCount();

    // 获取所有分类以及出现次数
    Map<String, Integer> getAllCategoriesAndCount();
}

