/**
 * 
 */
package com.psib.common;

/**
 * @author DatHT
 * Jun 28, 2016
 * @Email: datht0601@gmail.com
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(String message, Throwable e) {
        super(message, e);
    }

    public DatabaseException(String messsage) {
        super(messsage);
    }
}
