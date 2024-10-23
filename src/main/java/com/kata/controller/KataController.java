package com.kata.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kata.dto.TareasDto;
import com.kata.entity.TareasEntity;
import com.kata.request.RqCrearTarea;
import com.kata.response.GenericResponse;
import com.kata.service.ITareasService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3001" })
@RequestMapping(path = "/tasks")
public class KataController {

	@Autowired
	ITareasService iTareasService;

	@PostMapping(path = "/registraTarea", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GenericResponse registraTarea(@RequestBody RqCrearTarea body) {
		System.out.println("START Service registraTarea() ");
		boolean estado = true;
		String mensaje = "";
		try {
			TareasEntity tareasEntity = new TareasEntity(body);
			iTareasService.registrarTarea(tareasEntity);
			mensaje = "Tarea registrada exitosamente";
		} catch (Exception e) {
			System.out.println("Error --> " + e);
			if (e.getMessage().contains("could not execute statement")) {
				mensaje = "La longitud del campo descripción excede el limite permitido";
			} else {
				mensaje = "Falló el registro de la tarea, contacte al administrador";
			}
			estado = false;
		}
		return new GenericResponse(estado, mensaje, null);
	}

	@GetMapping(path = "/listarTareas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GenericResponse listarTareas() {
		System.out.println("START Service listarTareas() ");
		boolean estado = true;
		String mensaje = "";
		List<TareasDto> listaTareasDto = new ArrayList<>();
		try {
			List<TareasEntity> listaTareas = iTareasService.getTareasList();
			for (TareasEntity idT : listaTareas) {
				listaTareasDto.add(new TareasDto(idT));
			}
			mensaje = "Consulta exitosa";
		} catch (Exception e) {
			System.out.println("Error --> " + e);
			mensaje = "Falló la consulta de tareas, contacte al administrador";
			estado = false;
		}
		return new GenericResponse(estado, mensaje, listaTareasDto);
	}
	
	@PutMapping(path = "/actualizarTarea/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GenericResponse actualizarTarea(@PathVariable Long id, @RequestBody TareasDto body) {
		boolean estado = true;
		String mensaje = "";		
		try {
			TareasEntity tareaEntity = iTareasService.getTareaPorId(id);
			tareaEntity.setTitulo(body.getTitulo());
			tareaEntity.setDescripcion(body.getDescripcion());
			tareaEntity.setEstado(body.getEstado());
			iTareasService.registrarTarea(tareaEntity);
			mensaje = "Tarea actualizada exitosamente";
		} catch (Exception e) {
			System.out.println("Error --> " + e);
			mensaje = "Falló la actualizacion de la tarea, contacte al administrador";
			estado = false;
		}
		return new GenericResponse(estado, mensaje, null);
	}
	
	@DeleteMapping(path = "/eliminarTarea/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GenericResponse eliminarTarea(@PathVariable Long id) {
		boolean estado = true;
		String mensaje = "";		
		try {
			iTareasService.eliminarTarea(id);
			mensaje = "Tarea eliminada exitosamente";
		} catch (Exception e) {
			System.out.println("Error --> " + e);
			mensaje = "Falló la actualizacion de la tarea, contacte al administrador";
			estado = false;
		}
		return new GenericResponse(estado, mensaje, null);
	}
	
}