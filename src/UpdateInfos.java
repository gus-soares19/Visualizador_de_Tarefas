import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import models.Cluster;
import models.Information;
import models.Task;

public class UpdateInfos implements ActionListener {

	private Timer timer;
	private int delay;
	private JPanel contentPane;
	private JTable table;
	private TaskList taskList = new TaskList();
	private List<Cluster> clusters = new ArrayList<Cluster>();

	public UpdateInfos() {
		this.delay = 0;
		this.contentPane = new JPanel();

	}

	public UpdateInfos(int delay, JPanel contentPane, JTable table) {
		this.delay = delay;
		this.contentPane = contentPane;
		this.table = table;
	}

	public void init() {
		this.setTimer(new Timer(this.getDelay(), this));
		this.getTimer().start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateClusters();
		updateAllTasksInfos();
		listClusters();
		this.getContentPane().updateUI();
	}

	private void updateAllTasksInfos() {
		List<Information> infos = null;
		for (Cluster cluster : this.getClusters()) {
			for (Task task : cluster.getTasks()) {
				taskList.setIdProcesso(task.getId());
				infos = taskList.getTaskInfos();

				if (!infos.isEmpty()) {
					task.addHistory(infos);
				}
			}
		}
	}

	private List<Cluster> updateClusters() {
		this.getClusters().clear();
		List<Task> tasks = taskList.getTasks();
		Cluster cluster = null;

		if (tasks.isEmpty()) {
			return this.getClusters();
		}

		for (Task task : tasks) {
			cluster = getTaskCluster(task);

			if (cluster != null) {
				cluster.addTask(task);
			} else {
				cluster = new Cluster();
				cluster.setName(task.getName().split("\\.")[0]);
				cluster.addTask(task);
				this.getClusters().add(cluster);
			}
		}

		return this.getClusters();
	}

	private Cluster getTaskCluster(Task task) {
		if (this.getClusters().isEmpty()) {
			return null;
		}

		for (Cluster cluster : this.getClusters()) {
			if (cluster.getName().equals(task.getName().split("\\.")[0])) {
				return cluster;
			}
		}

		return null;
	}

	private void listClusters() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setNumRows(0);

		for (Cluster cluster : this.getClusters()) {
			model.addRow(new Object[] { cluster.getName() });
		}

	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

}
