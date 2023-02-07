package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.br.projetopizzaria.db.DbException;
import com.br.projetopizzaria.model.entities.SaboresIngredientes;
import com.br.projetopizzaria.model.service.SaboresIngredientesService;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainSaboresIngredientesController implements Initializable, DataChangeListener {

	private SaboresIngredientesService alterar;
	
	@FXML
	private TextField txtProcurar;

	@FXML
	private Button btNovoSaborIngrediente;

	@FXML
	private Button btProcurar;

	@FXML
	private Button btEditar;

	@FXML
	private Button btAtualizar;

	@FXML
	private TableView<SaboresIngredientes> tableViewClientes;

	@FXML
	private TableColumn<SaboresIngredientes, Long> tableColumnId;

	@FXML
	private TableColumn<SaboresIngredientes, String> tableColumnDescricao;
	
	@FXML
	private TableColumn<SaboresIngredientes, Long> tableColumnIdIngredientes;

	@FXML
	private TableColumn<SaboresIngredientes, SaboresIngredientes> tableColumnEdit;

	@FXML
	private TableColumn<SaboresIngredientes, SaboresIngredientes> tableColumnRemove;

	private ObservableList<SaboresIngredientes> obsList = FXCollections.observableArrayList();
	
	// Ao clicar no botao, irá abrir a tela de cadastro de cliente.
	@FXML
	public void onBtNovoSaboresIngrediente(ActionEvent event) {
		createDialogForm(new SaboresIngredientes(), "/gui/CadastroSaboresIngredientes.fxml", Utils.currentStage(event));
	}
	
	public void setSaboresIngredientesService(SaboresIngredientesService alterar) {
		this.alterar = alterar;
	}
	
	@Override
	public void onDataChanged() {
		updateTableView();
		pesquisarDados();	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	initializeNodes();		
	}
	
	public void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<SaboresIngredientes, Long>("id"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<SaboresIngredientes, String>("descricao"));
		tableColumnIdIngredientes.setCellValueFactory(new PropertyValueFactory<SaboresIngredientes, Long>("idIngredientes"));
		
		setSaboresIngredientesService(new SaboresIngredientesService());
		updateTableView();
		pesquisarDados();
		initEditButtons();
		initRemoveButtons();
	}	
	
	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<SaboresIngredientes, SaboresIngredientes>() {
			private final Button button = new Button("Delete");

			@Override
			protected void updateItem(SaboresIngredientes obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
//
	private void removeEntity(SaboresIngredientes obj) {
		Optional <ButtonType> result = Alerts.showConfirmation("Confirmação", "Você tem certeza que deseja deletar ?");
		
		if(result.get() == ButtonType.OK) {
			if (alterar == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				alterar.remove(obj);
				updateTableView();
			} 
			catch(DbException e){
				Alerts.showAlert("Error ao remover", null, e.getMessage(),AlertType.ERROR);
			}
		}
	}
	
	public void updateTableView() {
		setSaboresIngredientesService(new SaboresIngredientesService());
		if (alterar == null) {
			throw new IllegalStateException("Service was null");
		}
		List<SaboresIngredientes> list = alterar.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClientes.setItems(obsList);

	}
	
	//Pesquisa na tabela os dados informados na barra de pesquisa
	public void pesquisarDados() {
		FilteredList<SaboresIngredientes> filtrarDados = new FilteredList<>(obsList, b -> true);
		txtProcurar.textProperty().addListener((observable, oldValue, newValue) -> {
			filtrarDados.setPredicate(Ingredientes -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}

				String procuraTeclado = newValue.toLowerCase();
				if (Ingredientes.getDescricao().toLowerCase().contains(procuraTeclado)) {
					return true;
				} else {
					return false;
				}
				
			});
		});
		SortedList<SaboresIngredientes> sortedData = new SortedList<>(filtrarDados);
		sortedData.comparatorProperty().bind(tableViewClientes.comparatorProperty());
		tableViewClientes.setItems(sortedData);	
	}
	
	// Comando para criar o botao editar e abrir uma janela com os dados já
	// preenchidos
	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<SaboresIngredientes, SaboresIngredientes>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(SaboresIngredientes obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/CadastroSaboresIngredientes.fxml", Utils.currentStage(event)));
			}
		});
	}
	// Abrir uma janela ao clicar em algum botao
	
	private void createDialogForm(SaboresIngredientes obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroSaboresController controller = loader.getController();
			controller.setSabores(obj);
			controller.setSaboresIngredientesService(alterar);
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Pizzaria do Mori");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Excepetion", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}
	}
	
}

