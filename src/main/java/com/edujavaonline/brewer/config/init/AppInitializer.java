package com.edujavaonline.brewer.config.init;



import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.edujavaonline.brewer.config.JPAConfig;
import com.edujavaonline.brewer.config.WebConfig;

/**
 * Classe responsável pela inicialização da aplicação
 * A classe AbstractAnnotationConfigDispatcherServletInitializer foi extendida para configurar o DispatcherServlet (Front-Controller do Spring)
 * @author DosReis
 *
 */

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class };
	}
	

	/**
	 * Neste método é necessário retornar uma classe configurada ensinando o Spring como achar os controllers
	 * 
	 */	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class};
	}
	

	/**
	 * Método responsável por dizer o padrão da url que será delegado para o DispatcherServlet
	 * Colocando o "/", significa que será qualquer url dentro da aplicação, ou seja, o nome da aplicação para frente
	 */	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	/**
	 * Método para resolver problemas de acentuação e outros caracteres especiais. 
	 * Através deste método eu forço ele a utilizar o UTF-8.
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		
		return new Filter[] { characterEncodingFilter };
	}

}
