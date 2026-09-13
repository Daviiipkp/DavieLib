package org.daviipkp.davielib;

import org.daviipkp.davielib.errorhandler.DErrorHandler;
import org.daviipkp.davielib.errorhandler.DFallbackValue;

public class DInsurance {

    private static <T> T insuranceError(T object, DErrorHandler handler) {
        if(handler == null) {
            throw new IllegalArgumentException("The object of class " + object.getClass().getSimpleName() +  " should not be null.");
        }
        if(handler instanceof DFallbackValue) {
            Object obj = ((DFallbackValue)handler).getObj();
            if(object.getClass().isInstance(obj)) {
                return (T)obj;
            }else{
                throw new IllegalArgumentException("The error handler has a Fallback Value different than the required '" + object.getClass().getSimpleName() +  "'");
            }
        }else {
            DErrorHandler.declareError(handler);
            return null;
        }
    }

    private static <T> T insuranceError(Class<T> object, DErrorHandler handler) {
        if(handler instanceof DFallbackValue) {
            Object obj = ((DFallbackValue)handler).getObj();
            if(object.isInstance(obj)) {
                return (T)obj;
            }else{
                throw new IllegalArgumentException("The error handler has a Fallback Value different than the required '" + object.getClass().getSimpleName() +  "'");
            }
        }else {
            DErrorHandler.declareError(handler);
            return null;
        }
    }

    //
    //
    //
    //
    //

    public static <T> T ensureNotNull(T object, DErrorHandler errorHandler) {
        if(object != null) {
            return object;
        }
        return insuranceError(object, errorHandler);
    }

    public static <T> T ensureNotNull(T object) {
        if(object != null) {
            return object;
        }
        return insuranceError(object, null);
    }
    
    public static String ensureNotBlank(String object, DErrorHandler errorHandler) {
        if(!object.isBlank()) {
            return object;
        }
        return insuranceError(object, errorHandler);
    }

    public static String ensureNotBlank(String object) {
        if(!object.isBlank()) {
            return object;
        }
        return insuranceError(object, null);
    }

    public static <T> T ensureType(Object object, Class<T> type, DErrorHandler errorHandler) {
        if(type.isInstance(object)) {
            return (T)object;
        }
        return insuranceError(type, errorHandler);
    }

    public static <T> T ensureType(Object object, Class<T> type) {
        if(type.isInstance(object)) {
            return (T)object;
        }
        return insuranceError(type, null);
    }



}
