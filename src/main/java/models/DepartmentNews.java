package models;

import database_config.DB;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class DepartmentNews extends News {
 private int id;
 private int departmentId;
 public DepartmentNews(String newsHeading, String news, int personId, int departmentId){
     this.newsHeading= newsHeading;
     this.news= news;
     this.personId= personId;
     this.departmentId= departmentId;
 }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepartmentNews that = (DepartmentNews) o;
        return id == that.id && departmentId == that.departmentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, departmentId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
   public void save(){
     try(Connection con= DB.sql2o.open()){
         String departmentNewsSave= "INSERT INTO departmentnews (newsHeading, news, personId, departmentId) VALUES (:newsheading, :news, :personid, :departmentid)";
         this.id= (int) con.createQuery(departmentNewsSave, true)
                 .addParameter("newsheading", this.newsHeading)
                 .addParameter("news", this.news)
                 .addParameter("personid", this.personId)
                 .addParameter("departmentid", this.departmentId)
                 .executeUpdate()
                 .getKey();
     } catch(Sql2oException err){
         System.out.println("Error::" +err);
     }

    }
    @Override
   public User findPostedBy(){
        try(Connection con= DB.sql2o.open()){
            String postedBy= "SELECT * FROM users WHERE id = :personId";
            return con.createQuery(postedBy)
                    .addParameter("personId", this.personId)
                    .executeAndFetchFirst(User.class);
        }

    }
    public static List<DepartmentNews> getAllDepartmentNews(){
        try(Connection con= DB.sql2o.open()){
            String getAllNews= "SELECT * FROM departmentnews";
            return  con.createQuery(getAllNews)
                    .executeAndFetch(DepartmentNews.class);
        }
    }
    public static void deleteSingleInstanceOfDepartmentNews(int id){
        try(Connection con= DB.sql2o.open()){
            String deleteSingleNews= "DELETE FROM departmentnews WHERE id= :id";
            con.createQuery(deleteSingleNews).addParameter("id", id)
                    .executeUpdate();
        }
    }
    public static DepartmentNews findDepartmentNewsById(int id){
        try(Connection con= DB.sql2o.open()){
            String findNewById= "SELECT * FROM departmentnews WHERE id = :id";
            return con.createQuery(findNewById).addParameter("id",id).executeAndFetchFirst(DepartmentNews.class);
        }
    }
    public static void clearAllDepartmentNews(){
        try(Connection con= DB.sql2o.open()){
            String clearAllNews= "DELETE FROM departmentnews *";
            con.createQuery(clearAllNews).executeUpdate();
        }
    }
}


