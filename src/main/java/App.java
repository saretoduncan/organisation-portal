import com.google.gson.Gson;
import exceptions.ApiException;
import models.Department;
import models.DepartmentNews;
import models.GeneralNews;
import models.User;
import static spark.Spark.*;


public class App {
    public static void main(String[] args){
        Gson gson =new Gson();
        //create new department
        post("/department/new", "application/json",(request, response) ->{
            Department newDepartment = gson.fromJson(request.body(), Department.class);
            newDepartment.save();
            response.status(201);

            return gson.toJson(newDepartment);

        });
        // get all departments
        get("/departments","application/json", (request, response) -> {
             return gson.toJson(Department.getAll());
        });
        //get departmentById
        get("/departments/:departmentId","application/json",(request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            return gson.toJson(User.findEmployeeById(departmentId));
        });
        //delete single department
        post("/department/delete/:departmentId","application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            Department.deleteDepartment(departmentId);
            return  gson.toJson(Department.getAll());
        });
        //clearAllDepartments
        post("/departments/clearAllDepartment","application/json",(request, response) -> {
            Department.clearAll();
            return gson.toJson(Department.getAll());
        });
        //Create new user
        post("/user/:departmentId/new","application/json", (request, response) -> {
            int departmentId= Integer.parseInt(request.params("departmentId"));
            User newUser = gson.fromJson(request.body(),User.class);
            newUser.setDepartmentId(departmentId);
            newUser.save();
            response.status(201);
            return gson.toJson(newUser);

        });
        //view all users
        get("/users","application/json", (request, response) -> {
            return gson.toJson(User.getAllEmployees());
        });
        //find userById
        get("/user/:userId", "application/json", (request, response) -> {
            int userId= Integer.parseInt(request.params("userId"));
            return gson.toJson(User.findEmployeeById(userId));
        });
        //find User Department
        get("/user/department/:userId","application/json",(request, response) -> {
            int userId= Integer.parseInt(request.params("userId"));
            User currentUser= User.findEmployeeById(userId);
            return gson.toJson(currentUser.findDepartment());
        });
        //get all users in department
        get("/department/allusers/:departmentId", "application/json",(request, response) -> {
            int departmentId= Integer.parseInt(request.params("departmentId"));
            Department currentDepartment= Department.findById(departmentId);
            return gson.toJson(currentDepartment.getAllDepartmentEmployees());
        });
        //change users department
        post("/user/changedeparment/:userId/:departmentId", "application/json", (request, response) -> {
            int departmentId= Integer.parseInt(request.params("departmentId"));
            int userId= Integer.parseInt(request.params("userId"));
            User currentUser= User.findEmployeeById(userId);
            currentUser.changeDepartment(departmentId);
            response.status(201);
            return gson.toJson(currentUser);
        });
        // delete user
        post("/user/delete/:userId", "application/json", (request, response) -> {
            int userId= Integer.parseInt(request.params("userId"));
            User.deleteSingleEmployee(userId);
            return gson.toJson(User.getAllEmployees());
        });
        //clearAllUsers
        post("/users/deleteAll", "application/json", (request, response) -> {
               User.clearAllEmployees();
               return gson.toJson(User.getAllEmployees());
        });
        //create general news
        post("/general/news/:userId","application/json", (request, response) -> {
            int userId= Integer.parseInt(request.params("userId"));
            GeneralNews newGeneralNews= gson.fromJson(request.body(),GeneralNews.class);
            newGeneralNews.setPersonId(userId);
            newGeneralNews.save();
            response.status(201);
            return gson.toJson(newGeneralNews);
        } );
        //get All generalNews\
        get("/generalnews/all","application/json",(request, response) -> {
            return gson.toJson(GeneralNews.getAllGeneralNews());
        });
        //get specific general news
        get("/generalnews/:newsId","application/json",(request, response) -> {
            int newsId= Integer.parseInt(request.params("newsId"));
            return gson.toJson(GeneralNews.findGeneralNewsById(newsId));
        });
        //get posted by who
        get("/generalnew/postedby/:newsId","application/json",(request, response) -> {
            int newsId= Integer.parseInt(request.params("newsId"));
           GeneralNews currentNews= GeneralNews.findGeneralNewsById(newsId);
           return gson.toJson(currentNews.findPostedBy());
        });
        //Delete specific general news
        post("/generalnews/delete/:newsId","application/json", (request, response) -> {
            int newsId= Integer.parseInt(request.params("newsId"));

            response.status(201);
            return gson.toJson(GeneralNews.getAllGeneralNews());
        });
        //Delete all general news
        post("/generalnews/clearAll","application/json", (request, response) -> {
            GeneralNews.clearAllGeneralNews();
            return gson.toJson(GeneralNews.getAllGeneralNews());
        });
        //create department news
        post("/departmentnews/new/:userId/:departmentId", "application/json", (request, response) -> {
            int userId= Integer.parseInt(request.params("userId"));
            int departmentId = Integer.parseInt(request.params("departmentId"));
            DepartmentNews newDepartmentNews = gson.fromJson(request.body(),DepartmentNews.class);
            newDepartmentNews.setDepartmentId(departmentId);
            newDepartmentNews.setPersonId(userId);
            newDepartmentNews.save();
            response.status(201);
            return gson.toJson(newDepartmentNews);
        });
        //get all departmentNews
        get("/departmentnews","application/json", (request, response) -> {
            return gson.toJson(DepartmentNews.getAllDepartmentNews());
        });
        //get specific department news
        get("/departmentnews/:newsId", "application/json", (request, response) -> {
            int newsId= Integer.parseInt(request.params("newsId"));
            return gson.toJson(DepartmentNews.findDepartmentNewsById(newsId));
        });
        //get posted by who
        get("/departmentNews/postedbywho/:newsId", "application/json", (request, response) -> {
            int newsId= Integer.parseInt(request.params("newsId"));
            DepartmentNews currentNews = DepartmentNews.findDepartmentNewsById(newsId);
            return gson.toJson(currentNews.findPostedBy());
        });
        //delete specific department news
        post("/departmentnews/delete/:newsId", "application/json", (request, response) -> {
            int newsId= Integer.parseInt(request.params("newsId"));
            Department.deleteDepartment(newsId);
            response.status(201);
            return gson.toJson(DepartmentNews.getAllDepartmentNews());
        });
        //clear all department news
        post("/departmentnews/clearall","application/json", (request, response) -> {
            DepartmentNews.clearAllDepartmentNews();
            return gson.toJson(DepartmentNews.getAllDepartmentNews());
        });

        //
        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });
    }
}
