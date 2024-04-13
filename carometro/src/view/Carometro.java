package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.cj.jdbc.Blob;

import model.DAO;
import utils.Validador;

public class Carometro extends JFrame {

	// istanciando objetos
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// instanciar objetos para o fluxo de Byts

	private FileInputStream fis;

	// variavel global para armazenar imagem em bytes

	private int tamanho;

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblDate;
	private JLabel lblNewLabel;
	private JTextField txtRe;
	private JLabel lblFoto;
	private JLabel lblNewLabel_2;
	private JTextField txtNome;
	private JButton btnBuscar;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\paulo\\Documents\\Carometro\\carometro\\src\\img\\317712_code repository_github_repository_resource_icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 380);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBounds(new Rectangle(0, 0, 256, 256));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 292, 685, 49);
		contentPane.add(panel);
		panel.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(643, 11, 32, 32);
		panel.add(lblStatus);

		lblDate = new JLabel("");
		lblDate.setForeground(SystemColor.text);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDate.setBounds(10, 11, 271, 27);
		panel.add(lblDate);

		lblNewLabel = new JLabel("RE:");
		lblNewLabel.setBounds(23, 21, 21, 14);
		contentPane.add(lblNewLabel);

		txtRe = new JTextField();
		txtRe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtRe.setBounds(76, 15, 86, 26);
		contentPane.add(txtRe);
		txtRe.setColumns(10);
		// Uso do plainDocument para limitação de caracteres.
		txtRe.setDocument(new Validador(6));

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(23, 71, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(75, 64, 272, 26);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		// Uso do plainDocument para limitação de caracteres.
		txtNome.setDocument(new Validador(30));

		lblFoto = new JLabel("New label");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Carometro.class.getResource("/img/camera.png")));
		lblFoto.setBounds(419, 11, 256, 256);
		contentPane.add(lblFoto);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(0, 0, 46, 14);
		contentPane.add(lblNewLabel_2);

		JButton btnDownload = new JButton("Carregar Foto");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnDownload.setForeground(SystemColor.textHighlight);
		btnDownload.setBounds(214, 106, 133, 26);
		contentPane.add(btnDownload);

		JButton btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Carometro.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(23, 217, 64, 64);
		contentPane.add(btnAdicionar);

		JButton btnReset = new JButton("");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setIcon(new ImageIcon(Carometro.class.getResource("/img/eraser.png")));
		btnReset.setToolTipText("Limpar Campos");
		btnReset.setBounds(308, 217, 64, 64);
		contentPane.add(btnReset);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarRE();
			}
		});
		btnBuscar.setForeground(SystemColor.textHighlight);
		btnBuscar.setBounds(214, 15, 133, 26);
		contentPane.add(btnBuscar);

	} // fim do construtor

	private void status() {
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

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblDate.setText(formatador.format(data));
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de Imagem (*.PNG,*.JPG,*.JPEG", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void adicionar() {
		if (txtNome.getText().isEmpty()) { // obriga o preenchimento do campo
			JOptionPane.showMessageDialog(null, "Preencha o campo nome para continuar!");
			txtNome.requestFocus();
		} else {
			String insert = "insert into funcionários(nome,foto) values(? , ?)";
			try {
				con = dao.conection();
				pst = con.prepareStatement(insert);
				pst.setString(1, txtNome.getText());
				pst.setBlob(2, fis, tamanho);
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showInternalMessageDialog(null, "Funcionário cadastrado!");
					reset();
				} else {
					JOptionPane.showInternalMessageDialog(null, "Funcioário não cadastrado!");
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void buscarRE() {
		if (txtRe.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o RE");
			txtRe.requestFocus();
		} else {
			String readRe = "select * from funcionários where re = ?";
			try {
				con = dao.conection();
				pst = con.prepareStatement(readRe);
				pst.setString(1, txtRe.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtNome.setText(rs.getString(2));
					Blob blob = (Blob) rs.getBlob(3);
					byte[] img = blob.getBytes(1, (int) blob.length()); // converte dados binarios para arquivo de
																		// imagem
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);

				} else {
					JOptionPane.showMessageDialog(null, "Funcionário não cadastrado");
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void reset() { // apaga os campos quando o funcioário ja esta cadastrado e posiciona o cursor
							// no campo nome
		txtRe.setText(null);
		txtNome.setText(null);
		lblFoto.setIcon(new ImageIcon(Carometro.class.getResource("/img/camera.png")));
		txtNome.requestFocus();
	}

};
