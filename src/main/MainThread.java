package main;

import controller.KotakuReader;
import controller.Reader;
import controller.VnexpressReader;
import service.CrawlerThread;

import java.util.ArrayList;
import java.util.Scanner;

public class MainThread {

    public static void main(String[] args) {
        generateMenu();
    }

    public static void generateMenu(){
        Scanner input = new Scanner(System.in);
        int c;
        Reader reader;
        System.out.println("Choice page to read");
        System.out.println("+++++++++++++++++++");
        System.out.println("1. Vnexpress");
        System.out.println("2. Kotaku");
        int pageChoice = input.nextInt();
        if(pageChoice == 1) {
            reader = new VnexpressReader();
        }
        else{
            reader = new KotakuReader();
        }
        while (true){
            System.out.println("News reader application");
            System.out.println("========================");
            System.out.println("1. Display articles on home page");
            System.out.println("2. Display categories");
            System.out.println("3. Dislay articles in category");
            System.out.println("4. Display article detail");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            c= input.nextInt();
            switch (c){
                case 1:
                    reader.getIndexArticles();
                    break;
                case 2:
                    reader.getListCategories();
                    break;
                case 3:
                    reader.getArticlesbyCategory("");
                    break;
                case 4:
                    reader.getArticleDetail("");
                    break;
                case 5:
                    System.out.println("Goodbye. See you again!");
                    System.exit(5);
                    break;
                 default:
                     System.out.println("Your choice is invalid");
                     break;
            }

        }
    }
}
