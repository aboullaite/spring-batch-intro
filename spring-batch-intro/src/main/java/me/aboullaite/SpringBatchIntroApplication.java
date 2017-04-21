package me.aboullaite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SpringBatchIntroApplication {

	public static void main(String[] args) {

		System.setProperty("input", "file://" + new File("/Users/aboullaite/Projects/Spring-batch-demo/students.csv").getAbsolutePath());
		System.setProperty("output", "file://" + new File("/Users/aboullaite/Projects/Spring-batch-demo/age.csv").getAbsolutePath());

		SpringApplication.run(SpringBatchIntroApplication.class, args);
	}
}
