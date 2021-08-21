package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    Department testDepartment;
    @BeforeEach
    public void setUp(){
   testDepartment = new Department("Engineering", "Responsible for defining the configuration of the product and produce the drawing and specification and also responsible to modify the configuration as needed.");

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

}