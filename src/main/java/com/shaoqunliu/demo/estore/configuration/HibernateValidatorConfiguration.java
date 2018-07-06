package com.shaoqunliu.demo.estore.configuration;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class HibernateValidatorConfiguration {

    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure().failFast(true).buildValidatorFactory()
                .getValidator();
    }
}
