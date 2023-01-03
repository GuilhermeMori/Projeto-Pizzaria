package gui;

import java.net.URL;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.br.projetopizzaria.db.Db;
import com.br.projetopizzaria.db.DbException;
import com.br.projetopizzaria.model.dao.ClientesDao;
import com.br.projetopizzaria.model.dao.DaoFactory;
import com.br.projetopizzaria.model.entities.Clientes;
import com.br.projetopizzaria.model.exception.ValidationException;

import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.TextFieldFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientesController implements Initializable{
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtSobrenome;
	
	@FXML
	private TextField txtTelefone;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Label labelErrorSobrenome;
	
	@FXML
	private Label labelErrorTelefone;
	
	@FXML
	private Button btSave;

	@FXML
	private Button btLimpar;
	
	@FXML
	public void onBtSaveAction() {
		Connection conn = Db.getConecction();
		ClientesDao clientesdao = DaoFactory.createClientesDao();

		ValidationException exception = new ValidationException("Validation erro");
				
		try {
			String nome = txtNome.getText();
			String sobrenome = txtSobrenome.getText();
			String telefone = txtTelefone.getText();
	

			Clientes newClientes = new Clientes(nome, sobrenome, telefone);
			
			if (txtNome.getText() == null || txtNome.getText().trim().equals("")||txtNome.getText().isEmpty()) {
				exception.addError("nome", "Campos obrigatórios não preenchidos. Verifique e tente novamente");}

			if (txtSobrenome.getText()== null || txtSobrenome.getText().trim().equals("")|| txtSobrenome.getText().isEmpty()) {
				exception.addError("sobrenome", "Campos obrigatórios não preenchidos. Verifique e tente novamente");}
			
			if (txtTelefone.getText()== null || txtTelefone.getText().trim().equals("")||txtTelefone.getText().isEmpty()) {
				exception.addError("telefone", "Campos obrigatórios não preenchidos. Verifique e tente novamente");
			}
			
			if (exception.getErrors().size()>0) {
				throw exception;
			}
			
			clientesdao.insert(newClientes);
			Alerts.showAlert("Novo cliente cadastrado ", null, "Cliente cadastrado com sucesso", AlertType.INFORMATION);
		} 
		catch(ValidationException e ) {
			setErrosMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error ao Salvar", null, e.getMessage(), AlertType.ERROR);
			
		}
	}

	@FXML
	public void onBtLimparAction() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Limpar dados");
		alert.setContentText("Deseja realmente limpar os dados?");
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.get()== ButtonType.OK){
		txtNome.setText("");	
		txtSobrenome.setText("");
		txtTelefone.setText("");
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializenodes();
	}
	
	@FXML
	private void tftelefone() {
		TextFieldFormatter tff = new TextFieldFormatter();
		tff.setMask("(##)#####-####");
		tff.setCaracteresValidos("0123456789");
		tff.setTf(txtTelefone);
		tff.formatter();
	}
	
	private void initializenodes() {
		Constraints.setTextFieldMaxLength(txtNome, 100);
		Constraints.setTextFieldMaxLength(txtSobrenome, 100);
		tftelefone();
	}

	private void setErrosMessages(Map<String, String>errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
		if(fields.contains("sobrenome")) {
			labelErrorSobrenome.setText(errors.get("sobrenome"));
		}
		if(fields.contains("telefone")) {
			labelErrorTelefone.setText(errors.get("telefone"));
		}
	}
}