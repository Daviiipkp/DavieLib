package org.daviipkp.davielib.errorhandler;

public class DCrash extends DExecutableErrorHandler {

    private Exception e;

    public <T extends Exception> DCrash(T arg0) {
        e = arg0;
    }

    @Override
    public void run() {
        try {
            throw e;
        } catch (Exception ex) {
        }
    }

}
