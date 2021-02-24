package model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the blog database table.
 * 
 */
@Entity
@Table(name="blog")
@NamedQuery(name="Blog.findAll", query="SELECT b FROM Blog b")
public class Blog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_blog", unique=true, nullable=false)
	private Integer idBlog;

	@Column(nullable=false, length=300)
	private String descripcion;

	@Column(name="nombre_blog", nullable=false, length=50)
	private String nombreBlog;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="id_categoria", nullable=false)
	private Categoria categoria;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;

	//bi-directional many-to-one association to Publicacion
	@OneToMany(mappedBy="blog")
	private List<Publicacion> publicacions;

	public Blog() {
	}

	public Integer getIdBlog() {
		return this.idBlog;
	}

	public void setIdBlog(Integer idBlog) {
		this.idBlog = idBlog;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreBlog() {
		return this.nombreBlog;
	}

	public void setNombreBlog(String nombreBlog) {
		this.nombreBlog = nombreBlog;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Publicacion> getPublicacions() {
		return this.publicacions;
	}

	public void setPublicacions(List<Publicacion> publicacions) {
		this.publicacions = publicacions;
	}

	public Publicacion addPublicacion(Publicacion publicacion) {
		getPublicacions().add(publicacion);
		publicacion.setBlog(this);

		return publicacion;
	}

	public Publicacion removePublicacion(Publicacion publicacion) {
		getPublicacions().remove(publicacion);
		publicacion.setBlog(null);

		return publicacion;
	}

}