package hu.unideb.inf.estran.ep.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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
							raw.getJSONObject(i).getString("projectName"),
							raw.getJSONObject(i).getBoolean("alphabet"),
							raw.getJSONObject(i).getBoolean("ALPHABET"),
							raw.getJSONObject(i).getBoolean("numbers"),
							raw.getJSONObject(i).getBoolean("symbols"),
							raw.getJSONObject(i).getString("alpha"),
							raw.getJSONObject(i).getString("omega"),
							raw.getJSONObject(i).getInt("method"),
							raw.getJSONObject(i).getInt("weight"),
							raw.getJSONObject(i).getInt("mutationRate"),
							raw.getJSONObject(i).getBoolean("differentParents"),
							raw.getJSONObject(i).getInt("populationSize"),
							raw.getJSONObject(i).getInt("maxCycle")
							)
					);

		in.close();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	@Override
	public Vector<Project> loadProjects() {
		return projects;
	}


	@Override
	public void saveProjects(Vector<Project> projects) {


		JSONArray jsonArray = new JSONArray();

		try {

		for (Project p: projects) {

		    JSONObject jsonObj= new JSONObject();


		    jsonObj.put("projectName", p.getProjectName());
		    jsonObj.put("alphabet", p.isAlphabet());
		    jsonObj.put("ALPHABET", p.isALPHABET());
			jsonObj.put("numbers", p.isNumbers());
			jsonObj.put("symbols", p.isSymbols());
			jsonObj.put("alpha", p.getAlpha());
			jsonObj.put("omega", p.getOmega());
			jsonObj.put("method", p.getMethod());
			jsonObj.put("weight", p.getWeight());
			jsonObj.put("mutationRate", p.getMutationRate());
			jsonObj.put("differentParents", p.isDifferentParents());
			jsonObj.put("populationSize", p.getPopulationSize());
			jsonObj.put("maxCycle", p.getMaxCycle());

		    jsonArray.put(jsonObj.toString());
		}


		FileWriter file = new FileWriter("./resources/projects.json");

		file.write(jsonArray.toString().replace("\\", "").replace("[\"", "[").replace("\"]", "]").replace("\"{", "{").replace("}\"", "}"));
		file.flush();
		file.close();

		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
