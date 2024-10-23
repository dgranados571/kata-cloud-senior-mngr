package com.kata.dto;

import com.kata.entity.TareasEntity;

public class TareasDto {
	
	private long id;
	private String titulo;
	private String descripcion;
	private String estado;
	
	public TareasDto() {
		super();
	}

	public TareasDto(TareasEntity tareasEntity) {
		super();
		this.id = tareasEntity.getId();
		this.titulo = tareasEntity.getTitulo();
		this.descripcion = tareasEntity.getDescripcion();
		this.estado = tareasEntity.getEstado();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	


}
