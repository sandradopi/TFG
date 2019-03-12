package es.udc.lbd.asi.restexample.model.exception;

import es.udc.lbd.asi.restexample.web.exception.ModelException;

public class LocationExistsException extends ModelException {
     public LocationExistsException(String msg) {
         super(msg);
     }
 }