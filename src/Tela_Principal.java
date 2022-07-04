/*
 * Autores: Gustavo Soares e Vinicius Forte 
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import models.Cluster;
import models.Task;

public class Tela_Principal extends JFrame {

	private static final long serialVersionUID = 4955616339499403673L;
	private JPanel contentPane;
	private JTable ClustersTable;
	private JTable GeneralClusterInfoTable;
	private JTable TasksTable;
	private Cluster selectedCluster;
	private UpdateInfos update = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela_Principal frame = new Tela_Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Tela_Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 481);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Visualizador de Tarefas");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 661, 33);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane_ClustersTable = new JScrollPane();
		scrollPane_ClustersTable.setBounds(10, 56, 204, 378);
		contentPane.add(scrollPane_ClustersTable);

		ClustersTable = new JTable();
		ClustersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int line = ClustersTable.getSelectedRow();
				if (line == -1) {
					DefaultTableModel model = (DefaultTableModel) TasksTable.getModel();
					model.setNumRows(0);

					model = (DefaultTableModel) GeneralClusterInfoTable.getModel();
					model.setNumRows(0);
				} else {
					selectedCluster = update.getClusters().get(line);
					listGeneralTaskInfos(selectedCluster);
					listAllTasks(selectedCluster);
				}

				
			}
		});
		ClustersTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Tasks" }));
		ClustersTable.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
		scrollPane_ClustersTable.setViewportView(ClustersTable);

		JScrollPane scrollPane_GeneralClusterInfoTable = new JScrollPane();
		scrollPane_GeneralClusterInfoTable.setBounds(236, 56, 435, 39);
		contentPane.add(scrollPane_GeneralClusterInfoTable);

		GeneralClusterInfoTable = new JTable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) GeneralClusterInfoTable
				.getDefaultRenderer(JLabel.class);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);// aqui
		scrollPane_GeneralClusterInfoTable.setViewportView(GeneralClusterInfoTable);
		GeneralClusterInfoTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Cluster Name", "PageFault Count", "WorkingSet Size", "PageFile Usage" }));

		JScrollPane scrollPane_TasksTable = new JScrollPane();
		scrollPane_TasksTable.setBounds(236, 128, 435, 306);
		contentPane.add(scrollPane_TasksTable);

		TasksTable = new JTable();
		TasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int line = TasksTable.getSelectedRow();
				long id = Long.valueOf(TasksTable.getValueAt(line, 0).toString());

				for (Task task : selectedCluster.getTasks()) {
					if (task.getId() == id) {
						TaskInfosScreen tis = new TaskInfosScreen();
						tis.setVisible(true);
						tis.setLocationRelativeTo(contentPane);
						tis.setTask(task);
					}
				}
			}
		});
		TasksTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "PID", "PageFault Count", "WorkingSet Size", "PageFile Usage" }));
		scrollPane_TasksTable.setViewportView(TasksTable);

		update = new UpdateInfos();
		update.setDelay(1000);
		update.setContentPane(contentPane);
		update.setTable(ClustersTable);
		update.init();
	}

	private void listGeneralTaskInfos(Cluster cluster) {
		DefaultTableModel model = (DefaultTableModel) GeneralClusterInfoTable.getModel();
		model.setNumRows(0);
		model.addRow(new Object[] { cluster.getName(), cluster.getTotalFrom("PageFaultCount"),
				cluster.getTotalFrom("WorkingSetSize") + "MB", cluster.getTotalFrom("PagefileUsage") + "MB" });

	}

	private void listAllTasks(Cluster cluster) {
		DefaultTableModel model = (DefaultTableModel) TasksTable.getModel();
		model.setNumRows(0);

		for (Task task : cluster.getTasks()) {
			model.addRow(new Object[] { task.getId(), task.getLatestInformation("PageFaultCount"),
					task.getLatestInformation("WorkingSetSize") + "MB",
					task.getLatestInformation("PagefileUsage") + "MB" });
		}

	}
}
