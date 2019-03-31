package es.udc.lbd.asi.restexample.model.exception;

import es.udc.lbd.asi.restexample.web.exception.ModelException;

public class EventBeforeDayException extends ModelException {
     public EventBeforeDayException(String msg) {
         super(msg);
     }
 }