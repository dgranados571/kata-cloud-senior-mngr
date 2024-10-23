package com.kata.service;

import java.util.List;
import com.kata.entity.TareasEntity;

public interface ITareasService {
	
	public List<TareasEntity> getTareasList();
	public void registrarTarea(TareasEntity tareasEntity);
	public TareasEntity getTareaPorId(long id);
	public void eliminarTarea(long id);
}
