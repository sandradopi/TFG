package es.udc.lbd.asi.restexample.web.exception;

import es.udc.lbd.asi.restexample.model.domain.User_;

public class InstanceNotFoundExceptionHIB extends ModelException {
    private static final long serialVersionUID = 1L;

    public InstanceNotFoundExceptionHIB(Class<User_> clazz) {
        super("The object you are searching doesn´t exist " + clazz.getName());
    }
}
