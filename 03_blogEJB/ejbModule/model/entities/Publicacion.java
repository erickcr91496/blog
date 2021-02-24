package model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the publicacion database table.
 * 
 */
@Entity
@Table(name="publicacion")
@NamedQuery(name="Publicacion.findAll", query="SELECT p FROM Publicacion p")
public class Publicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_publicacion", unique=true, nullable=false)
	private Integer idPublicacion;

	@Column(nullable=false, length=3000)
	private String contenido;

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

	public Publicacion() {
	}

	public Integer getIdPublicacion() {
		return this.idPublicacion;
	}

	public void setIdPublicacion(Integer idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
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

}