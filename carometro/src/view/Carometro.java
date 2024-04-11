package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	private JLabel lblStatus;
	private JLabel lblDate;
	private JLabel lblNewLabel;
	private JTextField txtRa;
	

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
				setarData();
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
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 248, 624, 49);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(582, 11, 32, 32);
		panel.add(lblStatus);
		
		lblDate = new JLabel("");
		lblDate.setForeground(SystemColor.text);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDate.setBounds(10, 11, 271, 27);
		panel.add(lblDate);
		
		lblNewLabel = new JLabel("RE:");
		lblNewLabel.setBounds(23, 21, 21, 14);
		contentPane.add(lblNewLabel);
		
		txtRa = new JTextField();
		txtRa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if(!caracteres.contains(e.getKeyChar()+"")) {
					e.consume();
				}
			}
		});
		txtRa.setBounds(45, 20, 86, 16);
		contentPane.add(txtRa);
		txtRa.setColumns(10);
	} // fim do construtor
	
	
	

//	private void status() {
//		try {
//			con = dao.conection();
//				if (con == null) {
//				//System.out.println("Erro de Conecxão");	
//					lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/dboff.png")));
//				} else {
//					lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/dbon.png")));
//
//		}
//			con.close(); // fechando a conexão
//			
//		} catch (Exception e) {
//		System.out.println(e);  //digito sysout depois ctrl+space ele se auto completa
//			
//			
//		}
//		
//	}
	
	private void status(){
		try {
			con = dao.conection();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/dboff.png")));
				
			} else {
				lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/dbon.png")));
					
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void setarData(){
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblDate.setText(formatador.format(data));
	}
}
