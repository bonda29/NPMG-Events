package org.example.events.npmg.config;//package org.example.ticketmaster.config;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.jdbc.datasource.init.ScriptUtils;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//
//@Component
//@RequiredArgsConstructor
//public class DatabaseInitializer {
//
//    private final ResourceLoader resourceLoader;
//
//    private final DataSource dataSource;
//
//    @PostConstruct
//    public void init() {
//        Resource resource = resourceLoader.getResource("classpath:PropertyInitializer.sql");
//
//        try (Connection connection = dataSource.getConnection()) {
//            ScriptUtils.executeSqlScript(connection, resource);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}