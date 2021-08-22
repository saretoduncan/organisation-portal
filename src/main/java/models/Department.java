package models;

import database_config.DB;
import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;
import java.util.function.DoublePredicate;

public class Department {
    private String name;
    private String description;
    private int id;
    public Department(String name, String description){
        this.name= name;
        this.description= description;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void  save(){
        try(Connection connection= DB.sql2o.open()){
            String sql = "INSERT INTO departments (name, description) VALUES (:name, :description); ";
            this.id=(int) connection.createQuery(sql,true)
                    .addParameter("name", this.name)
                    .addParameter("description", this.description)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Department> getAll(){
        try(Connection connection= DB.sql2o.open()){
            String getAll= "SELECT * FROM departments;";
            return  connection.createQuery(getAll).executeAndFetch(Department.class);
        }
    }
    public static Department findById(int id){
        try(Connection connection= DB.sql2o.open()){
            String findDepartment= "SELECT * FROM departments WHERE id = :id";
            return connection.createQuery(findDepartment)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Department.class);
        }
    }
    public static void deleteDepartment(int id){
        try (Connection connection= DB.sql2o.open()){
            String deleteSingleInstance = "DELETE FROM departments WHERE id = :id";
            connection.createQuery(deleteSingleInstance)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
    public static void clearAll(){
        try (Connection connection= DB.sql2o.open()){
            String deleteAllInstances= "DELETE FROM departments *;";
            connection.createQuery(deleteAllInstances).executeUpdate();
        }
    }
}
