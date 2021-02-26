package blog.model.managers;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import blog.model.entities.Blog;
import blog.model.entities.Usuario;

/**
 * Session Bean implementation class ManagerAutor
 */
@Stateless
@LocalBean
public class ManagerAutor {
	@PersistenceContext
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public ManagerAutor() {
    
    }
    // METODOS PARA MANEJO DE BLOGS
    
    /**
     * Finder para obtener la lista de blogs correspondientes a un usuario.
     * Este metodo utiliza parametros en la consulta (setParameter), lo que
     * evita ataques de inyeccion SQL.
     * @param idUsuario El id del autor.
     * @return Lista de blogs.
     */
    public List<Blog> findAllBlogsByIdUsuario(String idUsuario) {
    	Query q = em.createQuery("select b from Blog b where b.usuario.idUsuario=:idUsuario",Blog.class);
    	q.setParameter("idUsuario", idUsuario);
    	return q.getResultList();
    }
    
    public void crearBlog(String idUsuario,String nombreBlog, String descripcion) throws Exception {
    	Blog b = new Blog();
    	b.setNombreBlog(nombreBlog);
    	b.setDescripcion(descripcion);
    	b.setFechaCreacion(new Date());
    	// Para asignar el id del usuario autor, se debe obtener la referencia al objeto Usuario;
    	Usuario u = em.find(Usuario.class, idUsuario);
    	if (u==null) 
    		throw new Exception("No existe el usuario indicado");
    	b.setUsuario(u);
    	em.persist(b);
    	
    } 
    public void crearBlog(Blog nuevoBlog, String idUsuario) throws Exception {
    	crearBlog(idUsuario,nuevoBlog.getNombreBlog(),nuevoBlog.getDescripcion());
    }

}
