package com.healthcare.integrator1.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;


@Configuration
@EnableWebMvc
@PropertySource("classpath:dbconfig.properties")
@ComponentScan(basePackages= {"com.healthcare.*"})
@EnableTransactionManagement
@Import({ SecurityConfig.class })
public class SpringAppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	public Environment env;
	
	@Autowired
    ApplicationContext applicationContext;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

    
    //1. Creating SpringResourceTemplateResolver
    @Bean
    public SpringResourceTemplateResolver springTemplateResolver(){
        SpringResourceTemplateResolver springTemplateResolver = new SpringResourceTemplateResolver();
        springTemplateResolver.setApplicationContext(this.applicationContext);
        springTemplateResolver.setPrefix("/WEB-INF/pages/");
        springTemplateResolver.setSuffix(".html");
        return springTemplateResolver;
    }
    
    //2. Creating SpringTemplateEngine
    @Bean
    public SpringTemplateEngine springTemplateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(springTemplateResolver());
        springTemplateEngine.addDialect(new SpringSecurityDialect());
        return springTemplateEngine;
    }
    
    
    //3. Registering ThymeleafViewResolver
    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        return viewResolver;
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException{
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
         resolver.setMaxUploadSizePerFile(5242880);//5MB
        return resolver;
    }

 
   
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("mysql_driver"));
		dataSource.setUrl(env.getProperty("mysql_url"));
		dataSource.setUsername(env.getProperty("mysql_user"));
		dataSource.setPassword(env.getProperty("mysql_password"));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		sf.setDataSource(dataSource);
		sf.setPackagesToScan("com.healthcare.model");
		Properties props = new Properties();
		props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		sf.setHibernateProperties(props);
		return sf;

	}
	
	@Bean
	public HibernateTemplate hibernateTemplate(SessionFactory sf) {
		HibernateTemplate htemp=new HibernateTemplate(sf);
		return htemp;

	}
	
	@Bean
	public HibernateTransactionManager txManager(SessionFactory sf) {
		HibernateTransactionManager txManager=new HibernateTransactionManager(sf);
		return txManager;
	}

}
