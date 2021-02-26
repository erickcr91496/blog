package blog.model.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import blog.model.entities.Usuario;

/**
 * Session Bean implementation class ManagerUsuarios
 */
@Stateless
@LocalBean
public class ManagerUsuarios {
	
	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public ManagerUsuarios() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * Validacion de login mediante la creacion de usuario y clave
	 * 
	 * @param idUsuario identificador unico en la bdd
	 * @param clave     Clave secreta
	 * @return la ruta de la pagina a donde se concede el acceso
	 */

	public String validarLogin(String idUsuario, String clave) throws Exception {

		// buscar al usuario en la bdd
		Usuario usuario = em.find(Usuario.class, idUsuario);
		// comprobamos si el usuario existe

		if (usuario == null)
			throw new Exception("Usuario ingresado no existe" + idUsuario);
		if (usuario.getActivo() == false)
			throw new Exception("usuario inactivo. consulte con el admin.");
		// verificacion de la contra
		if (usuario.getClave().equals(clave)) {
			// verificar si el usuario es admin
			if (usuario.getIdUsuario().equals("admin"))
				return "admin/index";
			return "autor/index";

		}
		throw new Exception("la clave es incorrecta");
	}

	public List<Usuario> findAllUsuarios() {
		return em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
	}

	public void crearUsuario(String idUsuario, String correo, String clave) throws Exception {
		Usuario u = new Usuario();
		if (idUsuario.equals("admin"))
			throw new Exception("no puede utilizar el id admin");
		u.setIdUsuario(idUsuario);
		u.setCorreo(correo);
		u.setClave(clave);
		u.setActivo(true);
		em.persist(u);
	}
	
	public void eliminarUsuario(String idUsuario) throws Exception{
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u==null) 
			throw new Exception("No existe el usuario indicado ("+idUsuario+")");
		//no se puede eliminar el usuario "admin"
		if(u.getIdUsuario().equals("admin"))
			throw new Exception("No se puede eliminar el usuario admin.");
		em.remove(u);
		
	}
	
	public void actualizarUsuario(Usuario usuario) throws Exception {
		//buscar objeto original
		Usuario u = em.find(Usuario.class, usuario.getIdUsuario());
		if(u==null)
			throw new Exception("No existe el usuario indicado ("+usuario.getIdUsuario()+")");
		u.setActivo(usuario.getActivo());
		u.setClave(usuario.getClave());
		u.setCorreo(usuario.getCorreo());
		em.merge(u);
	}
	
	public void cambiarClaveUsuario(String idUsario, String claveActual, 
				String nuevaClave,String nuevaClaveComprovacion) throws Exception {
		Usuario u = em.find(Usuario.class, idUsario);
		if (u==null)
			throw new Exception(" No existe el usuario indicado ("+idUsario+")");
		//comprobar clave actual:
		if(u.getClave().equals(claveActual)) {
			//comprobar la nueva clave
			if (nuevaClave.equals(nuevaClaveComprovacion)) {
				//se cambia la clave al coincidir todos los parametos:
				u.setClave(nuevaClave);
				em.merge(u);
				return;
			}
		}
		throw new Exception("Error en claves, porfavor verifique");
	}
		
	
}
