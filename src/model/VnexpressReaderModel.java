package model;

import entity.Articles;

import java.sql.*;
import java.util.HashSet;

public class VnexpressReaderModel {
    public boolean insertIntoDb(Articles articles){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vnexpressreader?useUnicode=true&characterEncoding=utf-8", "root", "");
            String sql = "insert into vnexpressdb (newsTitle, newsUrl) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, articles.getTitle());
            preparedStatement.setString(2, articles.getLink());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
