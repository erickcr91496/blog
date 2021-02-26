package blog.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import blog.controller.util.JSFUtil;
import blog.model.entities.Usuario;
import blog.model.managers.ManagerUsuarios;

@Named
@SessionScoped
public class beanAdmin implements Serializable {

		private List<Usuario> listaUsuarios;
		private String idUsuario;
		private String correo;
		private String clave;
		@EJB
		private ManagerUsuarios mUsuarios;
		
	public beanAdmin() {
	
		
	}
	@PostConstruct
	public void inicializar() {
		listaUsuarios = mUsuarios.findAllUsuarios();
	}
	
	public void actionListenerCrearUsaurio() {
		try {
			mUsuarios.crearUsuario(idUsuario, correo, clave);
			//actualizamos la lista de usuarios;
			listaUsuarios = mUsuarios.findAllUsuarios();
			JSFUtil.createMensajeInfo("Usuario creado");
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}


}
