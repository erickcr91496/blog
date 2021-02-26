package blog.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
	private boolean validacionLogin;

	@EJB
	private ManagerUsuarios managerUsuarios;

	public BeanLogin() {

	}

	public String actionLogin() {
		// este es metodo action controla el flujo de la navegacion
		// dependiendo del usuario ingresado
		String ruta = "";
		validacionLogin = false;
		try {
			ruta = managerUsuarios.validarLogin(idUsuario, clave);
			// si el acceso es correcto, se activo la variable:
			validacionLogin = true;
		} catch (Exception e) {
			JSFUtil.createMensajeError(e.getMessage());
		}
		return ruta;
	}

	// metodo de comprobacion para las paginas
	public void actionComprobarLogin() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			// el control de acceso se lo realiza comprobando las rutas
			System.out.println("RequestContextPath:" + ec.getRequestContextPath());
			System.out.println("RequestContextPath:" + ec.getRequestPathInfo());
			String path = ec.getRequestPathInfo();
			// mostramos el id del usuario para depuracion en consola:
			System.out.println("Id del usuario: "+idUsuario);
			// la pagina del login no requiere comprobacion:
			if (path.equals("/index.xhtml"))
				return;

			// comprobamos si el usuario hizo login:
			if (validacionLogin == false) {
				// no hizo login, entonces se redirecciona a la pagina inicial
				ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
			} else {
				// caso contrario, el usuario si hizo login, pero se
				// verifica si tiene acceso a una pagina determiada:
				if (idUsuario.equals("admin")) {
					// si el usuario es administrador, solo puede acceder a las paginas
					// con la ruta /admin/
					if (path.contains("/admin/") == false)
						ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
					else
						return;
				}
				// caso contrario es un autor de blog:
				if (path.contains("/autir/") == false)
					ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String actionCerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index?faces-redirect=true";
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

	public boolean isValidacionLogin() {
		return validacionLogin;
	}

	public void setValidacionLogin(boolean validacionLogin) {
		this.validacionLogin = validacionLogin;
	}

}
