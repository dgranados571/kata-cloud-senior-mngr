package com.kata.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kata.entity.TareasEntity;
import com.kata.repo.ITareasRepo;

@Service
public class TareasService implements ITareasService {
	
	@Autowired
	ITareasRepo iTareasRepo;

	@Override
	public List<TareasEntity> getTareasList() {
		return iTareasRepo.findAll();
	}

	@Override
	public void registrarTarea(TareasEntity tareasEntity) {
		iTareasRepo.save(tareasEntity);		
	}

	@Override
	public TareasEntity getTareaPorId(long id) {		
		return iTareasRepo.getById(id);
	}

	@Override
	public void eliminarTarea(long id) {
		iTareasRepo.deleteById(id);
	}

}
