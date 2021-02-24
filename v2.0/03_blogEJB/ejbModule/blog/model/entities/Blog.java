package blog.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
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

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion", nullable=false)
	private Date fechaCreacion;

	@Column(name="nombre_blog", nullable=false, length=50)
	private String nombreBlog;

	//bi-directional many-to-one association to Articulo
	@OneToMany(mappedBy="blog")
	private List<Articulo> articulos;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;

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

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getNombreBlog() {
		return this.nombreBlog;
	}

	public void setNombreBlog(String nombreBlog) {
		this.nombreBlog = nombreBlog;
	}

	public List<Articulo> getArticulos() {
		return this.articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public Articulo addArticulo(Articulo articulo) {
		getArticulos().add(articulo);
		articulo.setBlog(this);

		return articulo;
	}

	public Articulo removeArticulo(Articulo articulo) {
		getArticulos().remove(articulo);
		articulo.setBlog(null);

		return articulo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}