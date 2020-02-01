package com.gg.proj.app;

import com.gg.proj.service.exceptions.DetailSoapFaultDefinitionExceptionResolver;
import com.gg.proj.service.exceptions.ServiceFaultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

@EnableWs
@Configuration
public class WebServiceConfig {

    private CustomWebServiceProperties customWebServiceProperties;

    @Autowired
    public WebServiceConfig(CustomWebServiceProperties customWebServiceProperties) {
        this.customWebServiceProperties = customWebServiceProperties;
    }

    //    WEB SERVICE
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        // base is "/ws/*"
        String urlMapping = customWebServiceProperties.getLocation() + "/*";
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, urlMapping);
    }

    @Bean(name = "books")
    public DefaultWsdl11Definition bookWsdl11Definition(XsdSchema booksSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("BookPort");
        wsdl11Definition.setLocationUri(customWebServiceProperties.getLocation());
        wsdl11Definition.setTargetNamespace("http://proj.gg.com/service/books");
        wsdl11Definition.setSchema(booksSchema);
        return wsdl11Definition;
    }

    @Bean(name = "users")
    public DefaultWsdl11Definition userWsdl11Definition(XsdSchema usersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("UserPort");
        wsdl11Definition.setLocationUri(customWebServiceProperties.getLocation());
        wsdl11Definition.setTargetNamespace("http://proj.gg.com/service/users");
        wsdl11Definition.setSchema(usersSchema);
        return wsdl11Definition;
    }

    @Bean(name = "profiles")
    public DefaultWsdl11Definition profileWsdl11Definition(XsdSchema profilesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProfilePort");
        wsdl11Definition.setLocationUri(customWebServiceProperties.getLocation());
        wsdl11Definition.setTargetNamespace("http://proj.gg.com/service/profiles");
        wsdl11Definition.setSchema(profilesSchema);
        return wsdl11Definition;
    }

    @Bean(name = "loans")
    public DefaultWsdl11Definition loanWsdl11Definition(XsdSchema loansSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("LoanPort");
        wsdl11Definition.setLocationUri(customWebServiceProperties.getLocation());
        wsdl11Definition.setTargetNamespace("http://proj.gg.com/service/loans");
        wsdl11Definition.setSchema(loansSchema);
        return wsdl11Definition;
    }

    @Bean(name = "bookings")
    public DefaultWsdl11Definition bookingWsdl11Definition(XsdSchema bookingsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("BookingPort");
        wsdl11Definition.setLocationUri(customWebServiceProperties.getLocation());
        wsdl11Definition.setTargetNamespace("http://proj.gg.com/service/bookings");
        wsdl11Definition.setSchema(bookingsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema booksSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/xsd/books/books.xsd"));
    }

    @Bean
    public XsdSchema usersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/xsd/users/users.xsd"));
    }

    @Bean
    public XsdSchema loansSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/xsd/loans/loans.xsd"));
    }

    @Bean
    public XsdSchema profilesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/xsd/profiles/profiles.xsd"));
    }

    @Bean
    public XsdSchema bookingsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/xsd/bookings/bookings.xsd"));
    }

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        exceptionResolver.setDefaultFault(faultDefinition);

        Properties errorMappings = new Properties();
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(ServiceFaultException.class.getName(), SoapFaultDefinition.SERVER.toString());
        exceptionResolver.setExceptionMappings(errorMappings);
        exceptionResolver.setOrder(1);
        return exceptionResolver;
    }
}
