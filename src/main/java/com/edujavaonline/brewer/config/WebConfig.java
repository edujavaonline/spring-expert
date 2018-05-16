package com.edujavaonline.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.edujavaonline.brewer.controller.CervejasController;

/**
 * Classe responsável por ensinar o Spring a encontrar os controllers.
 * A anotação Configuration diz que essa classe é uma classe de configuração.
 * A anotação ComponentScan ensina o Spring como encontrar as classes de controllers.
 * A anotação EnableWebMvc habilita essa configuração para um projeto Web MVC.
 * O Spring fornece alguns adaptadores p/ configuração. 
 * Esses adaptadores são classes que podemos extender nas classes de configurações para facilitar a configuração
 * @author DosReis
 *
 */

@Configuration
@ComponentScan(basePackageClasses = {CervejasController.class})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{

}
