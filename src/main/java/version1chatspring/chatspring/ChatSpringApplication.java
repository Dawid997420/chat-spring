package version1chatspring.chatspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import version1chatspring.chatspring.configuration.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ChatSpringApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChatSpringApplication.class, args);
	}

}
