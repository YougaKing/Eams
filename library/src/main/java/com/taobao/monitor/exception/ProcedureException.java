package com.taobao.monitor.exception;

public class ProcedureException extends RuntimeException {

    public ProcedureException() {
    }

    public ProcedureException(String message) {
        super(message);
    }
}
