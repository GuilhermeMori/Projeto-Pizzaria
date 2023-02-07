package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.controlsfx.control.CheckComboBox;

import com.br.projetopizzaria.db.DbException;
import com.br.projetopizzaria.model.entities.Ingredientes;
import com.br.projetopizzaria.model.entities.SaboresIngredientes;
import com.br.projetopizzaria.model.exception.ValidationException;
import com.br.projetopizzaria.model.service.IngredientesService;
import com.br.projetopizzaria.model.service.SaboresIngredientesService;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroSaboresController implements Initializable{

	private SaboresIngredientesService service;
	
	private SaboresIngredientes entity;
	
	@FXML
	private CheckComboBox<Ingredientes> boxSabores;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtIdSabIngredientes;
	
	@FXML
	private Label labelErrorSabores;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btLimpar;
	
	private IngredientesService ingredientes;
	
	private ObservableList<Ingredientes> obsList = FXCollections.observableArrayList();
	
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private SaboresIngredientesService sabores;
	
	public void setSabores(SaboresIngredientes entity) {
		this.entity= entity;
	}
	
	public void setIngredientes(IngredientesService ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	public void seSaborestIngredientes(SaboresIngredientesService sabores) {
		this.sabores = sabores ;
	}
	public void setSaboresIngredientesService(SaboresIngredientesService service) {
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
			entity= injetarClasse();
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

	public void choiceBoxAction() {
		setIngredientes(new IngredientesService());
		if (ingredientes == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Ingredientes> list = ingredientes.findAll();
		obsList = FXCollections.observableArrayList(list);
		boxSabores.getItems().addAll(obsList);
		
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
	choiceBoxAction();
	initializenodes();
		
	}
	
	private void initializenodes() {
		Constraints.setTextFieldInteger(txtId);
	}
	

	
	private void setErrosMessages(Map<String, String> errors) {
		//Criar um set de erro
		Set<String> fields = errors.keySet();
		
		//Se conter o nome de algum dos campos, ira salvar esse erro.
		if(fields.contains("ingredientes")) {
			labelErrorSabores.setText(errors.get("sabores"));
		}
		
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	
	}

	private SaboresIngredientes injetarClasse(){
		SaboresIngredientes sabores = new SaboresIngredientes();
		
		ValidationException exception = new ValidationException("Validation error");
		
		sabores.setId(Utils.tryParseToInt(txtId.getText()));
		
		String str = "";
		
		ObservableList list = boxSabores.getCheckModel().getCheckedItems();
		
		
		for(Object obj:list) {
			str+= obj +",";
		}
		
		sabores.setDescricao(str);
		sabores.setIdIngredientes(1);
		

		if (exception.getErrors().size()>0) {
			throw exception;
		}
		
		return sabores;
	}

	public void onBtLimparAction() {
		//Ira abrir um caixa de alert 
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Limpar dados");
		alert.setContentText("Deseja realmente limpar os dados?");
		
		Optional<ButtonType> result = alert.showAndWait();
		
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
	}
}
