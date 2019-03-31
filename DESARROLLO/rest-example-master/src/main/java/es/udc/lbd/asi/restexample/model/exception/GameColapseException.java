package es.udc.lbd.asi.restexample.model.exception;

import es.udc.lbd.asi.restexample.web.exception.ModelException;

public class GameColapseException extends ModelException {
     public GameColapseException(String msg) {
         super(msg);
     }
 }