package is.springbootmono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootMonoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMonoApplication.class, args);
	}

}
