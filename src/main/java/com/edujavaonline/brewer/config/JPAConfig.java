package com.edujavaonline.brewer.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.edujavaonline.brewer.model.Cerveja;
import com.edujavaonline.brewer.repository.Cervejas;

/**
 * Classe responsável por configurar o JPA
 * @author DosReis
 *
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = Cervejas.class, enableDefaultTransactions = false)
@EnableTransactionManagement
public class JPAConfig {
	
	/**
	 * Esta configuração do datasource serve para definirmos parâmetros de conexão e configuração com o banco de dados. 
	 * Por exemplo, no dataSource que configuramos, definimos url de conexão com o banco, senha, usuário entre outros detalhes importantes.
	 * Usamos o objeto JndiDataSourceLookup para recuperar o dataSource(jdbc/brewerDB)
	 * Utilizamos o método setResourceRef(true) e setamos ele como true quando temos que procurar no container(Tomcat)
	 * @return
	 */	
	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true);
		return dataSourceLookup.getDataSource("jdbc/brewerDB");
	}
	
	/**
	 * Método responsável por configurar o Hibernate.
	 * O método setDatabase() informamos qual banco de dados iremos utilizar.
	 * O método setShowSql() é para mostrar o sql gerado(no console).
	 * O método setGenerateDdl() setamos false porque baseado nos nossos modelos,
	 * não queremos que ele gere o bd porque estamos usando o flyway
	 * O método setDatabasePlatform() informamos o dialeto. 
	 * Esse dialeto é para conseguir traduzir o JPQL, as Criteria para linguagem SQL que o MySQL entende.
	 * @return
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(false);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		return adapter;
		
	}
	
	/**
	 * Método responsável por configurar o EntityManagerFactory que o cara que vai criar o EntityManager.
	 * Precisamos do EntityManager para gerenciar as entidades, então o EntityManagerFactory é a fábrica do EntityManager.
	 * Para criar o EntityMangerFactory precisamos do LocalContainerEntityManagerFactoryBean
	 * Através do método setPackgesToScan() precisamos informar aonde estão as entidades que serão gerenciadas
	 * pelo EntityManager.
	 * Depois que estiver tudo definido a documentação exige que utilizemos o método afterPropertiesSet().
	 * Através do método getObject() ele vai saber criar o EntityManagerFactory
	 * @param dataSource
	 * @param jpaVendorAdapter
	 * @return
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan(Cerveja.class.getPackage().getName());
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	/**
	 * Método responsável por configurar a transação
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
