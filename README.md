# Spring Boot SQL Submission – Bajaj Finserv Health (JAVA)

👤 **Student**: Uday Kumar  
🎓 **Reg. No**: 22BCE20026  

---

## 📌 Overview
This Spring Boot app automates the submission for the **Java Qualifier**:

- On startup → generates a webhook & JWT.
- Picks SQL solution based on regNo:
  - **Odd RegNo → Q1** (highest salary employee).
  - **Even RegNo → Q2** (count younger employees in same department).
- Submits final SQL query back to API.

---

## 🚀 Run
```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
