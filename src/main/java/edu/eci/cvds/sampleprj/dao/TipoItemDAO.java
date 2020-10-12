package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.samples.entities.TipoItem;

public interface TipoItemDAO {

	public void save(TipoItem tipoItem) throws PersistenceException;

    public TipoItem consultarTipoItem(int id) throws PersistenceException;
    
    public List<TipoItem> consultarTiposItem() throws PersistenceException;
}