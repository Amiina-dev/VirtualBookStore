###VirtualBookStore

A backend system for a virtual bookstore built with Spring Boot, integrating with Airtable to manage and display book inventory. The application offers RESTful APIs to search, view, and manage books and shopping cart functionality — with a checkout feature in the works.

Features
- View available books from Airtable
- Search books by title, author or genre
- Simulated customer login using HTTP sessions
- Cart management: add, remove, view, and clear items
- Input validation for book IDs
- (Coming Soon) Checkout and order processing
- 
Tech Stack
- Java + Spring Boot
- Maven
- Airtable API
- RESTful Web Services
- HttpSession for session management


Getting Started
- Clone the repository
git clone https://github.com/your-username/VirtualBookStore.git
cd VirtualBookStore
git checkout master

- Configure Airtable credentials in application.properties:
airtable.api.token=your_airtable_api_key  
airtable.base.id=your_base_id  
airtable.table.name=Books

- Build and run the project:
mvn clean install
mvn spring-boot:run

API Endpoints
- GET    /books                 → Fetch all available books  
- GET    /books/search          → Search books by title, author or genre  
- POST   /cart/login            → Simulate user session(for testing)  
- POST   /cart/add/{bookId}     → Add book to cart  
- GET    /cart                  → View current cart contents  
- DELETE /cart/remove/{bookId} → Remove book from cart  
- DELETE /cart/clear           → Clear entire cart  
- POST   /checkout             → (Coming Soon) Finalize purchase  

Can be tested using [localhost:8080/Swaggerui](http://localhost:8080/swagger-ui/index.html#/)
