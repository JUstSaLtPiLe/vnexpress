package service;

import controller.Reader;
import controller.VnexpressReader;
import entity.Articles;
import model.VnexpressReaderModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class CrawlerThread extends Thread {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CrawlerThread(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        Articles articles = new Articles();
        Articles articles1 = new Articles();
//        HashSet<Articles> hashSet = new HashSet<Articles>();
        VnexpressReaderModel vnexpressReaderModel = new VnexpressReaderModel();
        Document doc = null;
        try {
            doc = Jsoup.connect(this.url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("h1");
        articles.setTitle(element.text());
        articles.setLink(this.url);
        element = doc.select(".list_title a:first-child");
        for (Element el : element) {
            articles1.setLink(el.attr("href"));
            try {
                doc = Jsoup.connect(articles1.getLink()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            element = doc.select("h1");
            articles1.setTitle(element.text());
//            vnexpressReaderModel.insertIntoDb(articles1);
//            hashSet.add(articles1);
        }
    }
}
