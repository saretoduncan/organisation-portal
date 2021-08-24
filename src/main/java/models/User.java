package models;

import database_config.DB;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class User {
    String name;
    String role;
    String position;
    int departmentId;

    int id;

    public User(String name, String position, String role, int departmentId){
        this.name= name;
        this.role = role;
        this.departmentId=departmentId;
        this.position= position;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return departmentId == user.departmentId && id == user.id && Objects.equals(name, user.name) && Objects.equals(role, user.role) && Objects.equals(position, user.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role, position, departmentId, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public  void save(){
        try (Connection con = DB.sql2o.open()){
          String sql = "INSERT INTO users (name, role, position, departmentId) VALUES (:name, :role, :position, :departmentId);";
             this.id= (int) con.createQuery(sql,true)
                     .addParameter("name", this.name)
                     .addParameter("role", this.role)
                     .addParameter("position", this.position)
                     .addParameter("departmentId", this.departmentId)
                     .executeUpdate()
                     .getKey();
        } catch (Sql2oException er){
            System.out.println("Error ::: "+ er);
        }
    }
    public void changeDepartment(int departmentId){
        try(Connection con = DB.sql2o.open()) {
            String changeDepartment = "UPDATE users SET departmentid = :departmentId WHERE id = :id";

            con.createQuery(changeDepartment)
                    .addParameter("departmentId", departmentId)
                    .addParameter("id", this.id)
                    .executeUpdate();
            this.departmentId=departmentId;
        }catch (Sql2oException err){
            System.out.println("Error ::: " +err);
        }
    }
    public Department findDepartment(){
        try (Connection con = DB.sql2o.open()){
            String findDepartment= "SELECT * FROM departments where id = :departmentId ";
            return con.createQuery(findDepartment)
                    .addParameter("departmentId", this.departmentId)
                    .executeAndFetchFirst(Department.class);
        }
    }
public  static List<User> getAllEmployees() {
    try (Connection con = DB.sql2o.open()) {
        String getAll = "SELECT * FROM users;";
        return con.createQuery(getAll).executeAndFetch(User.class);

    }

}
    public static void deleteSingleEmployee(int id){
        try (Connection con = DB.sql2o.open()){
            String delete = "DELETE FROM users WHERE id= :id";
            con.createQuery(delete)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException err){
            System.out.println("Error::: "+ err);
        }
    }
    public static User findEmployeeById(int id){
        try (Connection con = DB.sql2o.open()){
            String findById = "SELECT * FROM users WHERE id = :id";
            return con.createQuery(findById).addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }
    public static void clearAllEmployees(){
        try (Connection con = DB.sql2o.open()) {
        String clearAll= "DELETE FROM users *";
         con.createQuery(clearAll).executeUpdate();
        }catch (Sql2oException err){
            System.out.println("Error::: "+ err);
        }

    }
}

