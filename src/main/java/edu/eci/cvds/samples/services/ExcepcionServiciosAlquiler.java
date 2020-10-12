
package edu.eci.cvds.samples.services;

public class ExcepcionServiciosAlquiler extends Exception {
    public ExcepcionServiciosAlquiler(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcepcionServiciosAlquiler(String message) {
        super(message);
    }
}