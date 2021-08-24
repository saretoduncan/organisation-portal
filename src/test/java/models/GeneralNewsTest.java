package models;

import database_config.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.lang.reflect.GenericDeclaration;

import static org.junit.jupiter.api.Assertions.*;

class GeneralNewsTest {
    Department testDepartment;
    User testUser;
    GeneralNews testGeneralNews;

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
        testGeneralNews = new GeneralNews("Department Offices", "All Departments will be allocated there own offices",testUser.getId());

    }
    @AfterEach
    public void afterEach(){ // clear database after each test method
        try (Connection connection= DB.sql2o.open()){
            String clearDepartments= "DELETE FROM departments *;";
            connection.createQuery(clearDepartments).executeUpdate();
            String clearEmployee= "DELETE FROM users *;";
            connection.createQuery(clearEmployee).executeUpdate();
            String clearAllGeneralNews = "DELETE FROM generalnews *";
            connection.createQuery(clearAllGeneralNews).executeUpdate();


        }
    }
    @Test
    void setEmployee_instantiateCorrectly(){ // test department constructor instantiates correctly
        assertTrue(testGeneralNews instanceof GeneralNews);
    }
    @Test
    void InstanceOfGetters_true(){ // test instance of getters
        assertEquals("Department Offices", testGeneralNews.getHeading());
        assertEquals("All Departments will be allocated there own offices", testGeneralNews.getNews());
        assertEquals("manage projects", testUser.getRole());
        assertEquals(testUser.getId(), testGeneralNews.getPersonId());
    }
    @Test
    void testInstanceOfSetters_true(){ //test instance of setters
        testGeneralNews.setNewsHeading("Report Time");
      testGeneralNews.setNews("Every one should report to work by 7:30am");

        assertEquals("Report Time", testGeneralNews.getHeading());
        assertEquals("Every one should report to work by 7:30am", testGeneralNews.getNews());

    }
    @Test
    void save_InstanceOfGeneralNews_true(){ //test instanceof save method that save data to database
       testGeneralNews.save();
        assertEquals(GeneralNews.getAllGeneralNews().get(0), testGeneralNews);
    }
    @Test
    void testInstanceOfIdGetter_true(){ // test instance of id getter
        testGeneralNews.save();
        assertEquals(GeneralNews.getAllGeneralNews().get(0).getId(),testGeneralNews.getId());
    }
    @Test
    void testInstanceOfDeleteGeneralNews_true(){ //test instance of delete news  method
        testGeneralNews.save();
        GeneralNews testGeneralNew1= new GeneralNews("Report Time","Every one should report to work by 7:30am",testUser.getId());
        testGeneralNew1.save();
        GeneralNews.deleteNews(testGeneralNew1.getId());
        assertEquals(GeneralNews.getAllGeneralNews().size(),1);
    }
    @Test
    void testInstanceOfFindById_true(){ //test instance of find by id method
        testGeneralNews.save();
        assertEquals(GeneralNews.findGeneralNewsById(testGeneralNews.getId()),testGeneralNews);
    }
    @Test
    void testInstanceOfClearAll_true(){ // test instance of clear all data in the table method
        testGeneralNews.save();
        GeneralNews.clearAllGeneralNews();
        assertEquals(GeneralNews.getAllGeneralNews().size(),0);
    }
    @Test
    void testInstanceOfFindPostedBy_true(){// test instance of news posted by.
        testGeneralNews.save();
        System.out.println(testGeneralNews.findPostedBy().getName());
        assertEquals(testGeneralNews.findPostedBy().getName(), testUser.getName());
    }


}