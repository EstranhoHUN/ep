package hu.unideb.inf.estran.ep.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.json.*;

import hu.unideb.inf.estran.ep.view.Project;


public class ProjectDaoJsonImpl implements ProjectDao {

	private Vector<Project> projects = new Vector<>();

	public ProjectDaoJsonImpl() {
		try {

			Reader in = new InputStreamReader(new FileInputStream("./resources/projects.json"), StandardCharsets.UTF_8);
			JSONArray raw = new JSONArray(new JSONTokener(in));


		for (int i = 0; i < raw.length(); i++)
			projects.add(
					new Project(
							raw.getJSONObject(i).getString("alpha"),
							raw.getJSONObject(i).getString("omega"),
							Integer.parseInt(raw.getJSONObject(i).getString("genomeSize"))
							)
					);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public Vector<Project> loadProjects() {
		return projects;
	}


	@Override
	public void saveProjects(Vector<Project> p) {
		//TODO
	}

}
