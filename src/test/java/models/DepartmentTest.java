package models;

import database_config.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    Department testDepartment;
    @BeforeAll
    public static void beforeAll(){
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/organisation_db_test","softwaredev","1234");
    }
    @BeforeEach
    public void setUp(){
   testDepartment = new Department("Engineering", "Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.");

    }
    @AfterEach
    public void afterEach(){
        try (Connection connection= DB.sql2o.open()){
            String clearDepartments= "DELETE FROM departments *;";
            connection.createQuery(clearDepartments).executeUpdate();


        }
    }
  @Test
    void department_instantiateCorrectly(){
      assertTrue(testDepartment instanceof Department);
  }
  @Test
    void InstanceOfGettersAndSetter_true(){
        assertEquals("Engineering", testDepartment.getName());
        assertEquals("Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.",testDepartment.getDescription());
  }
  @Test
    void save_InstanceOfDepartments_true(){
        testDepartment.save();
        assertEquals(Department.getAll().get(0),testDepartment);
    }


}