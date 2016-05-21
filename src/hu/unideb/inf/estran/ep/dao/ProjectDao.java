package hu.unideb.inf.estran.ep.dao;

import hu.unideb.inf.estran.ep.view.Project;
import java.util.Vector;

public interface ProjectDao  {

	public Vector<Project> loadProjects ();

	public void saveProjects (Vector<Project> p);
}
