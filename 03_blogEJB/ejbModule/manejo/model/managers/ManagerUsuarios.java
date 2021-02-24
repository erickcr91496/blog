package manejo.model.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.entities.Usuario;

/**
 * Session Bean implementation class ManagerUsuarios
 */
@Stateless
@LocalBean
public class ManagerUsuarios {

	@PersistenceContext
	private EntityManager em;
	
    public ManagerUsuarios() {

    }
    /**
     * Validacion de login mendiante la credencial de usuario y clave.
     * @param idUsuario identificador unico de usuario en la base de datos.
     * @param clave Clave secreta
     * @return la ruta de la pagina a donde se concede el acceso.
     */
    public String validarLogin(String idUsuario,String clave) throws Exception{
    	// buscar el diccionario mediante su identificador (clave primaria);
    	Usuario usuario= em.find(Usuario.class, idUsuario);
    	if (usuario==null) 
			throw new Exception("Error el usuario no existe ("+idUsuario+")");
		if (usuario.getActivo()==false) 
				throw new Exception("El usuario esta inactivo, comuniquese con el administrador");
				//verificar clave
		if(usuario.getClave().equals(clave)) {
				// verificar si es usuario administrador:
				if (usuario.getIdUsuario().equals("admin")) 
					return "admin/index";
				return "autor/index";
			}   	
    	//caso contrario significa que la clacve esta erronea:
    	throw new Exception("Error la clave es incorrecta");
    }
    
    public List<Usuario> findAllUsuarios(){
    	return em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
    }

    public void crearUsuario(String idUsuario, String correo, String clave) throws Exception{
    	Usuario u = new Usuario();
    		if (idUsuario.equals("admin")) 
				throw new Exception("No puede utilizar el id admin");   	
    		u.setIdUsuario(idUsuario);
    		u.setCorreo(correo);
    		u.setClave(clave);
    		u.setActivo(true);
    		em.persist(u);
    	}
    
}















