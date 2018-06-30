package com.edujavaonline.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.edujavaonline.brewer.service.CadastroCervejaService;
/**
 * Classe responsável por configurar as classes de serviço
 * @author DosReis
 *
 */

@Configuration
@ComponentScan(basePackageClasses = CadastroCervejaService.class)
public class ServiceConfig {

}
