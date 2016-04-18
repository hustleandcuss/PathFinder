import graph.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PathFinder extends JFrame {
	public static ListGraph<Place> listGraph = new ListGraph<>();

	MouseAdd ma = new MouseAdd();
	MouseGet mg = new MouseGet();

	JMenuItem findWayOpt;
	JMenuItem showConOpt;
	JMenuItem newPlaceOpt;
	JMenuItem newConOpt;
	JMenuItem changeConOpt;
	JButton changeCon;
	JButton newCon;
	JButton newPlace;
	JButton showCon;
	JButton findWay;

	Place p1, p2;
	JLabel map;
	String fileName;
	int x = -1;
	int y = -1;

	public PathFinder() {
		super("Path Finder");

		//Meny
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		JMenu aMen = new JMenu("File");
		mb.add(aMen);
		JMenu oMen = new JMenu("Operations");
		mb.add(oMen);

		JMenuItem newOpt = new JMenuItem("New");
		aMen.add(newOpt);
		newOpt.addActionListener(new NewOpt());

		JMenuItem exitOpt = new JMenuItem("Exit");
		aMen.add(exitOpt);
		exitOpt.addActionListener(new ExitOpt());

		findWayOpt = new JMenuItem("Find way");
		oMen.add(findWayOpt);
		findWayOpt.setEnabled(false);
		findWayOpt.addActionListener(new FindWay());

		showConOpt = new JMenuItem("Show conection");
		oMen.add(showConOpt);
		showConOpt.setEnabled(false);
		showConOpt.addActionListener(new ShowCon());

		newPlaceOpt = new JMenuItem("New place");
		oMen.add(newPlaceOpt);
		newPlaceOpt.setEnabled(false);
		newPlaceOpt.addActionListener(new NewPlace());

		newConOpt = new JMenuItem("New conection");
		oMen.add(newConOpt);
		newConOpt.setEnabled(false);
		newConOpt.addActionListener(new NewCon());

		changeConOpt = new JMenuItem("Change conection");
		oMen.add(changeConOpt);
		changeConOpt.setEnabled(false);
		changeConOpt.addActionListener(new ChangeCon());
		//Meny slut.

		setLayout(new BorderLayout());

		JPanel north = new JPanel();
		add(north, BorderLayout.NORTH);

		//Knappar
		findWay = new JButton("Find way");
		north.add(findWay);
		findWay.setEnabled(false);
		findWay.addActionListener(new FindWay());

		showCon = new JButton("Show conection");
		north.add(showCon);
		showCon.setEnabled(false);
		showCon.addActionListener(new ShowCon());

		newPlace = new JButton("New place");
		newPlace.setMultiClickThreshhold(3000);
		north.add(newPlace);
		newPlace.setEnabled(false);
		newPlace.addActionListener(new NewPlace());

		newCon = new JButton("New conection");
		north.add(newCon);
		newCon.setEnabled(false);
		newCon.addActionListener(new NewCon());

		changeCon = new JButton("Change conection");
		north.add(changeCon);
		changeCon.setEnabled(false);
		changeCon.addActionListener(new ChangeCon());
		//Knappar slut

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	} //PathFinder

	class NewOpt implements ActionListener {
		public void actionPerformed (ActionEvent ave) {

			if(map != null) {
			remove(map);

			}

			p1 = null;
			p2= null;

			JFileChooser jfc = new JFileChooser("./images");

			int result = jfc.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) {
				fileName = jfc.getSelectedFile().getPath();

				map = new JLabel(new ImageIcon(fileName));
				add(map, BorderLayout.CENTER);

				newPlaceOpt.setEnabled(true);
				newPlace.setEnabled(true);

				pack();
				validate();
				repaint();

			}
		}		
	} //NewOpt

	class ExitOpt implements ActionListener {
		public void actionPerformed (ActionEvent ave) {
			System.exit(0);

		}
	} //ExitOpt

	class NewPlace implements ActionListener {
		public void actionPerformed (ActionEvent ave) {
			map.addMouseListener(ma);
			map.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				
		}
	} //NewPlace

	class MouseAdd extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			x = mev.getX();
			y = mev.getY();

			map.removeMouseListener(ma);
			map.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			
			JPanel newPlace = new JPanel();
			JTextField placeName = new JTextField(8);

			newPlace.add(new JLabel("Name of place: "));
			newPlace.add(placeName);
			
			Place p;
			String name;

			for(;;) {
				int answer = JOptionPane.showConfirmDialog(null, newPlace, "New Place", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if(answer != JOptionPane.OK_OPTION) {
					return;

				}

				name = placeName.getText();

				if(name.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Name the place!", "Fail!", JOptionPane.ERROR_MESSAGE);

				} else {
					p = new Place(x, y, name);
					p.addMouseListener(mg);
					listGraph.add(p);
					break;

				} 
			}

			newConOpt.setEnabled(true);
			newCon.setEnabled(true);

			map.add(p);
			validate();
			repaint();

		}
	} //MouseLis

	class NewCon implements ActionListener {
		public void actionPerformed (ActionEvent ave) {
			if(p1 == null || p2 == null) {
				JOptionPane.showMessageDialog(null, "Click on two places to use this function!", "Fail!", JOptionPane.ERROR_MESSAGE);

			} else {
				Edge<Place> edge = listGraph.getEdgeBetween(p1, p2);

				if(edge != null) {
					JOptionPane.showMessageDialog(null, "There already is such a connection!", "Fail!", JOptionPane.ERROR_MESSAGE);
				
				} else {
					String from = p1.getName();
					String to = p2.getName();
					JPanel newCon = new JPanel();
					newCon.setLayout(new BoxLayout(newCon, BoxLayout.Y_AXIS));
					JTextField eName = new JTextField(8);
					JTextField eWeight = new JTextField(8);

					newCon.add(new JLabel("Add a connection between " + from + " and " + to + ":"));
				
					newCon.add(new JLabel("Connection type: "));
					newCon.add(eName);

					newCon.add(new JLabel("Time: "));
					newCon.add(eWeight);
	 
					String name;
					int weight;

					for(;;) {
						try {
							int answer = JOptionPane.showConfirmDialog(null, newCon, "New Connection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

							if(answer != JOptionPane.OK_OPTION) {
							return;

							}

							name = eName.getText();
							weight = Integer.parseInt(eWeight.getText());

							if(name.isEmpty() || weight < 0) {
								JOptionPane.showMessageDialog(null, "Plase enter the type of the connection and positve number as time!", "Fail!", JOptionPane.ERROR_MESSAGE);

							} else {
								listGraph.connect(p1, p2, name, weight);
								
								break;

							}

						} catch(NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Enter time as a numreric value!", "Fail!", JOptionPane.ERROR_MESSAGE);
					
						}
					}

					changeConOpt.setEnabled(true);
					changeCon.setEnabled(true);
					showConOpt.setEnabled(true);
					showCon.setEnabled(true);
					findWayOpt.setEnabled(true);
					findWay.setEnabled(true);

				}	
			}
		}
	} //NewCon

	class MouseGet extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			Place p = (Place)mev.getSource();

			if(p1 == null && p != p2) {
				p1 = p;
				p1.isClicked = true;
				repaint();

			} else if(p2 == null && p != p1) {
				p2 = p;
				p2.isClicked = true;
				repaint();

			} else if(p1 != null && p == p1) {
				p1.isClicked = false;
				p1 = null;
				repaint();

			} else if(p2 != null && p == p2) {
				p2.isClicked = false;
				p2 = null;
				repaint();

			}
		}
	} //MouseGet

	class FindWay implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if(p1 == null || p2 == null) {
				JOptionPane.showMessageDialog(null, "Click on two places to use this function!", "Fail!", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Edge<Place> edge;
			int totalWeigth = 0;
			String theWay = "";

			JPanel findWay = new JPanel();
			JTextArea text = new JTextArea();
			text.setEditable(false);
			findWay.setLayout(new BoxLayout(findWay, BoxLayout.Y_AXIS));
			text.setText("The way from " + p1.getName() + " to " + p2.getName() + ":\n");
			findWay.add(text);
			
			if(GraphMethods.pathExists(listGraph, p1, p2)) {
				List<Edge<Place>> foundWay = GraphMethods.getPath(listGraph, p1, p2);

				for(int i = 0; i < foundWay.size(); i++) {
					edge = foundWay.get(i);
					totalWeigth += edge.getWeight();
					theWay += "- " + edge;

				}

				text.append(theWay);
				text.append("The total time of traveling is " + totalWeigth + "h");

				for(;;) {
					int answer = JOptionPane.showConfirmDialog(null, findWay, "Finding all connections from " + p1.getName(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if(answer != JOptionPane.OK_OPTION || answer == JOptionPane.OK_OPTION) {
						return; 
					}
	
				}
			} else {
				JOptionPane.showMessageDialog(null, "You can't travel from " + p1 + " to " + p2, "Fail!", JOptionPane.ERROR_MESSAGE);
			}
		}
	} //FindWay

	class ShowCon implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if(p1 == null || p2 == null) {
				JOptionPane.showMessageDialog(null, "Click on two places to use this function!", "Fail!", JOptionPane.ERROR_MESSAGE);
			
			} else {
				Edge<Place> e;
				String name = "";
				String weight = "";

				JPanel showCon = new JPanel();
				showCon.setLayout(new BoxLayout(showCon, BoxLayout.Y_AXIS));
				JTextField eName = new JTextField(8);
				JTextField eWeight = new JTextField(8);
				
				showCon.add(new JLabel("Add a connection between " + p1.getName() + " and " + p2.getName() + ":"));
				
				showCon.add(new JLabel("Connection type: "));
				showCon.add(eName);
				eName.setEditable(false);
				
				showCon.add(new JLabel("Time: "));
				showCon.add(eWeight);
				eWeight.setEditable(false);
			
			
				for(;;) {
					e = listGraph.getEdgeBetween(p1, p2);

					if(e == null) {
						JOptionPane.showMessageDialog(null, "There is no such connection!", "Fail!", JOptionPane.ERROR_MESSAGE);
					} else {
						name += e.getName();
						eName.setText(name);
						weight += e.getWeight();
						eWeight.setText(weight);

						int answer = JOptionPane.showConfirmDialog(null, showCon, "The connection between " + p1.getName() + " and " + p2.getName(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(answer != JOptionPane.OK_OPTION) {
							return;

						}
					}
	
					break;

				}
			}
		} 
	} //ShowCon

	class ChangeCon implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if(p1 == null || p2 == null) {
				JOptionPane.showMessageDialog(null, "Click on two places to use this function!", "Fail!", JOptionPane.ERROR_MESSAGE);

			} else if(listGraph.getEdgeBetween(p1, p2) == null) {
				JOptionPane.showMessageDialog(null, "There is no connection between " + p1 + " and " + p2, "Fail!", JOptionPane.ERROR_MESSAGE);
			
			} else {
				JPanel changeCon = new JPanel();
				changeCon.setLayout(new BoxLayout(changeCon, BoxLayout.Y_AXIS));
				changeCon.add(new JLabel("Add new connection time between " + p1.getName() + " and " + p2.getName() + ": "));

				Edge<Place> p;

				JTextField eName = new JTextField(8);
				JTextField eWeight = new JTextField(8);
				
				p = listGraph.getEdgeBetween(p1, p2);
				String name = "" + p.getName();
				eName.setText(name);
				String weight = "" + p.getWeight();
				eWeight.setText(weight);

				changeCon.add(new JLabel("Connection type: "));
				changeCon.add(eName);
				eName.setEditable(false);
				
				changeCon.add(new JLabel("Time: "));
				changeCon.add(eWeight);
			
				for(;;) {
					try {
						int answer = JOptionPane.showConfirmDialog(null, changeCon, "New Connection Time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if(answer != JOptionPane.OK_OPTION) {
						return;

						}

						int nW = Integer.parseInt(eWeight.getText());

						if(nW < 0) {
							JOptionPane.showMessageDialog(null, "Plase enter positve numbereric value as time!", "Fail!", JOptionPane.ERROR_MESSAGE);
						
						} else {
							listGraph.setConnectionWeight(p1, p2, nW);
							break;
						
						}

					} catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Enter time as a numreric value!", "Fail!", JOptionPane.ERROR_MESSAGE);

					}
				}
			}
		}
	}

	public static void main(String []args) {
		new PathFinder();

	} //main


}