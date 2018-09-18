package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import lucene.Main;
import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Gui extends JFrame {
	private JTextField txtPesquisa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setBounds(100, 100, 622, 362);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 604, 32);
		getContentPane().add(panel);
		
		txtPesquisa = new JTextField();
		txtPesquisa.setToolTipText("Pesquise aqui");
		panel.add(txtPesquisa);
		txtPesquisa.setColumns(30);
		
		JRadioButton rdbtnStemming = new JRadioButton("Stemming");
		rdbtnStemming.setBounds(125, 41, 97, 53);
		getContentPane().add(rdbtnStemming);
		
		JRadioButton rdbtnStopwords = new JRadioButton("Stopwords");
		rdbtnStopwords.setBounds(10, 41, 89, 53);
		getContentPane().add(rdbtnStopwords);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(442, 47, 131, 41);
		getContentPane().add(btnPesquisar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 99, 580, 203);
		getContentPane().add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setToolTipText("Resultados");
		
		btnPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//doStem, doStopwords, search string
				new Main().run(rdbtnStemming.isSelected(), rdbtnStopwords.isSelected(), txtPesquisa.getText(), textPane);
			}
		});

	}
}
