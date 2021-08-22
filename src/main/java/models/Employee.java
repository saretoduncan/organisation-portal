package models;

import java.util.Objects;

public class Employee {
    String name;
    String role;
    String position;
    int departmentId;

    int id;

    public Employee(String name, String position, String role, int departmentId){
        this.name= name;
        this.role = role;
        this.departmentId=departmentId;
        this.position= position;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return departmentId == employee.departmentId && id == employee.id && Objects.equals(name, employee.name) && Objects.equals(role, employee.role) && Objects.equals(position, employee.position);
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

}
