package models;

import database_config.DB;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class GeneralNews extends News {
 private int id;
    public GeneralNews(String newsHeading, String news, int personId){
        this.news=news;
        this.newsHeading = newsHeading;
        this.personId=personId;
    }
//   public String getHeading(){
//        return this.newsHeading;
//   }
//   public void setNewsHeading(String newsHeading){
//        this.newsHeading = newsHeading;
//   }
//   public String getNews(){
//        return this.news;
//   }
//   public void setNews(String news){
//        this.news=news;
//
//   }
//   public int getPersonId(){
//        return this.personId;
//   }
//   public void setPersonId(int id){
//        this.personId= id;
//   }
   public int getId(){
        return this.id;
   }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()){
            String saveNews= "INSERT INTO generalnews (newsHeading, news, personId) VALUES ( :newsheading, :news, :personid)";
            this.id = (int) con.createQuery(saveNews,true)
                    .addParameter("newsheading",this.newsHeading)
                    .addParameter("news", this.news)
                    .addParameter("personid", this.personId)
                    .executeUpdate()
                    .getKey();
        }catch (Sql2oException err){
            System.out.println("Error:::: "+ err);
        }

    }
    @Override
    public User findPostedBy(){
        try(Connection con = DB.sql2o.open()){
            String postedBy= "SELECT * FROM users WHERE id= :personId";
            return con.createQuery(postedBy).addParameter("personId", this.personId)
                    .executeAndFetchFirst(User.class);
        }
    }
    public static void deleteNews(int id){
        try(Connection con = DB.sql2o.open()){
            String deleteNews= "DELETE FROM generalnews WHERE id=:id";
            con.createQuery(deleteNews).addParameter("id", id)
            .executeUpdate();
        }

    }
    public static GeneralNews findGeneralNewsById(int id){
        try(Connection con = DB.sql2o.open()){
            String findById = "SELECT * FROM generalnews where id = :id";
            return  con.createQuery(findById)
                    .addParameter("id", id)
                    .executeAndFetchFirst(GeneralNews.class);
        }
    }
    public static List<GeneralNews> getAllGeneralNews(){
        try(Connection con = DB.sql2o.open()){
            String getAllGeneralNew="SELECT * FROM generalnews";
            return con.createQuery(getAllGeneralNew).executeAndFetch(GeneralNews.class);
        }
    }
    public static void clearAllGeneralNews(){
        try(Connection con = DB.sql2o.open()){
            String clearAllGeneralNews = "DELETE FROM generalnews *";
            con.createQuery(clearAllGeneralNews).executeUpdate();
        }
    }

}
