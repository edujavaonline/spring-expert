package com.edujavaonline.brewer.config;

import java.math.BigDecimal;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.edujavaonline.brewer.controller.CervejasController;
import com.edujavaonline.brewer.converter.EstiloConverter;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Classe responsável por ensinar o Spring a encontrar os controllers. 
 * A anotação Configuration diz que essa classe é uma classe de configuração.
 * A anotação ComponentScan ensina o Spring como encontrar as classes de controllers. 
 * A anotação EnableWebMvc habilita essa configuração para um projeto Web MVC.
 * O Spring fornece alguns adaptadores p/ configuração. Esses
 * adaptadores são classes que podemos extender nas classes de configurações
 * para facilitar a configuração
 * 
 * @author DosReis
 *
 */

@Configuration
@ComponentScan(basePackageClasses = { CervejasController.class })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	
	/**
	 * Através deste método que as nossas páginas HTML serão encontradas, processar os dados que vamos colocar lá dentro, etc.
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	/**
	 * Através do TemplateEngine que conseguimos processar os arquivos HTML.
	 * SpringTemplateEngine é uma implementação do ITemplateEngine. Este método 
	 * tem a dependência do templateResolver que é passado por parâmetro no método
	 * setTemplateResolver. 
	 * O método addDialect para adicionarmos o dialeto, inclusive podemos criar o nosso próprio dialeto. Utilizamos
	 * este método para utilizar o Thymeleaf - Layout Dialect.
	 * Devemos utilizar a anotação Bean para que este método possa estar disponível para aplicação Spring, ou seja, 
	 * este objeto vai ficar disponível dentro do contexto do Spring
	 *  
	 * @return
	 */
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new LayoutDialect());
		return engine;
	}

	/**
	 * SpringResourceTemplateResolver é para resolver templates do Spring. O método
	 * setApplicationContext eu recebo um objeto applicationContext, a documentação
	 * do Thymeleaf diz que precisamos dele. O método setPrefix ele informa aonde
	 * estão os templates. O método setTemplateMode informa qual é o modo de
	 * template que estamos trabalhando.
	 * 
	 * @return
	 */
	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;

	}
	
	/**
	 * Através deste método, conseguimos adicionar recurso(imagens, CSS, Javascript, etc) que não tenham o controller
	 */
	@Override	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("**/").addResourceLocations("classpath:/static/");
	}
	/**
	 * Através deste método, registramos os nossos converters
	 * É muito importante que este método se chame mvcConversionService().
	 * Isso porque o Spring vai procurar um Bean que tenha esse nome para procurar os conversores.
	 * @return
	 */
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new EstiloConverter());
		
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		return conversionService;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

}

/*
 * O aplicationContext é um objeto do Spring. Quando a aplicação acaba de subir
 * é possível receber este applicationContext. Para receber tem que implementar a
 * interface ApplicationContextAware. Aware, porque eu desejo ser informado
 * disso.
 */
