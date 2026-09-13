package org.daviipkp.davielib.errorhandler;

public class DFallbackValue extends DErrorHandler {

    private Object obj;

    public DFallbackValue(Object value) {
        obj = value;
    }

    public Object getObj() {
        return obj;
    }
    
}
