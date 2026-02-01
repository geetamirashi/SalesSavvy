<h1>SalesSavvy</h1>

<p>
    <strong>SalesSavvy</strong> is a Java-based sales management application designed to streamline
    and automate core sales processes. It provides a foundation for managing products,
    customers, orders, and sales workflows.
</p>

<p>
    This project is built using <strong>Java</strong> and <strong>Maven</strong> and is intended as a
    backend/service layer for a complete sales solution.
</p>

<hr>

<h2>🛠️ Tech Stack</h2>
<ul>
    <li><strong>Language:</strong> Java</li>
    <li><strong>Build Tool:</strong> Maven</li>
    <li><strong>Framework:</strong> Spring Boot</li>
    <li><strong>Database:</strong> MySQL / PostgreSQL / H2</li>
</ul>

<hr>

<h2>📁 Project Structure</h2>
<pre>
SalesSavvy
├── src/
│   └── main/
│       ├── java/              # Application source code
│       └── resources/
│           └── application.properties (to be created manually)
├── .gitignore
├── pom.xml
├── mvnw / mvnw.cmd
└── .mvn/
</pre>

<hr>

<h2>⚠️ Important Configuration Notice</h2>

<div class="warning">
    <p>
        This repository <strong>does NOT include</strong> the
        <code>application.properties</code> file for security reasons.
    </p>
    <p>
        You must create this file manually before running the application.
    </p>
</div>

<h3>🧾 Create <code>application.properties</code></h3>

<p><strong>Step 1:</strong> Navigate to</p>
<pre>src/main/resources/</pre>

<p><strong>Step 2:</strong> Create the file</p>
<pre>application.properties</pre>

<p><strong>Step 3:</strong> Add configuration (example)</p>

<pre>
# Application Name
spring.application.name=SalesSavvy

# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/salessavvy
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
</pre>

<p>
    <strong>Note:</strong> Never commit sensitive information such as database passwords or API keys.
</p>

<hr>

<h2>🚀 Getting Started</h2>

<h3>Prerequisites</h3>
<ul>
    <li>Java 8 or higher</li>
    <li>Maven 3.6+</li>
    <li>Git</li>
</ul>

<hr>

<h2>📥 Installation</h2>
<pre>
git clone https://github.com/geetamirashi/SalesSavvy.git
cd SalesSavvy
mvn clean install
</pre>

<hr>

<h2>▶️ Run the Application</h2>
<pre>mvn spring-boot:run</pre>

<p>The application will start at:</p>
<pre>http://localhost:8080</pre>

<hr>

<h2>🧪 Usage</h2>
<ul>
    <li>/products</li>
    <li>/customers</li>
    <li>/orders</li>
    <li>/sales</li>
</ul>

<hr>

<h2>📦 Build & Deployment</h2>
<pre>mvn package</pre>
<p>The executable JAR file will be generated inside the <code>target/</code> directory.</p>

<hr>

<h2>🧩 Contributing</h2>
<ol>
    <li>Fork the repository</li>
    <li>Create a feature branch</li>
    <li>Commit your changes</li>
    <li>Push to your fork</li>
    <li>Open a pull request</li>
</ol>

<hr>

<h2>📄 License</h2>
<p>This project is currently unlicensed. Add a <code>LICENSE</code> file if required.</p>

<hr>

<h2>🤝 Author</h2>
<p>
    <strong>Geeta Mirashi</strong><br>
    GitHub:
    <a href="https://github.com/geetamirashi" target="_blank">
        https://github.com/geetamirashi
    </a>
</p>

<div class="footer">
    ⭐ If you find this project useful, consider giving it a star!
</div>

</div>
</body>
</html>
