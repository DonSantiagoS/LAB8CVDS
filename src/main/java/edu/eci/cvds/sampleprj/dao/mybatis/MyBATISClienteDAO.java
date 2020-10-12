package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBATISClienteDAO implements ClienteDAO{

  @Inject
  private ClienteMapper clienteMapper;    

  @Override
  public void save(Cliente c) throws PersistenceException{
  try{
      clienteMapper.insertarCliente(c);
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException(PersistenceException.CLIENTE);
  }        

  }

  @Override
  public Cliente consultarCliente(long id) throws PersistenceException {
	  try{
	      return clienteMapper.consultarCliente(id);
	  }
	  catch(org.apache.ibatis.exceptions.PersistenceException e){
	      throw new PersistenceException(PersistenceException.CLIENTE);
	  } 
  }
  
  @Override
  public List<Cliente> consultarClientes() throws PersistenceException {
	  try{
	      return  clienteMapper.consultarCliente();
	  }
	  catch(org.apache.ibatis.exceptions.PersistenceException e){
	      throw new PersistenceException(PersistenceException.CLIENTE);
	  } 
  }
  @Override
  public void agregarItemRentadoACliente(long idCliente,int idItem,Date fechainiciorenta, Date fechafinrenta) throws PersistenceException{
	  try{
		  clienteMapper.agregarItemRentadoACliente(idCliente,idItem,fechainiciorenta,fechafinrenta);
	  }
	  catch(org.apache.ibatis.exceptions.PersistenceException e){
	      throw new PersistenceException(PersistenceException.ITEM_CLIENTE);
	  }
  }
	@Override
	public void vetarCliente(long idCliente, int estado) throws PersistenceException {
	    try{
	        clienteMapper.vetarCliente(idCliente,estado);
	    }
	    catch(org.apache.ibatis.exceptions.PersistenceException e) {
	        throw new PersistenceException(PersistenceException.V_CLIENTE);
	    }
	
	}


}

