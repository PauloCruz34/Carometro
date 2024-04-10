package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;

import java.awt.Toolkit;
import java.sql.Connection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Carometro extends JFrame {
	
	// istanciando objetos
	DAO dao = new DAO();
	private Connection con;
	
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					Carometro frame = new Carometro();
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
	public Carometro() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("Carometro");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\paulo\\Documents\\Carometro\\carometro\\src\\img\\317712_code repository_github_repository_resource_icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	} // fim do construtor
	
	private void status() {
		try {
			con = dao.conection();
			if (con == null) {
				System.out.println("Erro de Conecxão");	
				
			} else {
				System.out.println("DataBase Connected");

			}
			con.close(); // fechando a conecção
			
		} catch (Exception e) {
			System.out.println(e);  //digito sysout depois ctrl+space ele se auto completa
			
			
		}
		
	}
	
}
