package com.bookstore;

import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            System.out.println("\n\nREAD-WRITE MODE:");
            bookstoreService.fetchAuthorReadWriteMode();
            
            System.out.println("\n\nREAD-ONLY MODE:");
            bookstoreService.fetchAuthorReadOnlyMode();
            
            System.out.println("\n\nREAD-WRITE MODE (DTO):");
            bookstoreService.fetchAuthorDtoReadWriteMode();
                        
            System.out.println("\n\nREAD-ONLY MODE (DTO):");
            bookstoreService.fetchAuthorDtoReadOnlyMode();
        };
    }
}


/*
 * 
 * 
 * 
 * 
 * What @Transactional(readOnly=true) Actually Do

Description: This application is meant to reveal what is the difference between @Transactional(readOnly = false) and @Transactional(readOnly = true). In a nuthsell, readOnly = false (default) fetches entites in read-write mode (managed). Before Spring 5.1, readOnly = true just set FlushType.MANUAL/NEVER, therefore the automatic dirty checking mechanism will not take action since there is no flush. In other words, Hibernate keep in the Persistent Context the fetched entities and the hydrated (loaded) state. By comparing the entity state with the hydrated state, the dirty checking mechanism can decide to trigger UPDATE statements in our behalf. But, the dirty checking mechanism take place at flush time, therefore, without a flush, the hydrated state is kept in Persistent Context for nothing, representing a performance penalty. Starting with Spring 5.1, the read-only mode is propagated to Hibernate, therefore the hydrated state is discarded immediately after loading the entities. Even if the read-only mode discards the hydrated state the entities are still loaded in the Persistent Context, therefore, for read-only data, relying on DTO (Spring projection) is better.

Key points:

readOnly = false load data in read-write mode (managed)
readOnly = true discard the hydrated state (starting with Spring 5.1)
 * 
 * 
 * 
 * 
 */
