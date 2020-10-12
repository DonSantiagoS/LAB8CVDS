package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBATISTipoItemDAO implements TipoItemDAO{

  @Inject
  private TipoItemMapper tipoItemMapper;    
 
  @Override
  public void save(TipoItem tipoItem) throws PersistenceException {
  		tipoItemMapper.addTipoItem(tipoItem);
  	
  }
  @Override
  public TipoItem consultarTipoItem(int id) throws PersistenceException {
  try{
     return tipoItemMapper.getTipoItem(id);
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException(PersistenceException.C_ITEM);
  }
  
  }
  @Override
  public List<TipoItem> consultarTiposItem() throws PersistenceException{
  try{
	  return tipoItemMapper.getTiposItem();
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException(PersistenceException.L_ITEM);
  }
  }



  }