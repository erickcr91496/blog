package blog.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import blog.controller.util.JSFUtil;
import blog.model.entities.Articulo;
import blog.model.entities.Blog;
import blog.model.entities.Clasificacion;
import blog.model.managers.ManagerAutor;
import blog.model.managers.ManagerClasificacion;

@Named
@SessionScoped
public class BeanAutor implements Serializable {

	private List<Blog> listaBlogs;
	@Inject
	private BeanLogin beanLogin;
	@EJB
	private ManagerAutor mAutor;
	@EJB
	private ManagerClasificacion mClasificacion;
	private Blog nuevoBlog;
	private Blog blogEdit;
	private Articulo nuevoArticulo;
	private List<Articulo> listaArticulos;
	private Articulo articuloEdit;
	private List<Clasificacion> listaClasificacions;
	private Blog blogSeleccionado;
	private Integer idClasificacionSeleccionado;
	
	public BeanAutor() {
		
		
	}
	@PostConstruct 
	public void inicializar() {
		listaBlogs = mAutor.findAllBlogsByIdUsuario(beanLogin.getIdUsuario());
		nuevoBlog = new Blog();
		blogEdit = new Blog();
		nuevoArticulo = new Articulo();
		articuloEdit = new Articulo();
	}
	
	public void actionListenerCrearBlog () {
		try {
			mAutor.crearBlog(nuevoBlog,beanLogin.getIdUsuario());
			// cada vez que se crea un blog, se actualiza la lista de consulta
			listaBlogs=mAutor.findAllBlogsByIdUsuario(beanLogin.getIdUsuario());
			nuevoBlog = new Blog();
			JSFUtil.createMensajeInfo("Se ha creado el blog");
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void acionListenerEliminarBlog(Integer idBlog) {
		try {
			mAutor.eliminarBlog(idBlog);
			listaBlogs=mAutor.findAllBlogsByIdUsuario(beanLogin.getIdUsuario());
			JSFUtil.createMensajeInfo("Blog eliminado");
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionConsultarArticulosByBlog(Blog blog) {
		listaArticulos=mAutor.findArticulosByBlog(blog.getIdBlog());
		listaClasificacions=mClasificacion.findAllClasificacion();
		blogSeleccionado=blog;
		return "articulos?faces-redirect=true";
	}
	
	public void actionListenerCrearArticulo() {
		try {
			mAutor.crearArticulo(nuevoArticulo, blogSeleccionado.getIdBlog(), idClasificacionSeleccionado);
			listaArticulos = mAutor.findArticulosByBlog(blogSeleccionado.getIdBlog());
			JSFUtil.createMensajeInfo("Articulo creado");
			nuevoArticulo = new Articulo(); // para limpiar el formulario de nuevo articulo
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.createMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerSeleccionarArticulo(Articulo articulo) {
		articuloEdit= articulo;
		idClasificacionSeleccionado = articulo.getClasificacion().getIdClasificacion();
	}
	
	public void actionListenerActualizarArticulo() {
		mAutor.actualizarArticulo(articuloEdit, idClasificacionSeleccionado);
		listaArticulos=mAutor.findArticulosByBlog(blogSeleccionado.getIdBlog());
		JSFUtil.createMensajeInfo("Articulo actualizado");
	}
	
	public List<Blog> getListaBlogs() {
		return listaBlogs;
	}
	public void setListaBlogs(List<Blog> listaBlogs) {
		this.listaBlogs = listaBlogs;
	}
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}
	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public Blog getNuevoBlog() {
		return nuevoBlog;
	}
	public void setNuevoBlog(Blog nuevoBlog) {
		this.nuevoBlog = nuevoBlog;
	}
	public Blog getBlogEdit() {
		return blogEdit;
	}
	public void setBlogEdit(Blog blogEdit) {
		this.blogEdit = blogEdit;
	}
	public Articulo getNuevoArticulo() {
		return nuevoArticulo;
	}
	public void setNuevoArticulo(Articulo nuevoArticulo) {
		this.nuevoArticulo = nuevoArticulo;
	}
	public List<Articulo> getListaArticulos() {
		return listaArticulos;
	}
	public void setListaArticulos(List<Articulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}
	public Articulo getArticuloEdit() {
		return articuloEdit;
	}
	public void setArticuloEdit(Articulo articuloEdit) {
		this.articuloEdit = articuloEdit;
	}
	public List<Clasificacion> getListaClasificacions() {
		return listaClasificacions;
	}
	public void setListaClasificacions(List<Clasificacion> listaClasificacions) {
		this.listaClasificacions = listaClasificacions;
	}
	public Blog getBlogSeleccionado() {
		return blogSeleccionado;
	}
	public void setBlogSeleccionado(Blog blogSeleccionado) {
		this.blogSeleccionado = blogSeleccionado;
	}
	public Integer getIdClasificacionSeleccionado() {
		return idClasificacionSeleccionado;
	}
	public void setIdClasificacionSeleccionado(Integer idClasificacionSeleccionado) {
		this.idClasificacionSeleccionado = idClasificacionSeleccionado;
	}
	
	
}
