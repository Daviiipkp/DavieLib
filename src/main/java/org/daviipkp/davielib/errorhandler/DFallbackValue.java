package org.daviipkp.davielib.errorhandler;

import lombok.Getter;

@Getter 
public class DFallbackValue extends DErrorHandler {

    private Object obj;

    public DFallbackValue(Object value) {
        obj = value;
    }
    
}
