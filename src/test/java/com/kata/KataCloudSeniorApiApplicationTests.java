package com.kata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.kata.controller.KataController;
import com.kata.dto.TareasDto;
import com.kata.entity.TareasEntity;
import com.kata.request.RqCrearTarea;
import com.kata.response.GenericResponse;
import com.kata.service.ITareasService;

@SpringBootTest
class KataCloudSeniorApiApplicationTests {

	@Mock
	private ITareasService iTareasService;

	@InjectMocks
	private KataController kataController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void test1() {
		RqCrearTarea rqCrearTarea = new RqCrearTarea();
		rqCrearTarea.setTitulo("Tarea test 1");
		rqCrearTarea.setDescripcion("Descripcion Tarea test 1");		
		GenericResponse response = kataController.registraTarea(rqCrearTarea);
		verify(iTareasService, times(1)).registrarTarea(any(TareasEntity.class));
		assertTrue(response.isEstado());
		assertEquals("Tarea registrada exitosamente", response.getMensaje());
	}

	@Test
	void test2() {
		doNothing().when(iTareasService).registrarTarea(any(TareasEntity.class));
		RqCrearTarea rqCrearTarea = new RqCrearTarea();
		rqCrearTarea.setDescripcion("Tarea test 2");
		GenericResponse response = kataController.registraTarea(rqCrearTarea);
		verify(iTareasService, times(1)).registrarTarea(any(TareasEntity.class));
		assertTrue(response.isEstado());
		assertEquals("Tarea registrada exitosamente", response.getMensaje());
	}

	@Test
	void test3() { 
		doThrow(new RuntimeException("could not execute statement")).when(iTareasService).registrarTarea(any(TareasEntity.class));
		RqCrearTarea rqCrearTarea = new RqCrearTarea();
		rqCrearTarea.setDescripcion("Descripción larga");
		GenericResponse response = kataController.registraTarea(rqCrearTarea);
		assertFalse(response.isEstado());
		assertEquals("La longitud del campo descripción excede el limite permitido", response.getMensaje());
	}
	
	@Test
	void test4() { 
		doThrow(new RuntimeException("Generic error")).when(iTareasService).registrarTarea(any(TareasEntity.class));
		RqCrearTarea rqCrearTarea = new RqCrearTarea();
		rqCrearTarea.setDescripcion("Descripción larga");
		GenericResponse response = kataController.registraTarea(rqCrearTarea);
		assertFalse(response.isEstado());
		assertEquals("Falló el registro de la tarea, contacte al administrador", response.getMensaje());
	}	
	
	@Test
	void test5() {
		List<TareasEntity> arrayTaskResponse = new ArrayList<TareasEntity>();
		when(iTareasService.getTareasList()).thenReturn(arrayTaskResponse);
		GenericResponse response = kataController.listarTareas();
		assertTrue(response.isEstado());
		assertEquals(arrayTaskResponse.getClass(), response.getObjeto().getClass());
	}

	@Test
	void test6() {
		doThrow(new RuntimeException("could not execute statement")).when(iTareasService).getTareasList();
		GenericResponse response = kataController.listarTareas();
		assertFalse(response.isEstado());
		assertEquals("Falló la consulta de tareas, contacte al administrador", response.getMensaje());
	}
	
	@Test
	void test7() {
	    Long tareaId = 1L;
	    TareasDto tareasDto = new TareasDto();
	    tareasDto.setTitulo("Tarea actualizada");
	    tareasDto.setDescripcion("Descripción actualizada");
	    tareasDto.setEstado("COMPLETADA");

	    TareasEntity tareaEntity = new TareasEntity();
	    tareaEntity.setId(tareaId);
	    tareaEntity.setTitulo("Tarea anterior");
	    tareaEntity.setDescripcion("Descripción anterior");
	    tareaEntity.setEstado("");

	    when(iTareasService.getTareaPorId(tareaId)).thenReturn(tareaEntity);
	    doNothing().when(iTareasService).registrarTarea(any(TareasEntity.class));
	    GenericResponse response = kataController.actualizarTarea(tareaId, tareasDto);
	    assertTrue(response.isEstado());
	    assertEquals("Tarea actualizada exitosamente", response.getMensaje());
	    verify(iTareasService, times(1)).getTareaPorId(tareaId);
	    verify(iTareasService, times(1)).registrarTarea(any(TareasEntity.class));
	}
	
	@Test
	void test8() {
	    Long tareaId = 1L;
	    TareasDto tareasDto = new TareasDto();
	    tareasDto.setTitulo("Tarea actualizada");
	    tareasDto.setDescripcion("Descripción actualizada");
	    tareasDto.setEstado("COMPLETADA");
	    when(iTareasService.getTareaPorId(tareaId)).thenThrow(new RuntimeException("No se encontró la tarea"));
	    GenericResponse response = kataController.actualizarTarea(tareaId, tareasDto);
	    assertFalse(response.isEstado());
	    assertEquals("Falló la actualizacion de la tarea, contacte al administrador", response.getMensaje());
	    verify(iTareasService, times(1)).getTareaPorId(tareaId);
	    verify(iTareasService, never()).registrarTarea(any(TareasEntity.class)); // Valida que no se llame el sericio registrarTarea()
	}



}
