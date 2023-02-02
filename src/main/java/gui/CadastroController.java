package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.br.projetopizzaria.db.DbException;
import com.br.projetopizzaria.model.entities.Cadastro;
import com.br.projetopizzaria.model.exception.ValidationException;
import com.br.projetopizzaria.model.service.AlterarDadosService;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.TextFieldFormatter;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroController implements Initializable{
	
	private AlterarDadosService service;
	
	private Cadastro entity;
		
	@FXML
	private TextField txtId;
	
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
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	public void setCadastro(Cadastro entity) {
		this.entity = entity;
	}

	public void setAlterardados(AlterarDadosService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	//Acao do botao salvar
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrosMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	//Notifica a lista que é preciso atualizar
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	//Criar um novo cliente 
	private Cadastro getFormData() {
		Cadastro cadastro = new Cadastro();
		
		ValidationException exception = new ValidationException("Validation error");
		
		cadastro.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")||txtNome.getText().isEmpty()) {
			exception.addError("nome", "Campos obrigatórios não preenchidos. Verifique e tente novamente");}

		if (txtSobrenome.getText()== null || txtSobrenome.getText().trim().equals("")|| txtSobrenome.getText().isEmpty()) {
			exception.addError("sobrenome", "Campos obrigatórios não preenchidos. Verifique e tente novamente");}
		
		if (txtTelefone.getText()== null || txtTelefone.getText().trim().equals("")||txtTelefone.getText().isEmpty()) {
			exception.addError("telefone", "Campos obrigatórios não preenchidos. Verifique e tente novamente");
		}
		

		cadastro.setNome(txtNome.getText());
		cadastro.setSobrenome(txtSobrenome.getText());
		cadastro.setTelefone(txtTelefone.getText());
		
		if (exception.getErrors().size()>0) {
			throw exception;
		}
		
		return cadastro;
	}

	@FXML
	public void onBtLimparAction() {
		//Ira abrir um caixa de alert 
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Limpar dados");
		alert.setContentText("Deseja realmente limpar os dados?");
		
		Optional<ButtonType> result = alert.showAndWait();
		
		//Se clicar em ok , irá apagar todos os campos, 
		//se clicar em cancelar, não irá acontecer nada.
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
	
	//Mascara para o campo txtTelfone
	@FXML
	private void tftelefone() {
		TextFieldFormatter tff = new TextFieldFormatter();
		tff.setMask("(##)#####-####");
		tff.setCaracteresValidos("0123456789");
		tff.setTf(txtTelefone);
		tff.formatter();
	}
	
	//Irá iniciar assim que aberta a tela, podendo aceitar as configuraçoes abaixo
	private void initializenodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 100);
		Constraints.setTextFieldMaxLength(txtSobrenome, 100);
		
		tftelefone();
	}
	
	//Método para associar os dados digitados nas txtField com os atributos da classe 
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtSobrenome.setText(entity.getSobrenome());
		txtTelefone.setText(entity.getTelefone());
	}
	
	
	//Método para gravar todos os erros e seus respectivos nomes
	private void setErrosMessages(Map<String, String>errors) {
		//Criar um set de erro
		Set<String> fields = errors.keySet();
		
		//Se conter o nome de algum dos campos, ira salvar esse erro.
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