package ru.studiotg.minesweeper;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class,
                ReactiveSecurityAutoConfiguration.class,
        }
)
public class MinesweeperApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinesweeperApplication.class, args);
    }
}
