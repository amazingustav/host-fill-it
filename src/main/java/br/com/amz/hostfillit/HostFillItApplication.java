package br.com.amz.hostfillit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing
@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = {"br.com.amz.hostfillit.persistence.entity"})
public class HostFillItApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostFillItApplication.class, args);
	}
}
