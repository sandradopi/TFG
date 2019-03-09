package es.udc.lbd.asi.restexample.model.exception;

import es.udc.lbd.asi.restexample.web.exception.ModelException;

public class TeamExistsException extends ModelException {
     public TeamExistsException(String msg) {
         super(msg);
     }
 }