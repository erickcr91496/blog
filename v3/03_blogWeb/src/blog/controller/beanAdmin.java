package blog.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
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
		private Usuario usuarioEdit;
		
		@EJB
		private ManagerUsuarios mUsuarios;
		private String nuevaClave;
		private String nuevaClaveComprobacion;
		// referencia al bean de sesion cuando el usuario realizo login:
		@Inject
		private BeanLogin beanLogin;
		
	public beanAdmin() {
	
		
	}
	@PostConstruct
	public void inicializar() {
		listaUsuarios = mUsuarios.findAllUsuarios();
	}
	
	public void actionListenerCrearUsaurio() {
		try {
			mUsuarios.crearUsuario(idUsuario, correo, clave);
			JSFUtil.createMensajeInfo("Usuario creado");

			listaUsuarios = mUsuarios.findAllUsuarios();
			idUsuario="";
			correo="";
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerEliminarUsuario(String idUsuario) {
		try {
			mUsuarios.eliminarUsuario(idUsuario);
			listaUsuarios = mUsuarios.findAllUsuarios();

			JSFUtil.createMensajeInfo("Usuario "+idUsuario+" eliminado.");
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}
	public void actionListenerSeleccionarUsuario(Usuario usuario) {
		usuarioEdit = usuario;
		System.out.println("Usuario seleccionado: "+usuarioEdit.getIdUsuario());
	}
	
	public void actionListenerActualizarUsuario() {
		try {
			mUsuarios.actualizarUsuario(usuarioEdit);
			JSFUtil.createMensajeInfo("Usuario actualizado");
		} catch (Exception e) {
			JSFUtil.createMensajeError("Usuario actualizado");
			e.printStackTrace();
		}
	}
	
	public String actionPaginaCambioClave() {
		nuevaClave="";
		nuevaClaveComprobacion="";
		return "clave";
	}
	
	public void actionListenerCambiarClave() {
		try {
			mUsuarios.cambiarClaveUsuario(beanLogin.getIdUsuario(),clave,nuevaClave,nuevaClaveComprobacion);
			JSFUtil.createMensajeInfo("Clave actualizada");
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String getNuevaClave() {
		return nuevaClave;
	}
	public void setNuevaClave(String nuevaClave) {
		this.nuevaClave = nuevaClave;
	}
	public String getNuevaClaveComprobacion() {
		return nuevaClaveComprobacion;
	}
	public void setNuevaClaveComprobacion(String nuevaClaveComprobacion) {
		this.nuevaClaveComprobacion = nuevaClaveComprobacion;
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
	public Usuario getUsuarioEdit() {
		return usuarioEdit;
	}
	public void setUsuarioEdit(Usuario usuarioEdit) {
		this.usuarioEdit = usuarioEdit;
	}
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}
	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}


}
