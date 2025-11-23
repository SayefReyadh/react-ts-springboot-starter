# Local Development Setup Guide

This guide will help you run the React TypeScript + Spring Boot starter project on your local machine.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+**: Required for running the Spring Boot backend
- **Maven 3.6+**: Build tool for the backend
- **Node.js 18+**: Required for the React frontend
- **npm**: Comes with Node.js

### Installing Prerequisites on macOS

```bash
# Install Java 17 and Maven using Homebrew
brew install openjdk@17 maven

# Add Java 17 to your PATH (add this to your ~/.zshrc or ~/.bash_profile)
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"

# Verify installations
java -version    # Should show Java 17
mvn -version     # Should show Maven 3.6+
node -version    # Should show Node.js 18+
npm -version     # Should show npm version
```

### Installing Prerequisites on Linux

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk maven nodejs npm

# Verify installations
java -version
mvn -version
node -version
npm -version
```

### Installing Prerequisites on Windows

1. Download and install [Java 17 JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. Download and install [Maven](https://maven.apache.org/download.cgi)
3. Download and install [Node.js](https://nodejs.org/) (includes npm)
4. Add Java and Maven to your system PATH
5. Verify installations in Command Prompt

## Initial Setup (First Time Only)

### 1. Install Frontend Dependencies

```bash
cd frontend
npm install
cd ..
```

### 2. Build the Backend

```bash
cd backend
mvn clean package -DskipTests
cd ..
```

## Running the Application

You'll need to run both the backend and frontend servers. Open two terminal windows:

### Terminal 1: Start the Backend Server

```bash
cd backend
mvn spring-boot:run
```

The backend will start on **http://localhost:8080**

You should see output similar to:
```
Started DemoApplication in X.XXX seconds
```

### Terminal 2: Start the Frontend Development Server

```bash
cd frontend
npm run dev
```

The frontend will start on **http://localhost:3000**

You should see output similar to:
```
VITE v5.x.x  ready in XXX ms
➜  Local:   http://localhost:3000/
```

## Accessing the Application

1. Open your browser and navigate to **http://localhost:3000**
2. You should see the message: **"Backend says: Hello World"**

This confirms that:
- The frontend is running correctly
- The frontend can communicate with the backend
- The backend API is working

## Verifying the Backend API

You can test the backend API directly:

```bash
curl http://localhost:8080/api/hello
```

Expected response:
```json
{"message":"Hello World"}
```

## Common Issues and Solutions

### Port Already in Use

If you see errors about ports 8080 or 3000 being in use:

```bash
# Find and kill the process using port 8080 (backend)
lsof -i :8080
kill -9 <PID>

# Find and kill the process using port 3000 (frontend)
lsof -i :3000
kill -9 <PID>
```

### Frontend Can't Connect to Backend

1. Ensure the backend is running on port 8080
2. Check the proxy configuration in `frontend/vite.config.ts`
3. Verify CORS is enabled in `backend/src/main/java/com/example/demo/controller/HelloController.java`

### Maven Build Fails

```bash
# Try cleaning and rebuilding
cd backend
mvn clean
mvn install -DskipTests
```

### npm Install Fails

```bash
# Clear npm cache and try again
cd frontend
rm -rf node_modules package-lock.json
npm cache clean --force
npm install
```

### Java Version Mismatch

Ensure you're using Java 17:

```bash
# Check your Java version
java -version

# If you have multiple Java versions, set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 17)  # macOS
```

## Development Workflow

### Making Changes

**Backend Changes:**
- Edit files in `backend/src/main/java/`
- Spring Boot will automatically restart (if using Spring Boot DevTools)
- Otherwise, stop the server (Ctrl+C) and restart with `mvn spring-boot:run`

**Frontend Changes:**
- Edit files in `frontend/src/`
- Vite will automatically reload the page (Hot Module Replacement)

### Running Tests

**Backend:**
```bash
cd backend
mvn test
```

**Frontend:**
```bash
cd frontend
npm test  # If tests are configured
```

## Building for Production

### Backend

```bash
cd backend
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The JAR file will be created in `backend/target/`

### Frontend

```bash
cd frontend
npm run build
npm run preview  # Preview the production build
```

The production files will be in `frontend/dist/`

## Project Structure

```
react-ts-springboot-starter/
├── backend/                 # Spring Boot application
│   ├── src/main/java/      # Java source code
│   ├── src/main/resources/ # Application properties
│   ├── pom.xml             # Maven configuration
│   └── target/             # Build output
│
├── frontend/               # React + TypeScript app
│   ├── src/                # React source code
│   ├── package.json        # npm configuration
│   ├── vite.config.ts      # Vite configuration with API proxy
│   └── node_modules/       # npm dependencies
│
└── LOCAL_SETUP.md          # This file
```

## API Endpoints

### Current Endpoints

- `GET /api/hello` - Returns `{"message":"Hello World"}`

### Adding New Endpoints

Create new controllers in:
```
backend/src/main/java/com/example/demo/controller/
```

## Environment Variables

### Frontend

Create `frontend/.env` for environment-specific configuration:

```env
VITE_API_URL=http://localhost:8080
```

### Backend

Edit `backend/src/main/resources/application.properties`:

```properties
server.port=8080
```

## Next Steps

- Add database support with Spring Data JPA
- Implement authentication and authorization
- Add more React components and routes
- Set up CI/CD pipeline
- Configure production deployment

## Need Help?

- Check the main [README.md](README.md) for more information
- Review the Spring Boot documentation: https://spring.io/projects/spring-boot
- Review the React documentation: https://react.dev
- Review the Vite documentation: https://vitejs.dev
