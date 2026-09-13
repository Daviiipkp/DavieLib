package org.daviipkp.davielib.errorhandler;

public abstract class DErrorHandler {

    public static <T extends Exception> DErrorHandler crash(T exception) throws T {
        return new DCrash(exception);
    }

    public static <T extends Exception> DErrorHandler crash(String message) throws T {
        return new DCrash(new RuntimeException(message));
    }

    public static <T extends Exception> DErrorHandler crash() throws T {
        return new DCrash(new RuntimeException());
    }
    
    public static DErrorHandler fallbackValue(Object value) {
        return new DFallbackValue(value);
    }


    public static void declareError(DErrorHandler handler) {
        if(handler instanceof DExecutableErrorHandler) {
            ((DExecutableErrorHandler)handler).run();
        }
    }
}
