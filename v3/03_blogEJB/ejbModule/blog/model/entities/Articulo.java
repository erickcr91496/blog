package blog.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the articulo database table.
 * 
 */
@Entity
@Table(name="articulo")
@NamedQuery(name="Articulo.findAll", query="SELECT a FROM Articulo a")
public class Articulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_articulo", unique=true, nullable=false)
	private Integer idArticulo;

	@Column(nullable=false, length=2147483647)
	private String contenido;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion", nullable=false)
	private Date fechaCreacion;

	@Column(name="fecha_modificacion", nullable=false)
	private Timestamp fechaModificacion;

	@Column(nullable=false)
	private Integer likes;

	@Column(nullable=false, precision=7, scale=2)
	private BigDecimal recaudado;

	@Column(nullable=false, length=100)
	private String titulo;

	//bi-directional many-to-one association to Blog
	@ManyToOne
	@JoinColumn(name="id_blog", nullable=false)
	private Blog blog;

	//bi-directional many-to-one association to Clasificacion
	@ManyToOne
	@JoinColumn(name="id_clasificacion", nullable=false)
	private Clasificacion clasificacion;

	public Articulo() {
	}

	public Integer getIdArticulo() {
		return this.idArticulo;
	}

	public void setIdArticulo(Integer idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public BigDecimal getRecaudado() {
		return this.recaudado;
	}

	public void setRecaudado(BigDecimal recaudado) {
		this.recaudado = recaudado;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Blog getBlog() {
		return this.blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public Clasificacion getClasificacion() {
		return this.clasificacion;
	}

	public void setClasificacion(Clasificacion clasificacion) {
		this.clasificacion = clasificacion;
	}

}