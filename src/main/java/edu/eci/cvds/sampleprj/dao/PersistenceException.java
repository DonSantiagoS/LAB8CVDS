package edu.eci.cvds.sampleprj.dao;

public class PersistenceException extends Exception {
	
	public static final String ITEM = "Error al registrar el item";
	public static final String CLIENTE = "Error al registrar el cliente";
	public static final String C_ITEM = "Error al consultar el item";
	public static final String C_CLIENTE = "Error al consultar el cliente";
	public static final String L_CLIENTE = "Error al consultar los clientes";
	public static final String L_ITEM = "Error al consultar los items";
	public static final String L_ITEMD = "Error al consultar los items disponibles";
	public static final String ITEM_CLIENTE = "Error al agregar items al cliente";
	public static final String A_TARIFA = "Error al actualizar Tarifa";
	public static final String V_CLIENTE = "Error al vetar al cliente";

	public PersistenceException(String msg) {
        super(msg);
    }
}
