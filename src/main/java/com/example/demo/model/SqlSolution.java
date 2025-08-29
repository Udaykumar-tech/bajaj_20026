package com.example.demo.model;

public class SqlSolution {

    // Question 1 SQL (Odd regNo → Q1)
    public static final String Q1_QUERY = "SELECT e.EMP_ID, " +
            "e.FIRST_NAME, " +
            "e.LAST_NAME, " +
            "d.DEPARTMENT_NAME, " +
            "SUM(p.AMOUNT) AS TOTAL_SALARY " +
            "FROM EMPLOYEE e " +
            "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
            "JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID " +
            "GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME " +
            "HAVING SUM(p.AMOUNT) = ( " +
            "   SELECT MAX(total_salary) " +
            "   FROM (SELECT SUM(AMOUNT) AS total_salary FROM PAYMENTS GROUP BY EMP_ID) sub)";

    // Question 2 SQL (Even regNo → Q2)
    public static final String Q2_QUERY = "SELECT e1.EMP_ID, " +
            "e1.FIRST_NAME, " +
            "e1.LAST_NAME, " +
            "d.DEPARTMENT_NAME, " +
            "COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
            "FROM EMPLOYEE e1 " +
            "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
            "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT " +
            "AND e2.DOB > e1.DOB " +
            "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
            "ORDER BY e1.EMP_ID DESC";

    // Method to decide which query to pick
    public static String getSolution(String regNo) {
        int lastTwoDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));
        return (lastTwoDigits % 2 == 0) ? Q2_QUERY : Q1_QUERY;
    }
}
