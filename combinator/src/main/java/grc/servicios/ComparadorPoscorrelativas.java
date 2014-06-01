package grc.servicios;

import grc.dominio.Curso;
import grc.dominio.PlanEstudio;

public class ComparadorPoscorrelativas extends Comparador
{
	PlanEstudio planEstudio;
	
	@Override
	public int compare(Recomendacion o1, Recomendacion o2) 
	{
		Integer cantidadRecomendacion1 = contarCantidadPoscorrelativas(o1, planEstudio);
		Integer cantidadRecomendacion2 = contarCantidadPoscorrelativas(o2, planEstudio);
		return cantidadRecomendacion2 - cantidadRecomendacion1;
	}
	
	public Integer contarCantidadPoscorrelativas(Recomendacion recomendacion, PlanEstudio planEstudio)
	{
		Integer cantidadPoscorrelativas = 0;
		for (Curso c : recomendacion.getRecomendacion()) // Por cada curso en la recomendacion
		{
			cantidadPoscorrelativas+=planEstudio.getCantidadPoscorrelativas(c.getMateria());
		}
		return cantidadPoscorrelativas;
	}
	
	public void setPlanEstudio(PlanEstudio planEstudio)
	{
		this.planEstudio = planEstudio;
	}
	
	/*public ArrayList<Integer> contarCantPosCorrelativas(List<Recomendacion> recomendaciones, PlanEstudio pe) 
	{
		HashMap<Materia, Set<Materia>> correlativas = pe.getCorrelativas();
		ArrayList<Integer> cantidadPoscorrelativas = new ArrayList<Integer>();
		Materia materia;
		for (Recomendacion r : recomendaciones) // Por cada recomendacion
		{
			Integer cantPosCorrelativas = 0;
			for (Curso c : r.getRecomendacion()) // Por cada curso
			{
				materia = c.getMateria();
				Collection<Set<Materia>> correl = correlativas.values();
				for (Set<Materia> mats : correl) //Contamos la cantidad de veces que 
				{
					for (Materia m : mats) //aparece una materia como correlativa de otras
					{
						if (m.getNombre().equalsIgnoreCase(materia.getNombre()))
						{
							cantPosCorrelativas++;
						}
					}
				}
			}
			cantidadPoscorrelativas.add(cantPosCorrelativas); // termino de contar y las
													// guardo en una lista
		}
		return cantidadPoscorrelativas;
	}*/
	
	
}
