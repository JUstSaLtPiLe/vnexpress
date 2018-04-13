package controller;

import entity.Articles;

public interface Reader {
    public void getIndexArticles();
    public String getListCategories();
    public String getArticlesbyCategory(String CateId);
    public Articles getArticleDetail(String url);
}
