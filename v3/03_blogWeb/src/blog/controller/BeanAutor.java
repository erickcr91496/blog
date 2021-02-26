package blog.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import blog.controller.util.JSFUtil;
import blog.model.entities.Blog;
import blog.model.managers.ManagerAutor;

@Named
@SessionScoped
public class BeanAutor implements Serializable {

	private List<Blog> listaBlogs;
	@Inject
	private BeanLogin beanLogin;
	@EJB
	private ManagerAutor mAutor;
	private Blog nuevoBlog;
	private Blog blogEdit;
	
	public BeanAutor() {
		
		
	}
	@PostConstruct 
	public void inicializar() {
		listaBlogs = mAutor.findAllBlogsByIdUsuario(beanLogin.getIdUsuario());
		nuevoBlog = new Blog();
		blogEdit = new Blog();
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
	
}
