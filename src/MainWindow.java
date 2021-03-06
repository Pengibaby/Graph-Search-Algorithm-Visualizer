import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class MainWindow {

	private JFrame frame;
	private int size;
	private int algoIndex;
	private int speed = 4;
	private String[] gridSizeList = new String[9];
	private String[] searchAlgoList = new String[4];
	private int startX = -1;
	private int startY = -1;
	private int endX = -1;
	private int endY = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Graph Search Visualization With Grid");
		frame.setBounds(50, 50, 923, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		this.size = 5;
		this.algoIndex = 0;
		CellMaze newMaze = new CellMaze(size);
		newMaze.generateGrid(size);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 80, 886, 831);
		frame.getContentPane().add(tabbedPane);
		tabbedPane.add(newMaze);

		gridSizeList[0] = "2";
		gridSizeList[1] = "4";
		gridSizeList[2] = "5";
		gridSizeList[3] = "8";
		gridSizeList[4] = "10";
		gridSizeList[5] = "16";
		gridSizeList[6] = "20";
		gridSizeList[7] = "40";
		gridSizeList[8] = "80";
		
		searchAlgoList[0] = "A*";
		searchAlgoList[1] = "BFS";
		searchAlgoList[2] = "DFS";
		searchAlgoList[3] = "Dijkstra";
		
		JSlider gridSpeedSlider = new JSlider(1, 16, 4);
		gridSpeedSlider.setBounds(200, 38, 200, 50);
		gridSpeedSlider.setMajorTickSpacing(3);
		gridSpeedSlider.setMinorTickSpacing(1);
		gridSpeedSlider.setPaintTicks(true);
		gridSpeedSlider.setPaintLabels(true);
		gridSpeedSlider.setSnapToTicks(true);
		frame.getContentPane().add(gridSpeedSlider);
		
		JLabel lblGridSize = new JLabel("Grid Size");
		lblGridSize.setBounds(10, 13, 54, 14);
		frame.getContentPane().add(lblGridSize);
		
		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(280, 13, 54, 14);
		frame.getContentPane().add(lblSpeed);
		
		JComboBox gridSizeComboBox = new JComboBox();
		gridSizeComboBox.setBounds(10, 38, 54, 37);
		gridSizeComboBox.setModel(new DefaultComboBoxModel(gridSizeList));
		frame.getContentPane().add(gridSizeComboBox);
		gridSizeComboBox.setSelectedIndex(2);
		
		JComboBox searchAlgorithmComboBox = new JComboBox();
		searchAlgorithmComboBox.setBounds(74, 38, 116, 37);
		searchAlgorithmComboBox.setModel(new DefaultComboBoxModel(searchAlgoList));
		frame.getContentPane().add(searchAlgorithmComboBox);
		searchAlgorithmComboBox.setSelectedIndex(0);
		
		JLabel lblSearchAlgorithm = new JLabel("Search Algorithm");
		lblSearchAlgorithm.setBounds(74, 13, 116, 14);
		frame.getContentPane().add(lblSearchAlgorithm);
		
		JRadioButton setBlocksRadio = new JRadioButton("Set Blocks");
		setBlocksRadio.setBounds(406, 38, 89, 23);
		frame.getContentPane().add(setBlocksRadio);
		
		JRadioButton setStartRadio = new JRadioButton("Set Start");
		setStartRadio.setBounds(497, 38, 89, 23);
		frame.getContentPane().add(setStartRadio);
		
		JRadioButton setEndRadio = new JRadioButton("Set End");
		setEndRadio.setBounds(588, 38, 89, 23);
		frame.getContentPane().add(setEndRadio);
		
		JButton startButton = new JButton("Start");
		startButton.setBounds(683, 28, 102, 50);
		frame.getContentPane().add(startButton);
		startButton.setEnabled(false);
		
		JButton resetButton = new JButton("Reset");
		resetButton.setBounds(795, 28, 102, 50);
		frame.getContentPane().add(resetButton);
		resetButton.setEnabled(false);
		
		gridSizeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selection = (String) gridSizeComboBox.getSelectedItem();
				int selectionInt = Integer.parseInt(selection);
				size = selectionInt;
				newMaze.generateGrid(size);
			}
		});
		
		searchAlgorithmComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				algoIndex = searchAlgorithmComboBox.getSelectedIndex();
			}
		});
		
		setBlocksRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(setBlocksRadio.isSelected()) {
					setStartRadio.setSelected(false);
					setEndRadio.setSelected(false);
				}
			}
		});
		
		setStartRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(setStartRadio.isSelected()) {
					setBlocksRadio.setSelected(false);
					setEndRadio.setSelected(false);
				}
			}
		});
		
		setEndRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(setEndRadio.isSelected()) {
					setBlocksRadio.setSelected(false);
					setStartRadio.setSelected(false);
				}
			}
		});
		
		gridSpeedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				speed = ((JSlider) arg0.getSource()).getValue();
				newMaze.setSpeed(speed);
			}
		});
		
		tabbedPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!setBlocksRadio.isSelected() && !setStartRadio.isSelected() && !setEndRadio.isSelected()) {
					return;
				} else if(setBlocksRadio.isSelected() && !setStartRadio.isSelected() && !setEndRadio.isSelected()) {
					int mouseClickX = e.getX();
					int mouseClickY = e.getY();
					System.out.println((mouseClickX - 3) + ", " + (mouseClickY - 28));
					newMaze.setBlocks((mouseClickY - 28) / (800 / size), (mouseClickX - 3) / (880 / size));
				} else if(setStartRadio.isSelected() && !setEndRadio.isSelected() && !setBlocksRadio.isSelected()) {
					int mouseClickX = e.getX();
					int mouseClickY = e.getY();
					System.out.println((mouseClickX - 3) + ", " + (mouseClickY - 28));
					Cell start = newMaze.setStart((mouseClickY - 28) / (800 / size), (mouseClickX - 3) / (880 / size));
					if(start != null) {
						startX = (int) (mouseClickY - 28) / (800 / size);
						startY = (int) (mouseClickX - 3) / (880 / size);
					}
					System.out.println(startX + ", " + startY);
					
					if(startX != -1 && startY != -1 && endX != -1 && endY != -1 && (algoIndex == 0 || algoIndex == 1 || algoIndex == 2 || algoIndex == 3)) {
						startButton.setEnabled(true);
					}
				} else if(setEndRadio.isSelected() && !setStartRadio.isSelected() && !setBlocksRadio.isSelected()) {
					int mouseClickX = e.getX();
					int mouseClickY = e.getY();
					System.out.println((mouseClickX - 3) + ", " + (mouseClickY - 28));
					Cell end = newMaze.setEnd((mouseClickY - 28) / (800 / size), (mouseClickX - 3) / (880 / size));
					if(end != null) {
						endX = (int) (mouseClickY - 28) / (800 / size);
						endY = (int) (mouseClickX - 3) / (880 / size);
					}
					System.out.println(endX + ", " + endY);
					
					if(startX != -1 && startY != -1 && endX != -1 && endY != -1 && (algoIndex == 0 || algoIndex == 1 || algoIndex == 2 || algoIndex == 3)) {
						startButton.setEnabled(true);
					}
				}
		    }
		});
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startButton.setEnabled(false);
				resetButton.setEnabled(true);
				setBlocksRadio.setSelected(false);
				setBlocksRadio.setEnabled(false);
				setStartRadio.setSelected(false);
				setStartRadio.setEnabled(false);
				setEndRadio.setSelected(false);
				setEndRadio.setEnabled(false);
				gridSizeComboBox.setEnabled(false);
				searchAlgorithmComboBox.setEnabled(false);
				newMaze.setNeighbours();
				newMaze.printGrid();
				if(algoIndex == 0) {
					newMaze.solveAStar(startX, startY, endX, endY);
					System.out.println(startX + ", " + startY + ", " + endX + ", " + endY);
				} else if(algoIndex == 1) {
					newMaze.solveBFS(startX, startY, endX, endY);
					System.out.println(startX + ", " + startY + ", " + endX + ", " + endY);
				} else if(algoIndex == 2) {
					newMaze.solveDFS(startX, startY, endX, endY);
					System.out.println(startX + ", " + startY + ", " + endX + ", " + endY);
				} else if(algoIndex == 3) {
					newMaze.solveDijkstra(startX, startY, endX, endY);
					System.out.println(startX + ", " + startY + ", " + endX + ", " + endY);
				}
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startButton.setEnabled(false);
				resetButton.setEnabled(false);
				setBlocksRadio.setSelected(false);
				setBlocksRadio.setEnabled(true);
				setStartRadio.setSelected(false);
				setStartRadio.setEnabled(true);
				setEndRadio.setSelected(false);
				setEndRadio.setEnabled(true);
				gridSizeComboBox.setEnabled(true);
				searchAlgorithmComboBox.setEnabled(true);
				gridSizeComboBox.setSelectedIndex(2);
				searchAlgorithmComboBox.setSelectedIndex(0);
				size = 5;
				algoIndex = 0;
				speed = 4;
				startX = -1;
				startY = -1;
				endX = -1;
				endY = -1;
				newMaze.generateGrid(5);
			}
		});
	}
}
