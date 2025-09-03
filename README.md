# Chit Fund Management System

A Spring Boot application for managing chit fund operations including user management, group creation, member management, and auction/bidding functionality.

## Technology Stack

- **Framework**: Spring Boot 2.5.14
- **Java Version**: 17
- **Database**: PostgreSQL
- **Authentication**: JWT-based security
- **Build Tool**: Maven
- **Documentation**: Swagger/OpenAPI

## Project Structure

```
com.chitfund/
├── user/           # User management and authentication
├── chitGroups/     # Chit fund group management
├── members/        # Member registration and management
├── auction/        # Bidding and auction functionality
├── menu/           # Dynamic menu system
├── security/       # JWT security configuration
└── util/           # Utilities and exception handling
```

## Key Features

- User registration and JWT-based authentication
- Chit fund group creation and management
- Member onboarding with KYC management
- Real-time auction/bidding system using WebSockets
- Role-based access control
- RESTful API with Swagger documentation

## Recent Code Review and Improvements

### Issues Fixed

1. **Critical Bug Fix**: Fixed NullPointerException in `AuctionSchedulerService`
   - Was trying to iterate over null list causing runtime failure
   - Implemented proper auction filtering logic

2. **Security Improvements**:
   - Created example configuration file with security warnings
   - Added .gitignore entries to prevent committing sensitive data
   - Documented need for stronger JWT secret keys

3. **Code Quality Improvements**:
   - Fixed inconsistent logging prefixes (EPG_US_ -> CFM_USI_)
   - Enabled commented-out auction functionality
   - Added proper validation to Member controller
   - Improved error handling consistency in GlobalExceptionHandler

4. **Memory Leak Prevention**:
   - Fixed AuctionManager to use shared ScheduledExecutorService
   - Added proper cleanup and shutdown methods
   - Improved logging and error handling

### Security Considerations

⚠️ **Important Security Notes**:

1. **JWT Secret**: The default JWT secret is weak. Use a strong 256-bit key for production.
2. **Database Credentials**: Never commit actual database credentials to version control.
3. **CORS Configuration**: Currently allows all origins (`*`) - restrict for production.
4. **Input Validation**: Ensure all endpoints have proper validation.

### Configuration Setup

1. Copy `application-example.properties` to `src/main/resources/application.properties`
2. Update database credentials and JWT secret
3. Configure appropriate CORS settings for your environment

### Running the Application

```bash
# Start PostgreSQL database
# Update application.properties with correct database settings

# Run the application
./mvnw spring-boot:run
```

The application will start on port 8075 with context path `/CFM/v1`.

### API Documentation

Access Swagger UI at: `http://localhost:8075/CFM/v1/swagger-ui/`

### Testing

⚠️ **Note**: Tests currently require a running PostgreSQL database. Consider implementing test containers or H2 in-memory database for unit tests.

## Recommended Next Steps

1. **Add Comprehensive Testing**:
   - Unit tests for service layers
   - Integration tests for controllers
   - Test database configuration

2. **Security Enhancements**:
   - Implement proper JWT secret key management
   - Add rate limiting
   - Enhance input validation and sanitization

3. **Business Logic Completion**:
   - Complete auction repository queries
   - Add transaction management
   - Implement payment processing

4. **Monitoring and Logging**:
   - Add application metrics
   - Implement structured logging
   - Add health check endpoints

## Contributing

1. Follow the existing code patterns and logging conventions
2. Add proper validation to all endpoints
3. Include appropriate error handling
4. Update tests for new functionality
5. Document any configuration changes

## License

[Add your license information here]