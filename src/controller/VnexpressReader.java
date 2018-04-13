package controller;

import entity.Articles;
import model.VnexpressReaderModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.CrawlerThread;
import service.CrawlerThread2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;


public class VnexpressReader implements Reader {
    public static ArrayList<Articles> listArticles = new ArrayList<>();

    @Override
    public void getIndexArticles(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://vnexpress.net/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select(".list_news .title_news a:first-child");
        for (int i = 0; i < element.size(); i++){
            Element el = element.get(i);
            Articles articles = new Articles();
            articles.setLink(el.attr("href"));
            articles.setTitle(el.text());
            listArticles.add(articles);
//            articles.setLink(el.attr("href"));
//            System.out.println(i + 1 + ". " + articles.getTitle());
        }

//        System.out.println("Choose artile you want to read: ");
//        Scanner input = new Scanner(System.in);
//        int choosedIndex = input.nextInt();
//        int trueIndex = choosedIndex - 1;
//        Element choosedElement = element.get(trueIndex);
//        System.out.println(choosedElement.text());
//        String url = choosedElement.attr("href");
//        getArticleDetail(url);
//        for (Element el : element) {
//            Articles articles = new Articles();
//            articles.setTitle(el.text());
//            articles.setLink(el.attr("href"));
//            System.out.println(articles.getTitle());
//        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException, TimeoutException {
        int dem = 0;
        VnexpressReader vnexpressReader = new VnexpressReader();
        vnexpressReader.getIndexArticles();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        VnexpressReaderModel vnexpressReaderModel = new VnexpressReaderModel();
        HashSet<Future<HashSet<Articles>>> hashSet = new HashSet<Future<HashSet<Articles>>>();
        HashSet<Articles> hashSet1 = new HashSet<Articles>();
        for (int i = 0; i < listArticles.size(); i++){
//            CrawlerThread crawlerThread = new CrawlerThread(listArticles.get(i).getLink());
//            crawlerThread.start();
            CrawlerThread2 crawlerThread2 = new CrawlerThread2(listArticles.get(i).getLink());
            hashSet.add(executorService.submit(crawlerThread2));
        }
        for (Future<HashSet<Articles>> f: hashSet){
            for (Articles articles: f.get()){
                vnexpressReaderModel.insertIntoDb(articles);
            }
        }
        executorService.shutdown();
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
        Elements video = choosedDoc.select("div .box_embed_video");
        for (Element ArticleDetail: ArticleElements){
            articles.setTitle(ArticleDetail.text());
            System.out.println(articles.getTitle());
        }
        return articles;
    }
}
