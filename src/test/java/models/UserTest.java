package models;

import database_config.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    Department testDepartment;
    Employee testEmployee;

    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/organisation_db_test","softwaredev","1234");
    }
    @BeforeEach
    public void setUp(){  //helper method
        testDepartment = new Department("Engineering", "Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.");
       testDepartment.save();
        testEmployee = new Employee("Duncan","senior developer","manage projects",testDepartment.getId() );
    }
    @AfterEach
    public void afterEach(){ // clear database after each test method
        try (Connection connection= DB.sql2o.open()){
            String clearDepartments= "DELETE FROM departments *;";
            connection.createQuery(clearDepartments).executeUpdate();
            String clearEmployee= "DELETE FROM employee *;";
            connection.createQuery(clearEmployee).executeUpdate();


        }
    }
    @Test
    void setEmployee_instantiateCorrectly(){ // test department constructor instantiates correctly
        assertTrue(testDepartment instanceof Department);
    }
    @Test
    void InstanceOfGetters_true(){ // test instance of getters
        assertEquals("Duncan", testEmployee.getName());
        assertEquals("senior developer",testEmployee.getPosition());
        assertEquals("manage projects",testEmployee.getRole());
        assertEquals(testDepartment.getId(), testEmployee.getDepartmentId());
    }
    @Test
    void testInstanceOfSetters_true(){ //test instance of setters
        testEmployee.setName("Kelvin");
        testEmployee.setRole("Responsible for all telecommunication services");
        testEmployee.setPosition("Junior IT expert");
        assertEquals("Kelvin", testEmployee.getName());
        assertEquals("Responsible for all telecommunication services", testEmployee.getRole());
        assertEquals("Junior IT expert",testEmployee.getPosition());
    }
    @Test
    void save_InstanceOfEmployee_true(){ //test instanceof save method that save data to database
        testEmployee.save();
        assertEquals(Employee.getAllEmployees().get(0),testEmployee);
    }
    @Test
    void testInstanceOfIdGetter_true(){ // test instance of id getter
        testEmployee.save();
        assertEquals(Employee.getAllEmployees().get(0).getId(), testEmployee.getId());
    }
    @Test
    void testInstanceOfDeleteEmployee_true(){ //test instance of delete Employee,m  method
        testEmployee.save();
        Employee testEmployee1= new Employee("Kelvin","Junior IT expert","Responsible for all telecommunication services", testDepartment.getId());
        testEmployee1.save();
        Employee.deleteSingleEmployee(testEmployee.getId());
        assertEquals(Employee.getAllEmployees().size(),1);
    }
    @Test
    void testInstanceOfFindById_true(){ //test instance of find by id method
        testEmployee.save();
        assertEquals(Employee.findEmployeeById(testEmployee.getId()),testEmployee);
    }
    @Test
    void testInstanceOfClearAll_true(){ // test instance of clear all data in the database method
        testEmployee.save();
        Employee.clearAllEmployees();
        assertEquals(Employee.getAllEmployees().size(),0);
    }
    @Test
    void testInstanceOfFindDepartment_true(){// test instance of find employees department
        testEmployee.save();
        assertEquals(testDepartment.getName(),testEmployee.findDepartment().getName());
    }


}