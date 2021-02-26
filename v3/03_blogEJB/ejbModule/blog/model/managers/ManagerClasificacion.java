package blog.model.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import blog.model.entities.Clasificacion;

/**
 * Session Bean implementation class ManagerClasificacion
 */
@Stateless
@LocalBean
public class ManagerClasificacion {
	@PersistenceContext
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public ManagerClasificacion() {
        // TODO Auto-generated constructor stub
    }
    public List<Clasificacion> findAllClasificacion(){
    	Query q = em.createQuery("select c from Clasificacion c order by c.orden",Clasificacion.class);
    	return q.getResultList();
    }

}
