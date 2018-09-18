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
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JRadioButton rdbtnStemming = new JRadioButton("Stemming");
		getContentPane().add(rdbtnStemming, BorderLayout.CENTER);
		
		JRadioButton rdbtnStopwords = new JRadioButton("Stopwords");
		getContentPane().add(rdbtnStopwords, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		txtPesquisa = new JTextField();
		txtPesquisa.setToolTipText("Pesquise aqui");
		panel.add(txtPesquisa);
		txtPesquisa.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		getContentPane().add(btnPesquisar, BorderLayout.SOUTH);
		btnPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//doStem, doStopwords, search string
				new Main().run(rdbtnStemming.isSelected(), rdbtnStopwords.isSelected(), txtPesquisa.getText());
			}
		});

	}

}
