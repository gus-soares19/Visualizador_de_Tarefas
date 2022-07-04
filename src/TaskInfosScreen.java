/*
 * Autores: Gustavo Soares e Vinicius Forte 
 */

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import models.Information;
import models.Task;

public class TaskInfosScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable TaskInfosTable;
	private Task task;
	private JLabel lblNewLabel;
	String[] descriptions = { "PageFault Count", "WorkingSet Size", "Pagefile Usage", "PeakWorkingSet Size",
			"QuotaPeakPagedPool Usage", "QuotaPagedPool Usage", "QuotaPeakNonPagedPool Usage",
			"QuotaNonPagedPool Usage", "PeakPagefile Usage" };

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskInfosScreen frame = new TaskInfosScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TaskInfosScreen() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 266);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 366, 29);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane_TaskInfosTable = new JScrollPane();
		scrollPane_TaskInfosTable.setBounds(10, 49, 366, 167);
		contentPane.add(scrollPane_TaskInfosTable);

		TaskInfosTable = new JTable();
		TaskInfosTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Description", "Value" }));
		scrollPane_TaskInfosTable.setViewportView(TaskInfosTable);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
		lblNewLabel.setText(task.getName().split("\\.")[0] + " (PID: " + task.getId() + ")");

		DefaultTableModel model = (DefaultTableModel) TaskInfosTable.getModel();

		for (int i = 0; i < 9; i++) {
			if (Information.isInBytes(descriptions[i].replace(" ", ""))) {
				model.addRow(new Object[] { descriptions[i],
						task.getLatestInformation(descriptions[i].replace(" ", "")) + "MB" });

			} else {
				model.addRow(
						new Object[] { descriptions[i], task.getLatestInformation(descriptions[i].replace(" ", "")) });
			}
		}
	}
}
