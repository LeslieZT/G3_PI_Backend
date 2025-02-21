package dh.backend.music_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class MusicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicStoreApplication.class, args);

		String url = "jdbc:h2:./MusicStoreApplication";
		String usuario = "sa";
		String contraseña = "";

		try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
			 Statement stmt = conn.createStatement()) {

			// CREAR TABLAS
			String sql = """
                    CREATE TABLE IF NOT EXISTS role (
                        role_id INT AUTO_INCREMENT PRIMARY KEY,
                        role_name VARCHAR(255) NOT NULL
                    );

                    CREATE TABLE IF NOT EXISTS users (
                        user_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_name VARCHAR(255) NOT NULL,
                        user_lastname VARCHAR(255) NOT NULL,
                        user_email VARCHAR(255) UNIQUE NOT NULL,
                        user_password VARCHAR(255) NOT NULL,
                        user_phone VARCHAR(20),
                        user_address VARCHAR(255),
                        role_id INT,
                        FOREIGN KEY (role_id) REFERENCES role(role_id)
                    );

                    CREATE TABLE IF NOT EXISTS brands (
                        brand_id INT AUTO_INCREMENT PRIMARY KEY,
                        brand_name VARCHAR(255) NOT NULL
                    );

                    CREATE TABLE IF NOT EXISTS categories (
                        category_id INT AUTO_INCREMENT PRIMARY KEY,
                        category_name VARCHAR(255) NOT NULL
                    );

                    CREATE TABLE IF NOT EXISTS products (
                        product_id INT AUTO_INCREMENT PRIMARY KEY,
                        product_category_id INT,
                        product_brand_id INT,
                        product_description TEXT,
                        product_price DECIMAL(10,2),
                        product_stock INT,
                        is_available BOOLEAN DEFAULT TRUE,
                        FOREIGN KEY (product_category_id) REFERENCES categories(category_id),
                        FOREIGN KEY (product_brand_id) REFERENCES brands(brand_id)
                    );

                    CREATE TABLE IF NOT EXISTS rentals (
                        rental_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT,
                        rental_status VARCHAR(50),
                        total_amount DECIMAL(10,2),
                        creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
                    );

                    CREATE TABLE IF NOT EXISTS order_details (
                        order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
                        rental_id INT,
                        product_id INT,
                        quantity INT,
                        unit_price DECIMAL(10,2),
                        FOREIGN KEY (rental_id) REFERENCES rentals(rental_id),
                        FOREIGN KEY (product_id) REFERENCES products(product_id)
                    );

                    CREATE TABLE IF NOT EXISTS payment (
                        payment_id INT AUTO_INCREMENT PRIMARY KEY,
                        type VARCHAR(50) NOT NULL
                    );

                    CREATE TABLE IF NOT EXISTS rental_payments (
                        rental_payment_id INT AUTO_INCREMENT PRIMARY KEY,
                        rental_id INT,
                        days_exceeded BOOLEAN DEFAULT FALSE,
                        days_since_rented INT NOT NULL,
                        FOREIGN KEY (rental_id) REFERENCES rentals(rental_id)
                    );
                """;

			stmt.executeUpdate(sql);
			System.out.println("Base de datos y tablas creadas correctamente");

			// INSERTAR DATOS DE PRUEBA
			String insertSQL = """
                    INSERT INTO role (role_name) VALUES ('Admin'), ('Cliente');

                    INSERT INTO users (user_name, user_lastname, user_email, user_password, role_id)
                    VALUES ('Juan', 'Pérez', 'juan@email.com', '1234', 2);

                    INSERT INTO brands (brand_name) VALUES ('Zildjian'), ('Peavey'), ('Yamaha');

                    INSERT INTO categories (category_name) VALUES ('Guitarras'), ('Baterías'), ('Bajos');

                    INSERT INTO products (product_category_id, product_brand_id, product_description, product_price, product_stock, is_available)
                    VALUES (1, 1, 'Bajo Yamaha ', 500.00, 4, TRUE),
                           (2, 3, 'Batería Peavey ', 600.00, 4, TRUE);

                    INSERT INTO rentals (user_id, rental_status, total_amount) VALUES (1, 'Pendiente', 2000.00);
                """;

			stmt.executeUpdate(insertSQL);
			System.out.println("Datos de prueba insertados correctamente.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

