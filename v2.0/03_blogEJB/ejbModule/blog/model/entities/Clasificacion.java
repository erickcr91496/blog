package blog.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the clasificacion database table.
 * 
 */
@Entity
@Table(name="clasificacion")
@NamedQuery(name="Clasificacion.findAll", query="SELECT c FROM Clasificacion c")
public class Clasificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_clasificacion", unique=true, nullable=false)
	private Integer idClasificacion;

	@Column(nullable=false, length=50)
	private String descripcion;

	@Column(nullable=false)
	private Integer orden;

	//bi-directional many-to-one association to Articulo
	@OneToMany(mappedBy="clasificacion")
	private List<Articulo> articulos;

	public Clasificacion() {
	}

	public Integer getIdClasificacion() {
		return this.idClasificacion;
	}

	public void setIdClasificacion(Integer idClasificacion) {
		this.idClasificacion = idClasificacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<Articulo> getArticulos() {
		return this.articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public Articulo addArticulo(Articulo articulo) {
		getArticulos().add(articulo);
		articulo.setClasificacion(this);

		return articulo;
	}

	public Articulo removeArticulo(Articulo articulo) {
		getArticulos().remove(articulo);
		articulo.setClasificacion(null);

		return articulo;
	}

}