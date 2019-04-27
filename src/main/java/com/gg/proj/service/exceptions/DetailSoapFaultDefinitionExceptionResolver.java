package com.gg.proj.service.exceptions;

import com.gg.proj.service.books.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("statusCode");
    private static final QName MESSAGE = new QName("message");

    private static final Logger log = LoggerFactory.getLogger(DetailSoapFaultDefinitionExceptionResolver.class);

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {

        if (ex instanceof ServiceFaultException) {
            log.warn("Exception processed : " + ex);
            ServiceStatus status = ((ServiceFaultException) ex).getServiceStatus();
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText(status.getStatusCode());
            detail.addFaultDetailElement(MESSAGE).addText(status.getMessage());
        }
    }
}
