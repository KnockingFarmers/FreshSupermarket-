package org.gl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author GL
 */
@SpringBootApplication
@EnableTransactionManagement
public class FreshSupermarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreshSupermarketApplication.class, args);
    }

}
