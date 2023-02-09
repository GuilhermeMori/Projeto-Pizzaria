package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.br.projetopizzaria.db.DbException;
import com.br.projetopizzaria.model.entities.Ingredientes;
import com.br.projetopizzaria.model.exception.ValidationException;
import com.br.projetopizzaria.model.service.IngredientesService;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroIngredientesController implements Initializable {
	
	private IngredientesService service;
	
	private Ingredientes entity;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtIngredientes;
	
	@FXML
	private Label labelErrorIngredientes;
	
	@FXML
	private Button btSave;

	@FXML
	private Button btLimpar;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
	public void setIngredientes(Ingredientes entity) {
		this.entity= entity;
	}
	

	public void setIngredientesService(IngredientesService service) {
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
			 Alert alert = new Alert(Alert.AlertType.INFORMATION);
			    alert.setTitle("Novo Ingrediente Adicionado");
			    alert.setHeaderText("");
			    alert.setContentText("Um Ingrediente foi adicionado.");
			    alert.showAndWait();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrosMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void setErrosMessages(Map<String, String> errors) {
		//Criar um set de erro
		Set<String> fields = errors.keySet();
		
		//Se conter o nome de algum dos campos, ira salvar esse erro.
		if(fields.contains("ingredientes")) {
			labelErrorIngredientes.setText(errors.get("ingredientes"));
		}
		
	}


	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}


	private Ingredientes getFormData() {
		Ingredientes ingredientes = new Ingredientes();
		
		ValidationException exception = new ValidationException("Validation error");
		
		ingredientes.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtIngredientes.getText() == null || txtIngredientes.getText().trim().equals("")||txtIngredientes.getText().isEmpty()) {
			exception.addError("ingredientes", "Campos obrigatórios não preenchidos. Verifique e tente novamente");}
		
		ingredientes.setDescricao(txtIngredientes.getText());

		if (exception.getErrors().size()>0) {
			throw exception;
		}
		
		return ingredientes;
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
		txtIngredientes.setText("");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rs) {
		initializenodes();
	}


	private void initializenodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtIngredientes, 50);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtIngredientes.setText(entity.getDescricao());
	}

}
