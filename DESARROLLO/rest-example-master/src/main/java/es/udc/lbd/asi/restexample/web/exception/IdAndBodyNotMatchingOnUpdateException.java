package es.udc.lbd.asi.restexample.web.exception;

import es.udc.lbd.asi.restexample.model.domain.Sport;

public class IdAndBodyNotMatchingOnUpdateException extends ResourceException {
    private static final long serialVersionUID = 1L;

    public IdAndBodyNotMatchingOnUpdateException(Class<Sport> clazz) {
        super("On update the sent item and the id on the request must be the same. Happening with " + clazz.getName());
    }
}