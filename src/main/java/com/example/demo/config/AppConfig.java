# Server Configuration
server.port=8080

# Database Configuration (Example: MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Settings (Custom properties used in your JwtUtils)
app.jwt.secret=YourSuperSecretKeyThatIsVeryLongAndSecure123456
app.jwt.expiration-ms=86400000

# Swagger UI Path
springdoc.swagger-ui.path=/swagger-ui.html