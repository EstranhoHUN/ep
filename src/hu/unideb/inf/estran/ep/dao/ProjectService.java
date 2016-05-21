package hu.unideb.inf.estran.ep.dao;

import hu.unideb.inf.estran.ep.view.Project;
import java.util.Vector;

import org.json.JSONException;

public class ProjectService {

	private ProjectDao projectDao = new ProjectDaoJsonImpl () ;

	public ProjectService() {
	}

	public Vector<Project> loadProjects () {
		return projectDao.loadProjects();
	};

	public void saveProjects (Vector<Project> p) {
		projectDao.saveProjects(p);
	};
}
