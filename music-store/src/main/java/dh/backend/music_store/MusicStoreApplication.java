package dh.backend.music_store;

import dh.backend.music_store.entity.Role;
import dh.backend.music_store.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MusicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicStoreApplication.class, args);
	}


}
