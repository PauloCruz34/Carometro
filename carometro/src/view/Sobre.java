package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGitHub;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setTitle("Sobre o Carometro");
		setResizable(false);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Sobre.class.getResource("/img/317712_code repository_github_repository_resource_icon.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Projeto Carometro");
		lblNewLabel.setBounds(31, 35, 163, 19);
		getContentPane().add(lblNewLabel);

		JLabel lblAutorPauloAfonso = new JLabel("@autor Paulo Afonso");
		lblAutorPauloAfonso.setBounds(31, 65, 163, 19);
		getContentPane().add(lblAutorPauloAfonso);

		JLabel lblLicenaMitLicense = new JLabel("Licença: MIT license");
		lblLicenaMitLicense.setBounds(31, 95, 163, 19);
		getContentPane().add(lblLicenaMitLicense);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit.png")));
		lblNewLabel_1.setBounds(272, 23, 96, 96);
		getContentPane().add(lblNewLabel_1);

		btnGitHub = new JButton("");
		btnGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/PauloCruz34/Carometro");
			}
		});
		btnGitHub.setContentAreaFilled(false);
		btnGitHub.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		btnGitHub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGitHub.setBorderPainted(false);
		btnGitHub.setBounds(24, 173, 48, 48);
		getContentPane().add(btnGitHub);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(272, 193, 89, 23);
		getContentPane().add(btnOk);

	}

	private void link(String url) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(url);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
