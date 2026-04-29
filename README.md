# F1 Garage - Formula 1 Cars Web Application

A complete Spring Boot web application for managing Formula 1 cars and teams with a modern, responsive dark racing theme.

## Features

- **Dark Racing Theme**: Red accent colors, modern UI inspired by F1 aesthetics
- **Car Management**: Add, edit, delete cars with image uploads
- **Team Management**: Organize cars by teams
- **Search & Filter**: Search by model, filter by season and team
- **REST API**: Full REST endpoints at `/api/cars`
- **Bean Validation**: Input validation with error messages
- **Pagination & Sorting**: For car listings
- **Logging**: SLF4J logging with file and console output
- **Profile Support**: Dev (H2) and Prod (PostgreSQL) profiles

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Validation
- Thymeleaf
- H2 Database (dev) / PostgreSQL (prod)
- Lombok
- Maven

## Project Structure

```
com.f1garage.f1cars/
├── car/           # Car entity, repository, service, controller
├── team/          # Team entity, repository, service, controller
├── controller/    # HomeController, ContactController
├── rest/          # REST API controllers
├── service/       # Business logic
├── dto/           # Data Transfer Objects
├── config/        # AppProperties, WebConfig, GlobalModelAttributes
├── exception/     # Custom exceptions and global handler
└── F1CarsApplication.java
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- (Optional) PostgreSQL for production profile

### Running the Application

**Development (H2 Database):**
```bash
mvn spring-boot:run
```

**Production (PostgreSQL):**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

The application will start on `http://localhost:8080`

### Access Points

- **Home**: http://localhost:8080/
- **Cars**: http://localhost:8080/cars
- **Teams**: http://localhost:8080/teams
- **Contact**: http://localhost:8080/contact
- **H2 Console** (dev): http://localhost:8080/h2-console
- **REST API**: http://localhost:8080/api/cars

### H2 Console Settings

- JDBC URL: `jdbc:h2:file:./f1cars`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/cars | List all cars |
| GET | /api/cars/{id} | Get car by ID |
| POST | /api/cars | Create new car |
| PUT | /api/cars/{id} | Update car |
| DELETE | /api/cars/{id} | Delete car |

## Configuration

### Application Properties

Key settings in `application.properties`:

- `app.title` - Application title
- `app.upload-dir` - Upload directory for car images
- `logging.file.name` - Log file location

### Profiles

- **dev**: Uses H2 in-memory database
- **prod**: Uses PostgreSQL (configure credentials)

## Screenshots

- Hero banner with F1 racing theme
- Dark cards with red accents
- Responsive grid layout
- Form validation with error messages

## License

This project is for educational purposes.
