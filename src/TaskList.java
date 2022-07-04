/*
 * Autores: Gustavo Soares e Vinicius Forte 
 */

import java.util.ArrayList;
import java.util.List;

import models.Information;
import models.Task;

public class TaskList {

	private long idProcesso;

	static {
		System.load("D:\\Programação\\Eclipse\\Workspaces\\FURB\\Gerenciador_de_Tarefas\\src\\getOSTaskList.dll");
	}

	public List<Task> getTasks() {
		String tasksStr = this.nativeGetTasks();
		List<Task> tasks = new ArrayList<Task>();

		if (tasksStr.equals("null")) {
			return tasks;
		}

		String[] tasksArray = tasksStr.split("\n");
		Task task = null;

		for (String taskStr : tasksArray) {
			String[] taskArray = taskStr.split(" - ");

			if (!taskArray[0].equals("<unknown>")) {
				task = new Task(taskArray[0], Long.valueOf(taskArray[1]));
				tasks.add(task);
			}
		}

		return tasks;
	}

	public List<Information> getTaskInfos() {
		String taskInfosStr = this.nativeGetTaskInfos();
		List<Information> informations = new ArrayList<Information>();

		if (taskInfosStr.equals("null")) {
			return informations;
		}

		String[] taskInfosArray = taskInfosStr.replace("0x", "").split("\n");
		Information info = null;

		for (String infos : taskInfosArray) {
			String[] infosArray = infos.split(": ");
			info = new Information(infosArray[0], Long.valueOf(infosArray[1]));
			informations.add(info);
		}

		return informations;
	}

	private native String nativeGetTasks();

	private native String nativeGetTaskInfos();

	public long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(long idProcesso) {
		this.idProcesso = idProcesso;
	}

}
