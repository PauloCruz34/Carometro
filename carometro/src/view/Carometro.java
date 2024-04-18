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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultButtonModel;
import javax.swing.DefaultListModel;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.jdbc.Blob;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.Desktop;

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

	// correção de bug da foto

	private boolean fotoCarregada = false;

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
	private JList<String> listNomes;
	private JScrollPane scrollPaneLista;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnAdicionar;
	private JButton btnDownload;
	private JButton btnSobre;
	private JButton btnPdf;

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
		setBounds(100, 100, 812, 380);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBounds(new Rectangle(0, 0, 256, 256));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPaneLista = new JScrollPane();
		scrollPaneLista.setBorder(null);
		scrollPaneLista.setVisible(false);
		scrollPaneLista.setBounds(76, 88, 271, 118);
		contentPane.add(scrollPaneLista);

		listNomes = new JList();
		listNomes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarNome();
			}
		});
		listNomes.setBorder(null);
		scrollPaneLista.setViewportView(listNomes);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 292, 796, 49);
		contentPane.add(panel);
		panel.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Carometro.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(754, 11, 32, 32);
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
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarNomes();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					scrollPaneLista.setVisible(false);
					int confirma = JOptionPane.showConfirmDialog(null,
							"Funcionário não cadastrado! /nDeseja cadastrar o Funcionário?", "AVISO",
							JOptionPane.YES_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						txtRe.setEditable(false);
						txtRe.setText(null);
						btnBuscar.setEnabled(false);
						btnDownload.setEnabled(true);
						btnAdicionar.setEnabled(true);
						btnPdf.setEnabled(false);		
					}
				}
			}
		});
		txtNome.setBounds(75, 64, 272, 26);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		// Uso do plainDocument para limitação de caracteres.
		txtNome.setDocument(new Validador(30));

		lblFoto = new JLabel("New label");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Carometro.class.getResource("/img/camera.png")));
		lblFoto.setBounds(474, 11, 256, 256);
		contentPane.add(lblFoto);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(0, 0, 46, 14);
		contentPane.add(lblNewLabel_2);

		btnDownload = new JButton("Carregar Foto");
		btnDownload.setEnabled(false);
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnDownload.setForeground(SystemColor.textHighlight);
		btnDownload.setBounds(214, 106, 133, 26);
		contentPane.add(btnDownload);

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

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Carometro.class.getResource("/img/create.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setBounds(23, 217, 64, 64);
		contentPane.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setIcon(new ImageIcon(Carometro.class.getResource("/img/update.png")));
		btnEditar.setToolTipText("Alterar");
		btnEditar.setBounds(97, 217, 64, 64);
		contentPane.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Carometro.class.getResource("/img/delete.png")));
		btnExcluir.setToolTipText("excluir");
		btnExcluir.setBounds(169, 217, 64, 64);
		contentPane.add(btnExcluir);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Carometro.class.getResource("/img/search.png")));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(348, 64, 24, 24);
		contentPane.add(lblNewLabel_1);
		
		btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBorderPainted(false);
		btnSobre.setIcon(new ImageIcon(Carometro.class.getResource("/img/info.png")));
		btnSobre.setBounds(738, 11, 48, 48);
		contentPane.add(btnSobre);
		
		btnPdf = new JButton("");
		btnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarPdf();
			}
		});
		btnPdf.setIcon(new ImageIcon(Carometro.class.getResource("/img/pdf.png")));
		btnPdf.setToolTipText("Gerar lista de alunos");
		btnPdf.setBounds(382, 217, 64, 64);
		contentPane.add(btnPdf);

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
				fotoCarregada = true;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "PRENCHA O NOME");
			txtNome.requestFocus();
		} else if (tamanho == 0) {
			JOptionPane.showMessageDialog(null, "ADICIONE A FOTO");
		} else {
			String insert = "insert into funcionários(nome,foto) values (? ,?)";
			try {
				con = dao.conection();
				pst = con.prepareStatement(insert);
				pst.setString(1, txtNome.getText());
				pst.setBlob(2, fis, tamanho);
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Funcionário Cadastrado");
				} else {
					JOptionPane.showMessageDialog(null, "Funcionário Não Cadastrado");
				}
				con.close();
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
					txtRe.setEditable(false);
					txtRe.setText(null);
					btnBuscar.setEnabled(false);
					btnDownload.setEnabled(true);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

				} else {
					int confirma = JOptionPane.showConfirmDialog(null,
							"Funcionário não cadastrado! /nDeseja cadastrar o Funcionário?", "AVISO",
							JOptionPane.YES_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						txtRe.setEditable(false);
						txtRe.setText(null);
						btnBuscar.setEnabled(false);
						txtNome.setText(null);
						txtNome.requestFocus();
						btnDownload.setEnabled(true);
						btnAdicionar.setEnabled(true);
						btnPdf.setEnabled(false);
					} else {
						reset();
					}
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void listarNomes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listNomes.setModel(modelo);
		String readLista = "select * from funcionários where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conection();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneLista.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPaneLista.setVisible(false);
				}
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarNome() {
		int linha = listNomes.getSelectedIndex();
		if (linha >= 0) {
			String readNome = "select * from funcionários where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + ", 1";
			try {
				con = dao.conection();
				pst = con.prepareStatement(readNome);
				rs = pst.executeQuery();
				while (rs.next()) {
					scrollPaneLista.setVisible(false);
					txtRe.setText(rs.getString(1));
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
					lblFoto.setIcon(foto);
					txtRe.setEditable(false);
					txtRe.setText(null);
					btnBuscar.setEnabled(false);
					btnDownload.setEnabled(true);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnPdf.setEnabled(false);

				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneLista.setVisible(false);
		}
	}

	private void editar() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "PRENCHA O NOME");
			txtNome.requestFocus();

		} else {
			if (fotoCarregada == true) {
				String update = "update funcionários set nome=? , foto=? where re = ?";

				try {
					con = dao.conection();
					pst = con.prepareStatement(update);
					pst.setString(1, txtNome.getText());
					pst.setBlob(2, fis, tamanho);
					pst.setString(3, txtRe.getText());
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Dados do Funcionário Alterados");
					} else {
						JOptionPane.showMessageDialog(null, "Funcionário Não Alterado");
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				String update = "update funcionários set nome=?  where re = ?";

				try {
					con = dao.conection();
					pst = con.prepareStatement(update);
					pst.setString(1, txtNome.getText());
					pst.setString(2, txtRe.getText());
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Dados do Funcionário Alterados");
					} else {
						JOptionPane.showMessageDialog(null, "Funcionário Não Alterado");
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		}
	}

	private void excluir() {
		int confirmaExcluir = JOptionPane.showConfirmDialog(null, "Confirma a exclusão?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmaExcluir == JOptionPane.YES_NO_OPTION) {
			String delete = "delete from funcionários where re=?";
			try {
				con = dao.conection();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtRe.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					reset();
					JOptionPane.showMessageDialog(null, "Funcioário Excluido");
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}
	
	private void gerarPdf() {
		Document document = new Document();
		//Gerar Pdf
		try {
			PdfWriter.getInstance(document, new FileOutputStream("funcionários.pdf"));
			document.open();
			document.add(new Paragraph("Listagem de Funcionários:"));
		} catch (Exception e) {
			System.out.println(e);
		}finally {
			document.close();
		}
		//abrir documento no leitor padrão do usuário
		try {
			Desktop.getDesktop().open(new File("funcionários.pdf"));
		} catch (Exception e2) {
			System.out.println(e2); 
		}
	}

	private void reset() { // apaga os campos quando o funcioário ja esta cadastrado e posiciona o cursor
							// no campo nome
		txtRe.setText(null);
		scrollPaneLista.setVisible(false);
		txtNome.setText(null);
		lblFoto.setIcon(new ImageIcon(Carometro.class.getResource("/img/camera.png")));
		txtNome.requestFocus();
		fotoCarregada = false;
		tamanho = 0;
		txtRe.setEditable(true);
		btnBuscar.setEnabled(true);
		btnDownload.setEnabled(false);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPdf.setEnabled(true);
	}
};
