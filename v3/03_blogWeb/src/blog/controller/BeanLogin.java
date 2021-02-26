package blog.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import blog.controller.util.JSFUtil;
import blog.model.managers.ManagerUsuarios;

@Named
@SessionScoped
public class BeanLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idUsuario;
	private String clave;
	
	@EJB 
	private ManagerUsuarios managerUsuarios;
	public BeanLogin() {

	}

	public String actionLogin() {
		// este es metodo action controla el flujo de la navegacion
		//dependiendo del usuario ingresado
		String ruta ="";
		try {
			ruta= managerUsuarios.validarLogin(idUsuario, clave);
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
		}
		return ruta;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}
