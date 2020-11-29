package com.bdg.banktransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.bdg.banktransfer.repository")
@EnableJpaRepositories("com.bdg.banktransfer.service")
@SpringBootApplication
public class BankTransferApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankTransferApplication.class, args);
    }
}