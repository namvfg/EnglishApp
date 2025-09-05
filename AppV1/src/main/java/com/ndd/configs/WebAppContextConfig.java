/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndd.formatters.CategoryFormatter;
import com.ndd.formatters.CategoryTypeFormatter;
import com.ndd.formatters.LessonFormatter;
import com.ndd.formatters.LessonTypeFormatter;
import com.ndd.formatters.SectionFormatter;
import com.ndd.formatters.SectionTypeFormatter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Admin
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.ndd.controllers",
    "com.ndd.repository",
    "com.ndd.service",
    "com.ndd.components"
})
@PropertySource("classpath:configs.properties")
public class WebAppContextConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new CategoryTypeFormatter());
        registry.addFormatter(new LessonTypeFormatter());
        registry.addFormatter(new CategoryFormatter());
        registry.addFormatter(new SectionTypeFormatter());
        registry.addFormatter(new LessonFormatter());
        registry.addFormatter(new SectionFormatter());
    }

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("YYYY-MM-dd");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource m = new ResourceBundleMessageSource();

        m.setBasename("messages");
        return m;
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean
                = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cho phép truy cập file JS, CSS, images
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter
                        = (MappingJackson2HttpMessageConverter) converter;

                ObjectMapper objectMapper = jacksonConverter.getObjectMapper();
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
                objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
        }
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(52428800); // 5MB
        return resolver;
    }

//    @Bean
//    public InternalResourceViewResolver internalResourceViewResolver() {
//        InternalResourceViewResolver r = new InternalResourceViewResolver();
//        r.setViewClass(JstlView.class);
//        r.setPrefix("/WEB-INF/pages/");
//        r.setSuffix(".jsp");
//        
//        return r;
//    }
}
