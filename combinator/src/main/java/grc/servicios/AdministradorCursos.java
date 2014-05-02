package grc.servicios;

import grc.dao.CursoDAO;
import grc.dominio.Curso;
import grc.dominio.Materia;
import grc.dominio.MateriaAprobada;
import grc.dominio.PlanEstudio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdministradorCursos
{

	/**
	 * Devuelve los cursos que se pueden cursar filtrando el plan de estudio y
	 * materias aprobadas
	 */
	public Set<Curso> getCursosDisponibles(PlanEstudio pe, List<MateriaAprobada> matAprobadas)
			throws Exception
	{
		List<Materia> materiasAprobadas = new ArrayList<Materia>();
		for (MateriaAprobada m : matAprobadas)
		{
			materiasAprobadas.add(m.getMateriaAprobada());
		}
		

		Set<Curso> cursosDisponibles = CursoDAO.getInstancia().getCursosPorCarrera(pe.getCarrera());
		quitarMateriasAprobadas(cursosDisponibles, materiasAprobadas);
		
		return quitarCursosSinCorrelativas(cursosDisponibles, materiasAprobadas, pe);
	}

	private Set<Curso> quitarCursosSinCorrelativas(Set<Curso> cursosDisponibles,
			List<Materia> materiasAprobadas, PlanEstudio pe)
	{
		Set<Curso> cursos = new HashSet<Curso>();
		for (Curso c : cursosDisponibles)
		{
			if (tieneCorrelativas(pe, c.getMateria(), materiasAprobadas))
			{
				cursos.add(c);
			}

		}
		return cursos;
	}

	private void quitarMateriasAprobadas(Set<Curso> cursosDisponibles, List<Materia> materiasAprobadas)
	{
		List<Curso> copia = new ArrayList<Curso>(cursosDisponibles);
		for (Curso c : copia)
		{
			for (Materia m : materiasAprobadas)
			{
				if (c.getMateria().getId() == m.getId())
				{
					cursosDisponibles.remove(c);
					continue;
				}
			}
		}
	}

	private boolean tieneCorrelativas(PlanEstudio pe, Materia materiaACursar,
			List<Materia> materiasAprobadas)
	{
		if (pe.getCorrelativas().containsKey(materiaACursar))
		{
			if (materiaACursar.getNombre().equalsIgnoreCase("Laboratorio Interdisciplinario"))
			{
				return materiasAprobadas.size() >= 11;
			}
			return materiasAprobadas.containsAll(pe.getCorrelativas().get(materiaACursar));
		}

		return false;
	}

}
