========================================================================
                   Project: Gmail Clone
========================================================================
------------------------------------------------------------------------
Table of Contents
------------------------------------------------------------------------
1. Introduction
2. Tech Stack
3. Key Functionalities
4. Validations
5. Database Design
6. Getting Started
7. Folder Structure
8. Running the Application
-----------------------------------------------------------------------
1. Introduction
-----------------------------------------------------------------------
This project aims to create a Gmail-like web application,
complete with user authentication, email sending and receiving,
profile management, and more. This README provides an overview
of the project and instructions on how to set it up and run it locally.
-----------------------------------------------------------------------
2. Tech Stack
-----------------------------------------------------------------------
FrontEnd:
- HTML/CSS/JS
- Vue.js
- Axios
- Vue Router
- Vue CLI
BackEnd:
- Java (JRE, JDK, JVM)
- Spring Boot (v3.1.3)
Dependencies:
- Spring Web
- Spring Data JPA
Database:
- SQL (RDBMS)
- PostgreSQL
Tools:
- Eclipse IDE
- Visual Studio Code
- Postman
- Maven
- Tomcat
- GIT
- npm, npx
------------------------------------------------------------------------
3. Key Functionalities
------------------------------------------------------------------------
a) **SignUp and SignIn:**
   - Users can sign up for an account.
   - Existing users can sign in.
b) **Forgot Password:**
   - Users can reset their password if forgotten.
c) **Profile Section:**
   - Users have a profile section with options to change their password,
     update their profile picture, and sign out.
d) **Email Functionality:**
   - Users can send emails to single or multiple recipients.
   - Receive failure notifications for non-existent email recipients.
   - View received emails in their inbox.
   - Unread emails are displayed in bold.
e) **Email Management:**
   - Users can delete single/multiple emails.
   - Create new folders and organize important emails.
-------------------------------------------------------------------------
4. Validations
-------------------------------------------------------------------------
a) **SignUp Validation:**
   - Checks email availability while typing.
   - Green text indicates the email is available.
   - Red text indicates the email is taken.
   - Proper email format validation.
   - Validation for all other signup fields.
b) **SignIn Validation:**
   - Validation for all signin fields.
c) **Change Password Validation:**
   - Validation for all fields in the change password form.
-------------------------------------------------------------------------
5. Database Design
-------------------------------------------------------------------------
The application uses a relational database to store user information and
emails. Below is the database design with tables, fields, and relationships:
Table: users
- `email` (Primary Key, Unique): Stores the user's email address, used as a unique identifier.
- `firstName`: Stores the user's first name.
- `lastName`: Stores the user's last name.
- `gender`: Stores the user's gender.
- `password`: Stores the user's password.
- `mobileNo`: Stores the user's mobile phone number.
- `profilePicture`: Stores the user's profile picture as binary data.
- `emails` (One-to-Many Relationship with `Email`): Represents the emails sent or received by the user.
Table: emails
- `id` (Primary Key): Auto-generated unique identifier for each email.
- `senderId`: Stores the sender's email address.
- `subject`: Stores the subject of the email.
- `body`: Stores the body/content of the email.
- `sentDate`: Stores the date and time when the email was sent.
- `isRead`: Indicates whether the email has been read.
- `isImportant`: Indicates whether the email is marked as important.
- `isDeleted`: Indicates whether the email has been deleted.
- `recipientIds`: Stores the email addresses of recipients.
- `type`: Stores the type of email (e.g., inbox, sent,trash items, etc.).
- `user` (Many-to-One Relationship with `User`): Links the email to the user who sent or received it.
This database design allows for the storage and retrieval of user data and emails, including their relationships.
Please ensure that your database schema aligns with this design for the application to function correctly.
-------------------------------------------------------------------------
6. Getting Started
-------------------------------------------------------------------------
To get started with this project, follow these steps:
-------------------------------------------------------------------------
7. Folder Structure
-------------------------------------------------------------------------
Folder Structure of Spring Boot Application for Project:
-------------------------------------------------------------------------
The project follows a well-organized folder structure for ease of development
and maintenance. Below is an outline of the main directories and their contents:
- **src/main/java**: This directory contains all the Java source code for the project.
    - `com.gmail`: This is the root package for the Java code.
        - `controller`: Contains controllers for handling HTTP requests and responses.
        - `model`: Contains entity classes (e.g., `User` and `Email`).
        - `repository`: Contains repositories for database operations.
        - `service`: Contains business logic and services.
- **src/main/resources**: This directory contains application configuration files, static resources, and templates.
    - `application.properties`: Configuration file for Spring Boot to Database.
- **pom.xml**: The project's Maven configuration file, which manages all dependencies.
This folder structure is designed to keep the project organized and maintainable.
Be sure to explore each directory to understand where different parts of the code and resources are located.

