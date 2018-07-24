package com.aivarsda.batterystatus.Exceptions;

public class ObjectAlreadyPresentInListException extends Exception
{
    private static final String EXCEPTION_MSG = " - battery observer already assigned.";
    public ObjectAlreadyPresentInListException(Object obj, String msg) {
        super(msg + obj.getClass().getCanonicalName() + EXCEPTION_MSG);
    }
}
