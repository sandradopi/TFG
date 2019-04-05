package es.udc.lbd.asi.restexample.model.exception;

import es.udc.lbd.asi.restexample.web.exception.ModelException;

public class MaxPlayersException extends ModelException {
     public MaxPlayersException(String msg) {
         super(msg);
     }
 }