Folder Structure of Vue.js Application for Project:
--------------------------------------------------------------------------
- `gmail-app/`: Root directory of your Vue.js project.
    - `node_modules/`: Directory containing project dependencies installed via npm or yarn.
    - `public/`: Directory for public assets.
    - `src/`: Main source code directory where you have all the Vue components and other code files.
        - `components/`: Directory containing Vue components used in your application.
                         Components are typically organized by their functionality.
            - `AllEmail.vue`: Vue component for displaying all emails.
            - `ChangePassword.vue`: Vue component for changing user passwords.
            - `EmailCompose.vue`: Vue component for composing emails.
            - `EmailInbox.vue`: Vue component for the email inbox.
            - `EmailSent.vue`: Vue component for sent emails.
            - `EmailView.vue`: Vue component for viewing individual emails.
            - `ForgotPassword.vue`: Vue component for handling forgotten passwords.
            - `Gmail.png`: Image asset.
            - `Important.vue`: Vue component for displaying important emails.
            - `Main.vue`: Main Vue component, often used as a layout or container for other components.
            - `NotFound.vue`: Vue component for handling 404 errors.
            - `Profile.vue`: Vue component for user profiles.
            - `profile-icon.png`: Image asset.
            - `ResetPassword.vue`: Vue component for resetting passwords.
            - `SignIn.vue`: Vue component for user sign-in.
            - `SignUp.vue`: Vue component for user sign-up.
            - `Starred.vue`: Vue component for displaying starred emails.
            - `Trash.vue`: Vue component for displaying deleted emails.
        - Other Vue.js project files and directories.
    - `index.html`: The main HTML file that serves as the entry point for your Vue application.
    - `package.json`: The configuration file that lists project dependencies and scripts.
    - `package-lock.json`: A lock file that ensures consistent dependency versions.
    - `README.md`: The main documentation file for your project.
    - `vite.config.js`: Configuration file for Vite, if you're using the Vite build tool.
The described folder structure is a common organization for Vue.js projects.
It separates components into their own directory for modularity and ease of maintenance.
You can add more directories and files as needed for your specific project requirements.
----------------------------------------------------------------------------
8. Running the Application
----------------------------------------------------------------------------
To run the "gmail-app" Vue.js project locally, follow these steps:
----------------------------------------------------------------------------
**Prerequisites:**
- Node.js: Ensure you have Node.js installed on your system.
           You can download it from [https://nodejs.org/](https://nodejs.org/).
           Note:(I Used The Node Version: v18.17.1)
**Steps:**
1. **Clone the Repository:**
   - Open your terminal or command prompt.
   - Navigate to the directory where you want to store the project.
   - Run the following command to clone the project repository:
     ```
     git clone https://github.com/buildbottech/Java-Vamsi-Work/tree/master/Gmail_clone/Gmail_ui
     ```
2. **Install Dependencies:**
   - Navigate to the "gmail-app" directory:
     ```
     cd gmail-app
     ```
   - Install project dependencies using npm (Node Package Manager):
     ```
     npm install
     ```
3. **Run the Development Server:**
   - After installing dependencies, start the development server:
     ```
     npm run dev
     ```
   - This command will build the Vue.js project and start a local development server.
   - You will see output indicating the local server's address (like http://localhost:5173).
4. **Access the Application:**
   - Open your web browser and go to (http://localhost:5173) to access the running application.
5. **Explore the Application:**
   - You can now explore and interact with the "gmail-app" Vue.js application locally.
6. **Stop the Development Server:**
   - To stop the development server, press `Ctrl + C` in the terminal where the server is running.
**Additional Notes:**
- Make sure that any backend server or API required for the application is also running and accessible.
Now, you should be able to import and run the Vue.js project "gmail-app" locally on your system.
If you encounter any issues or have specific configuration requirements,
refer to the project's documentation.
-----------------------------------------------------------------------------
To run the "gmail-app" Vue.js project locally, follow these steps:
-----------------------------------------------------------------------------
**Prerequisites:**
- Java Development Kit (JDK): Ensure you have Java JDK 8 or higher installed on your system.
   You can download it from (https://www.oracle.com/java/technologies/javase-downloads.html).
**Steps:**

**Clone the Repository:**
   - Open your terminal or command prompt.
   - Navigate to the directory where you want to store the project.
   - Run the following command to clone the project repository:
     ```
     git clone https://github.com/buildbottech/Java-Vamsi-Work/tree/master/Gmail_clone/Gmail_Backend
     ```
2. **Import Spring Boot Project:**
   - Open your preferred Integrated Development Environment (IDE) such as Eclipse or IntelliJ IDEA.
   - Import the Spring Boot project
3. **Run the Spring Boot Application:**
   - Locate the main application class (usually `GmailAppApplication.java`) in your IDE.
   - Right-click on the main class and select "Run" to start the Spring Boot application.
   - The Spring Boot application will start, and you'll see logs indicating that the application is running.
4. **Access the Application:**
   - The Spring Boot backend is now serving the Vue.js frontend.
5. **Explore the Application:**
   - You can now explore and interact with the "gmail-app" Spring Boot + Vue.js application locally.
6. **Stop the Spring Boot Application:**
   - To stop the Spring Boot application, use the stop button in your IDE.
**Additional Notes:**
- Depending on your project's specific configuration, you may need to set up a database or configure other application properties.
- Make sure that the Vue.js application (frontend) is also running and accessible as described in the previous steps.
Now, you should be able to import and run both the Vue.js frontend and Spring Boot backend of the "gmail-app" application locally on your system.
If you encounter any issues or have specific configuration requirements, refer to the project's documentation.
