package models;

import database_config.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentNewsTest {
    Department testDepartment;
    User testUser;
    DepartmentNews testDepartmentNews;


    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/organisation_db_test","softwaredev","1234");
    }
    @BeforeEach
    public void setUp(){  //helper method
        testDepartment = new Department("Engineering", "Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.");
        testDepartment.save();
        testUser = new User("Duncan","senior developer","manage projects",testDepartment.getId() );
        testUser.save();
        testDepartmentNews = new DepartmentNews("Department Offices", "All Departments will be allocated there own offices",testUser.getId(),testDepartment.getId());

    }
    @AfterEach
    public void afterEach(){ // clear database after each test method
        try (Connection connection= DB.sql2o.open()){
            String clearDepartments= "DELETE FROM departments *;";
            connection.createQuery(clearDepartments).executeUpdate();
            String clearEmployee= "DELETE FROM users *;";
            connection.createQuery(clearEmployee).executeUpdate();
            String clearAllNews= "DELETE FROM departmentnews *";
            connection.createQuery(clearAllNews).executeUpdate();


        }
    }
    @Test
    void setEmployee_instantiateCorrectly(){ // test department constructor instantiates correctly
        assertTrue(testDepartmentNews instanceof DepartmentNews);
    }
    @Test
    void InstanceOfGetters_true(){ // test instance of getters
        assertEquals("Department Offices", testDepartmentNews.getHeading());
        assertEquals("All Departments will be allocated there own offices", testDepartmentNews.getNews());

        assertEquals(testUser.getId(), testDepartmentNews.getPersonId());
    }
    @Test
    void testInstanceOfSetters_true(){ //test instance of setters
       testDepartmentNews.setNewsHeading("Report Time");
        testDepartmentNews.setNews("Every one should report to work by 7:30am");

        assertEquals("Report Time", testDepartmentNews.getHeading());
        assertEquals("Every one should report to work by 7:30am", testDepartmentNews.getNews());

    }
    @Test
    void save_InstanceOfGeneralNews_true(){ //test instanceof save method that save data to database
        testDepartmentNews.save();
        assertEquals(DepartmentNews.getAllDepartmentNews().get(0), testDepartmentNews);
    }
    @Test
    void testInstanceOfIdGetter_true(){ // test instance of id getter
        testDepartmentNews.save();
        assertEquals(DepartmentNews.getAllDepartmentNews().get(0).getId(), testDepartmentNews.getId());
    }
    @Test
    void testInstanceOfDeleteGeneralNews_true(){ //test instance of delete news  method
        testDepartmentNews.save();
        DepartmentNews testDepartmentNew1= new DepartmentNews("Report Time","Every one should report to work by 7:30am",testUser.getId(),testDepartment.getId());
        testDepartmentNew1.save();
        DepartmentNews.deleteSingleInstanceOfDepartmentNews(testDepartmentNew1.getId());
        assertEquals(DepartmentNews.getAllDepartmentNews().size(),1);
    }
    @Test
    void testInstanceOfFindById_true(){ //test instance of find by id method
        testDepartmentNews.save();
        assertEquals(DepartmentNews.findDepartmentNewsById(testDepartmentNews.getId()),testDepartmentNews);
    }
    @Test
    void testInstanceOfClearAll_true(){ // test instance of clear all data in the table method
      testDepartmentNews.save();
       DepartmentNews.clearAllDepartmentNews();
        assertEquals(DepartmentNews.getAllDepartmentNews().size(),0);
    }
    @Test
    void testInstanceOfFindPostedBy_true(){// test instance of news posted by.
        testDepartmentNews.save();
        System.out.println(testDepartmentNews.findPostedBy().getName());
        assertEquals(testDepartmentNews.findPostedBy().getName(), testUser.getName());
    }



}