package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;

import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import org.mybatis.guice.transactional.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

    @Inject
    private ItemDAO itemDAO;
    @Inject
    private  ClienteDAO clienteDAO;
    @Inject
    private TipoItemDAO tipoItemDAO;

    @Override
    public long valorMultaRetrasoxDia(int itemId)throws ExcepcionServiciosAlquiler {
        try {
            return  itemDAO.consultarItem(itemId).getTarifaxDia();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("El item no esta registrado");
        }
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try {
            Cliente cliente =clienteDAO.consultarCliente(docu);
            if (cliente==null) throw new ExcepcionServiciosAlquiler("El cliente no esta registrado");
            else return cliente;

        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el cliente con identificacion "+docu,e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
        Cliente cliente = consultarCliente(idcliente);
        if (cliente==null)throw new ExcepcionServiciosAlquiler("el cliente no esta registrado");
        else return consultarCliente(idcliente).getRentados();
    }

    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.consultarClientes();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar clientes",e);
        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.consultarItem(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.consultarItems();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los items ",ex);
        }
    }

    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
        List<Cliente> clientes = consultarClientes();
        for (Cliente cliente:clientes) {
            ArrayList<ItemRentado> rentados = cliente.getRentados();
            for (ItemRentado item :rentados) {
                if( item.getItem().getId()==iditem) {
                    LocalDate fechaFin=item.getFechafinrenta().toLocalDate();
                    LocalDate fechaEntrego=fechaDevolucion.toLocalDate();
                    long diasRetraso = ChronoUnit.DAYS.between(fechaFin, fechaEntrego);
                    if(diasRetraso<0){
                        return 0;
                    }
                    return diasRetraso*valorMultaRetrasoxDia(item.getItem().getId());
                }
            }
        }
        throw  new ExcepcionServiciosAlquiler("El item"+iditem+"no se encuentra rentado");
    }


    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return tipoItemDAO.consultarTipoItem(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el tipo de item "+id,e);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try {
            return tipoItemDAO.consultarTiposItem();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los tipos de item ",e);
        }
    }

    @Override
    @Transactional
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
        LocalDate actual=date.toLocalDate();
        LocalDate entrega=actual.plusDays(numdias);
        if(numdias<1)throw  new ExcepcionServiciosAlquiler("el numero de dias debe ser mayor o igual a 1");
        if(consultarCliente(docu)==null)throw new ExcepcionServiciosAlquiler("El cliente no esta registrado");
        if(consultarItem(item.getId())==null)throw new ExcepcionServiciosAlquiler("El item no esta registrado");
        for (ItemRentado itemRentado:consultarCliente(docu).getRentados()) {
            if (itemRentado.getItem().getId() == item.getId())
                throw new ExcepcionServiciosAlquiler("Este item con id: " + item.getId() + " ya se encuentra rentado");
        }
        try {
            clienteDAO.agregarItemRentadoACliente(docu,item.getId(),date,java.sql.Date.valueOf(entrega));
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al agregar el item"
                    +item+" a los items rentados del cliente"+docu,e);
        }
    }

    @Override
    public void registrarTipoItem(TipoItem tipoItem) throws ExcepcionServiciosAlquiler {
        try {
            tipoItemDAO.save(tipoItem);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al insertar el tipo de Item "+tipoItem.getID(),e);
        }
    }

    @Override
    @Transactional
    public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
        try {
            clienteDAO.save(c);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al registrar el cliente "+c,e);
        }
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        Item item=consultarItem(iditem);
        if (item==null) throw new ExcepcionServiciosAlquiler("El item no existe");
        else if(numdias<1) throw new ExcepcionServiciosAlquiler("el numero de dias debe ser mayor o igual a 1");
        else return consultarItem(iditem).getTarifaxDia()*numdias;
    }

    @Override
    @Transactional
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        try {
            itemDAO.actualizarTarifaItem(id,tarifa);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al actualizar  el item "+id,e);
        }
    }
    @Override
    @Transactional
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
            itemDAO.save(i);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al cargar el item"+i,e);
        }
    }

    @Override
    @Transactional
    public void vetarCliente(long docu, boolean vetado) throws ExcepcionServiciosAlquiler {
        try {
            clienteDAO.vetarCliente(docu,vetado?1:0);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al actualizar la informacion del cliente"+docu,e);
        }
    }
}
