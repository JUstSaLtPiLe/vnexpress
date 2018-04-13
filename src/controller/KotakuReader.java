package controller;

import entity.Articles;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class KotakuReader implements Reader {
    @Override
    public void getIndexArticles(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://kotaku.com/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("h1.js_entry-title a.js_entry-link:first-child");
        for (Element element1: element){
            Articles articles = new Articles();
            articles.setTitle(element1.text());
            articles.setLink(element1.attr("href"));
            System.out.println(articles.getTitle());
        }
    }
    @Override
    public String getListCategories(){
        return "Categories";
    }
    @Override
    public String getArticlesbyCategory(String CateId){
        return "Articles in" + " " + "category";
    }
    @Override
    public Articles getArticleDetail(String url){
        Document choosedDoc = null;
        try {
            choosedDoc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ArticleElements = choosedDoc.select("h2.description");
        Articles articles = new Articles();
        articles.setTitle(ArticleElements.text());
        System.out.println(articles.getTitle());
        ArticleElements = choosedDoc.select(".Normal span");
        for (Element ArticleDetail: ArticleElements){
            articles.setTitle(ArticleDetail.text());
            System.out.println(articles.getTitle());
        }
        return articles;
    }
}
