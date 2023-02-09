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
import com.br.projetopizzaria.model.entities.SaboresIngredientes;
import com.br.projetopizzaria.model.exception.ValidationException;
import com.br.projetopizzaria.model.service.IngredientesService;
import com.br.projetopizzaria.model.service.SaboresIngredientesService;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroSaboresController implements Initializable{

	private SaboresIngredientesService service;
	
	private SaboresIngredientes entity;
	
	@FXML
	private ComboBox<Ingredientes> boxSabores;
	
	@FXML
	private ComboBox<Ingredientes> boxSabores2;
	
	@FXML
	private ComboBox<Ingredientes> boxSabores3;
	
	@FXML
	private ComboBox<Ingredientes> boxSabores4;
	
	@FXML
	private ComboBox<Ingredientes> boxSabores5;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private Label labelErrorSabores;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btLimpar;
	
	private IngredientesService ingredientes;
	
	private ObservableList<Ingredientes> obsList = FXCollections.observableArrayList();
	
	private ObservableList<Ingredientes> obsList2 = FXCollections.observableArrayList();
	
	private ObservableList<Ingredientes> obsList3 = FXCollections.observableArrayList();
	
	private ObservableList<Ingredientes> obsList4 = FXCollections.observableArrayList();

	private ObservableList<Ingredientes> obsList5 = FXCollections.observableArrayList();
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	
	public void setSabores(SaboresIngredientes entity) {
		this.entity= entity;
	}
	
	public void setIngredientes(IngredientesService ingredientes) {
		this.ingredientes = ingredientes;
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
			 Alert alert = new Alert(Alert.AlertType.INFORMATION);
			    alert.setTitle("Novo SaboresIngredientes Adicionado");
			    alert.setHeaderText("");
			    alert.setContentText("O novo SaborIngrediente foi adicionado.");
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
	
	//Ação das caixas combobox ingredientes
	public void choiceBoxAction() {
		setIngredientes(new IngredientesService());
		if (ingredientes == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Ingredientes> list = ingredientes.findAll();
		obsList = FXCollections.observableArrayList(list);
		
		List<Ingredientes> list2 = ingredientes.findAll();
		obsList2 = FXCollections.observableArrayList(list2);
		
		List<Ingredientes> list3 = ingredientes.findAll();
		obsList3 = FXCollections.observableArrayList(list3);
		
		List<Ingredientes> list4 = ingredientes.findAll();
		obsList4 = FXCollections.observableArrayList(list4);
		
		List<Ingredientes> list5 = ingredientes.findAll();
		obsList5 = FXCollections.observableArrayList(list5);
		
		boxSabores.getItems().addAll(obsList);
		boxSabores2.getItems().addAll(obsList2);
		boxSabores3.getItems().addAll(obsList3);
		boxSabores4.getItems().addAll(obsList4);
		boxSabores5.getItems().addAll(obsList5);
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
	choiceBoxAction();
	}
	
	//Método para salvar os erros
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

	
	//Método para criar novo saborIngrediente
	private SaboresIngredientes injetarClasse(){
		SaboresIngredientes sabores = new SaboresIngredientes();
		
		ValidationException exception = new ValidationException("Validation error");
		
		sabores.setId(Utils.tryParseToInt(txtId.getText()));
		
		Ingredientes ingredientes1 = boxSabores.getSelectionModel().getSelectedItem();
		Ingredientes ingredientes2 = boxSabores2.getSelectionModel().getSelectedItem();
		Ingredientes ingredientes3 = boxSabores3.getSelectionModel().getSelectedItem();
		Ingredientes ingredientes4 = boxSabores4.getSelectionModel().getSelectedItem();
		Ingredientes ingredientes5 = boxSabores5.getSelectionModel().getSelectedItem();
		
		
		sabores.setIngredientes1(ingredientes1.getId());
		sabores.setIngredientes2(ingredientes2.getId());
		sabores.setIngredientes3(ingredientes3.getId());
		sabores.setIngredientes4(ingredientes4.getId());
		sabores.setIngredientes5(ingredientes5.getId());
		

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
		if(result.get()== ButtonType.OK){
		boxSabores.getSelectionModel().clearSelection();
		boxSabores2.getSelectionModel().clearSelection();
		boxSabores3.getSelectionModel().clearSelection();
		boxSabores4.getSelectionModel().clearSelection();
		boxSabores5.getSelectionModel().clearSelection();
		 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
		    alert1.setTitle("Limpo");
		    alert1.setHeaderText("");
		    alert1.setContentText("Os campos Ingredientes foram limpos.");
		    alert1.showAndWait();

		}	
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		boxSabores.setId(String.valueOf(entity.getIngredientes1()));
		boxSabores2.setId(String.valueOf(entity.getIngredientes2()));
		boxSabores3.setId(String.valueOf(entity.getIngredientes3()));
		boxSabores4.setId(String.valueOf(entity.getIngredientes4()));
		boxSabores5.setId(String.valueOf(entity.getIngredientes5()));
		
	}
}
