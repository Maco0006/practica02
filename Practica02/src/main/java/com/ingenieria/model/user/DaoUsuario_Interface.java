package com.ingenieria.model.user;

import java.util.List;


public interface DaoUsuario_Interface {
	
	public void insertaUsuario(DtoUsuario usuario);
	public Boolean buscaNombre(String nombreuser);
	public Boolean buscaEmail(String emailuser);
	public DtoUsuario extraerUsuario(String email);
	public void modificaDatos(DtoUsuario usuario, String email);
	public List<DtoUsuario> leeUsuarios();
	
}

