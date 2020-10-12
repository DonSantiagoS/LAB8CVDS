package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param ("idcli") long id);
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param idCliente
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
    public void agregarItemRentadoACliente(@Param("idcl") long idCliente,
            @Param("idit") int idit,
            @Param("Fechai") Date fechainicio,
            @Param("Fechaf") Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarCliente();
    
    /**
     * Agrega un nuevo cliente 
     * @param c
     */
    public void insertarCliente(@Param("cliente") Cliente c);
    public void vetarCliente(@Param("cliente") long idCliente,@Param("estado") int estado);
    
}
