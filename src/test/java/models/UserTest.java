package models;

import database_config.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    Department testDepartment;
    User testUser;

    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/organisation_db_test","softwaredev","1234");
    }
    @BeforeEach
    public void setUp(){  //helper method
        testDepartment = new Department("Engineering", "Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.");
       testDepartment.save();
        testUser = new User("Duncan","senior developer","manage projects",testDepartment.getId() );
    }
    @AfterEach
    public void afterEach(){ // clear database after each test method
        try (Connection connection= DB.sql2o.open()){
            String clearDepartments= "DELETE FROM departments *;";
            connection.createQuery(clearDepartments).executeUpdate();
            String clearEmployee= "DELETE FROM users *;";
            connection.createQuery(clearEmployee).executeUpdate();


        }
    }
    @Test
    void setEmployee_instantiateCorrectly(){ // test department constructor instantiates correctly
        assertTrue(testDepartment instanceof Department);
    }
    @Test
    void InstanceOfGetters_true(){ // test instance of getters
        assertEquals("Duncan", testUser.getName());
        assertEquals("senior developer", testUser.getPosition());
        assertEquals("manage projects", testUser.getRole());
        assertEquals(testDepartment.getId(), testUser.getDepartmentId());
    }
    @Test
    void testInstanceOfSetters_true(){ //test instance of setters
        testUser.setName("Kelvin");
        testUser.setRole("Responsible for all telecommunication services");
        testUser.setPosition("Junior IT expert");
        assertEquals("Kelvin", testUser.getName());
        assertEquals("Responsible for all telecommunication services", testUser.getRole());
        assertEquals("Junior IT expert", testUser.getPosition());
    }
    @Test
    void save_InstanceOfEmployee_true(){ //test instanceof save method that save data to database
        testUser.save();
        assertEquals(User.getAllEmployees().get(0), testUser);
    }
    @Test
    void testInstanceOfIdGetter_true(){ // test instance of id getter
        testUser.save();
        assertEquals(User.getAllEmployees().get(0).getId(), testUser.getId());
    }
    @Test
    void testInstanceOfDeleteEmployee_true(){ //test instance of delete Employee,m  method
        testUser.save();
        User testEmployee1= new User("Kelvin","Junior IT expert","Responsible for all telecommunication services", testDepartment.getId());
        testEmployee1.save();
        User.deleteSingleEmployee(testUser.getId());
        assertEquals(User.getAllEmployees().size(),1);
    }
    @Test
    void testInstanceOfFindById_true(){ //test instance of find by id method
        testUser.save();
        assertEquals(User.findEmployeeById(testUser.getId()), testUser);
    }
    @Test
    void testInstanceOfClearAll_true(){ // test instance of clear all data in the database method
        testUser.save();
        User.clearAllEmployees();
        assertEquals(User.getAllEmployees().size(),0);
    }
    @Test
    void testInstanceOfFindDepartment_true(){// test instance of find employees department
        testUser.save();
        assertEquals(testDepartment.getName(), testUser.findDepartment().getName());
    }


}