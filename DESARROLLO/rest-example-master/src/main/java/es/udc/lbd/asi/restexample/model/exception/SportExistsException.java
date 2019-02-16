package es.udc.lbd.asi.restexample.model.exception;

import es.udc.lbd.asi.restexample.web.exception.ModelException;

public class SportExistsException extends ModelException {
     public SportExistsException(String msg) {
         super(msg);
     }
 }