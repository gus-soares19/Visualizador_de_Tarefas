package models;

import java.util.ArrayList;
import java.util.List;

public class Task {

	private String name;
	private long id;
	private List<List<Information>> history;

	public Task() {
		this.setHistory(new ArrayList<List<Information>>());
	}

	public Task(String nome, long id) {
		this.setName(nome);
		this.setId(id);
		this.setHistory(new ArrayList<List<Information>>());
	}

	public void addHistory(List<Information> informations) {
		this.getHistory().add(informations);
	}
	
	public long getLatestInformation(String info) {
		List<Information> informations;
		informations = this.getHistory().get(this.getHistory().size() - 1);

		for (Information informaton : informations) {
			if (informaton.getName().equals(info)) {
				return informaton.getValue();
			}
		}

		return -1;
	}

	@Override
	public String toString() {
		return "Nome: " + this.getName() + " -- ID: " + this.getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<List<Information>> getHistory() {
		return history;
	}

	public void setHistory(List<List<Information>> history) {
		this.history = history;
	}

}
