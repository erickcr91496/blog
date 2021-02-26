package blog.model.managers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import blog.model.entities.Articulo;
import blog.model.entities.Blog;
import blog.model.entities.Clasificacion;
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
	 * Finder para obtener la lista de blogs correspondientes a un usuario. Este
	 * metodo utiliza parametros en la consulta (setParameter), lo que evita ataques
	 * de inyeccion SQL.
	 * 
	 * @param idUsuario El id del autor.
	 * @return Lista de blogs.
	 */
	public List<Blog> findAllBlogsByIdUsuario(String idUsuario) {
		Query q = em.createQuery("select b from Blog b where b.usuario.idUsuario=:idUsuario", Blog.class);
		q.setParameter("idUsuario", idUsuario);
		return q.getResultList();
	}

	public void crearBlog(String idUsuario, String nombreBlog, String descripcion) throws Exception {
		Blog b = new Blog();
		b.setNombreBlog(nombreBlog);
		b.setDescripcion(descripcion);
		b.setFechaCreacion(new Date());
		// Para asignar el id del usuario autor, se debe obtener la referencia al objeto
		// Usuario;
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u == null)
			throw new Exception("No existe el usuario indicado");
		b.setUsuario(u);
		em.persist(b);

	}

	public void crearBlog(Blog nuevoBlog, String idUsuario) throws Exception {
		crearBlog(idUsuario, nuevoBlog.getNombreBlog(), nuevoBlog.getDescripcion());
	}

	public void eliminarBlog(Integer idBlog) throws Exception {
		Blog b = em.find(Blog.class, idBlog);
		if (b == null)
			throw new Exception("No se encuentra el blog indicado");
		em.remove(b);
	}
	//METODOS PARA EL MANEJO DE ARTICULOS:
	/**
	 * Finder para consultar todos los articulos
	 * @param idBlog El identificador del blog
	 * @return Lista de articulos
	 */
	public List<Articulo> findArticulosByBlog(Integer idBlog){
		Query q = em.createQuery("select a from Articulo a where a.blog.idBlog=:idBlog", Articulo.class);
		q.setParameter("idBlog", idBlog);
		return q.getResultList();
	}
	/**
	 * Metodo para crear un nuevo articulo
	 * @param nuevoArticulo El nuevo objeto articulo que debera guardarse
	 * @param idBlog El id del blog al que pertenece el articulo
	 * @param idClasificacion el id de clasificacion del articulo
	 * @throws Exception 
	 */
	public void crearArticulo(Articulo nuevoArticulo,Integer idBlog,Integer idClasificacion) throws Exception {
		if (em.contains(nuevoArticulo))
			throw new Exception("El nuevo articulo indicado ya existe en la base de datos");
		Blog b  = em.find(Blog.class,idBlog);
		Clasificacion c = em.find(Clasificacion.class, idClasificacion);
		//asignamos las claves foraneas (objetos relacionados);
		nuevoArticulo.setBlog(b);
		nuevoArticulo.setClasificacion(c);
		em.persist(nuevoArticulo);
	}
	
	public void actualizarArticulo(Articulo articuloEdit,Integer idClasificacion) {
		//buscamos articulo original;
		Articulo a =em.find(Articulo.class,articuloEdit.getIdArticulo());
		//objetos relacionados:
		Clasificacion c = em.find(Clasificacion.class, idClasificacion);
		a.setContenido(articuloEdit.getContenido());
		Timestamp t = new Timestamp(new Date().getTime());
		a.setFechaModificacion(t);
		a.setTitulo(articuloEdit.getTitulo());
		em.merge(a);
	}
		
	

}
