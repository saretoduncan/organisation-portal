package models;

import database_config.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.function.DoublePredicate;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    Department testDepartment;
    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/organisation_db_test","softwaredev","1234");
    }
    @BeforeEach
    public void setUp(){  //helper method
   testDepartment = new Department("Engineering", "Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.");

    }
    @AfterEach
    public void afterEach(){ // clear database after each test method
        try (Connection connection= DB.sql2o.open()){
            String clearDepartments= "DELETE FROM departments *;";
            connection.createQuery(clearDepartments).executeUpdate();


        }
    }
  @Test
    void department_instantiateCorrectly(){ // test department constructor instantiates correctly
      assertTrue(testDepartment instanceof Department);
  }
  @Test
    void InstanceOfGetters_true(){ // test instance of getters
        assertEquals("Engineering", testDepartment.getName());
        assertEquals("Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.",testDepartment.getDescription());;
  }
  @Test
  void testInstanceOfSetters_true(){ //test instance of setters
        testDepartment.setName("IT");
        testDepartment.setDescription("Responsible for all telecommunication services");
        assertEquals("IT", testDepartment.getName());
        assertEquals("Responsible for all telecommunication services", testDepartment.getDescription());
  }
  @Test
    void save_InstanceOfDepartments_true(){ //test instanceof save method that save data to database
        testDepartment.save();
        assertEquals(Department.getAll().get(0),testDepartment);
    }
    @Test
    void testInstanceOfIdGetter_true(){ // test instance of id getter
       testDepartment.save();
       assertEquals(Department.getAll().get(0).getId(), testDepartment.getId());
    }
    @Test
    void testInstanceOfDeleteDepartment_true(){ //test instance of delete department  method
        testDepartment.save();
        Department testDepartment1= new Department("IT","Responsible for all telecommunication services");
        testDepartment1.save();
        Department.deleteDepartment(testDepartment1.getId());
        assertEquals(Department.getAll().size(),1);
    }
  @Test
    void testInstanceOfFindById_true(){ //test instance of find by id method
        testDepartment.save();
        assertEquals(Department.findById(testDepartment.getId()),testDepartment);
  }
  @Test
    void testInstanceOfClearAll_true(){ // test instance of clear all data in the database method
        testDepartment.save();
      Department.clearAll();
        assertEquals(Department.getAll().size(),0);
  }


}