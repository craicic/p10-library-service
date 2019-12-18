package com.gg.proj.service.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM,
        customFaultCode = "{" + UserNotFoundException.NAMESPACE_URI + "}custom_fault",
        faultStringOrReason = "@faultString")
public class InvalidBookingOperationException extends Exception {
    private static final long serialVersionUID = 1L;
    public static final String NAMESPACE_URI = "http://proj.gg.com/service/exception";

    public InvalidBookingOperationException(String message) {
        super(message);
    }
}
