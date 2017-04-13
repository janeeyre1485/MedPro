package auth.config;



import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "auth" })
@PropertySource(value = "classpath:application.properties")
public class JpaConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {

		LocalContainerEntityManagerFactoryBean managerFactory = new LocalContainerEntityManagerFactoryBean();

		managerFactory.setDataSource(dataSource());
		managerFactory.setPackagesToScan(new String[] { "auth" });
		managerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		managerFactory.setJpaProperties(jpaProperties());

		return managerFactory;
	}

	public Properties jpaProperties() {

		Properties jpaProperties = new Properties();

		jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		jpaProperties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));

		return jpaProperties;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		return jpaVendorAdapter;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory managerFactory){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(managerFactory);
		return txManager;
	}
}
