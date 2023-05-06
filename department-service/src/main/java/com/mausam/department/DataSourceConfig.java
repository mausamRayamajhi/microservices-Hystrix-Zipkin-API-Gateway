//package com.mausam.department;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//import static org.hibernate.cfg.AvailableSettings.*;
//
//@Configuration
//@PropertySource("classpath:application.yml")
////@EnableTransactionManagement
//public class DataSourceConfig {
//    @Value("${db.url}")
//    private String dbUrl;
//
//    @Value("${db.username}")
//    private String username;
//
//    @Value("${db.password}")
//    private String password;
//
//    @Value("${db.driverName}")
//    private String driverName;
//
//    @Bean
//    @Primary
//    public DataSource getDataSource() {
//        System.out.println(dbUrl);
////        System.out.println(dbUsername);
////
////        System.out.println(dbPassword);
//
//        return DataSourceBuilder.create()
//                .driverClassName(driverName)
//                .url(dbUrl)
//                .username(username)
//                .password(password)
//                .build();
//    }
//
////    @Bean
////    public LocalSessionFactoryBean sessionFactory() {
////        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
////        sessionFactory.setDataSource(getDataSource());
//////        sessionFactory.setPackagesToScan("com.als");
//////        sessionFactory.setAnnotatedClasses(EopInformation.class);
////        sessionFactory.setHibernateProperties(hibernateProperties());
////
////        return sessionFactory;
////    }
////    private Properties hibernateProperties() {
////        Properties hibernateProperties = new Properties();
//////        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", dbHibernateDdlAuto);
//////        hibernateProperties.setProperty("hibernate.show_sql", dbShowSql);
//////        hibernateProperties.setProperty("spring.jpa.database", dbDatabaseType);
//////        hibernateProperties.setProperty("hibernate.dialect", dialect);
//////        hibernateProperties.setProperty("connection.pool_size", connectionPoolSize);
//////        hibernateProperties.setProperty("hibernate.dialect.storage_engine", "innodb");
//////        hibernateProperties.setProperty("hibernate.generate_statistics", statistics);
//////        hibernateProperties.setProperty(C3P0_TIMEOUT, c3p0Timeout);
//////        hibernateProperties.setProperty(C3P0_MIN_SIZE, c3p0MinSize);
//////        hibernateProperties.setProperty(C3P0_MAX_SIZE, c3p0MaxSize);
////
////        return hibernateProperties;
////    }
////    @Bean
////    public PlatformTransactionManager hibernateTransactionManager() {
////        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
////        transactionManager.setSessionFactory(sessionFactory().getObject());
////        return transactionManager;
////    }
//}