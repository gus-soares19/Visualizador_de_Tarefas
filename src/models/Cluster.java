package models;

import java.util.ArrayList;
import java.util.List;

public class Cluster{

	private List<Task> tasks = new ArrayList<>();
	private String name;

	public Cluster() {
	}

	public Cluster(List<Task> tasks, String name) {
		this.setTasks(tasks);
		this.setName(name);
	}

	public long getTotalFrom(String info) {
		long total = 0;

		for (Task task : this.getTasks()) {
			total += task.getLatestInformation(info);
		}

		return total;
	}

	@Override
	public String toString() {
		String retorno = "Cluster: " + this.getName() + "\nTasks:\n";

		for (Task processo : this.getTasks()) {
			retorno += "[" + processo.toString() + "]\n";
		}

		return retorno;
	}

	public void addTask(Task task) {

		if (task == null) {
			throw new NullPointerException();
		}

		if (tasks.contains(task)) {
			throw new IllegalArgumentException();
		}

		this.getTasks().add(task);
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
