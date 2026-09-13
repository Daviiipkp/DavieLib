package org.daviipkp.davielib.errorhandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class DCrash extends DExecutableErrorHandler {

    private Exception e;

    @Override
    public void run() {
        try {
            throw e;
        } catch (Exception ex) {
        }
    }

}
