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
	private Button btEditar;

	@FXML
	private Button btAtualizar;

	@FXML
	private TableView<SaboresIngredientes> tableViewSaboresIngredientes;

	@FXML
	private TableColumn<SaboresIngredientes, Integer> Id;
	
	@FXML
	private TableColumn<SaboresIngredientes, Integer> Ingredientes1;
	
	@FXML
	private TableColumn<SaboresIngredientes, Integer> Ingredientes2;
	
	@FXML
	private TableColumn<SaboresIngredientes, Integer> Ingredientes3;;
	
	@FXML
	private TableColumn<SaboresIngredientes, Integer> Ingredientes4;
	
	@FXML
	private TableColumn<SaboresIngredientes, Integer> Ingredientes5;

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

	//Método utilizado ao iniciar o sistema.
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	initializeNodes();		
	}
	
	//Irá chamar os metodos necessarios para alimentar a tabela saboresingredientes
	public void initializeNodes() {	
		Id.setCellValueFactory(new PropertyValueFactory<>("id"));
		Ingredientes1.setCellValueFactory(new PropertyValueFactory<>("ingredientes1"));
		Ingredientes2.setCellValueFactory(new PropertyValueFactory<>("ingredientes2"));
		Ingredientes3.setCellValueFactory(new PropertyValueFactory<>("ingredientes3"));
		Ingredientes4.setCellValueFactory(new PropertyValueFactory<>("ingredientes4"));
		Ingredientes5.setCellValueFactory(new PropertyValueFactory<>("ingredientes5"));
			
		setSaboresIngredientesService(new SaboresIngredientesService());
		updateTableView();
		initEditButtons();
		initRemoveButtons();
	}	
	
	//Atualizar tabela de saboresingredientes
	public void updateTableView() {
		setSaboresIngredientesService(new SaboresIngredientesService());
		if (alterar == null) {
			throw new IllegalStateException("Service was null");
		}
		List<SaboresIngredientes> list = alterar.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSaboresIngredientes.setItems(obsList);

	}
	
	//Metodo para criar o botao de remover
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

	//Método para adicionar o botao de remover
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
			controller.setSaboresIngredientesService(new SaboresIngredientesService());
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

	//Pesquisa na tabela os dados informados na barra de pesquisa
	public void pesquisarDados() {
		FilteredList<SaboresIngredientes> filtrarDados = new FilteredList<>(obsList, b -> true);
		txtProcurar.textProperty().addListener((observable, oldValue, newValue) -> {
			filtrarDados.setPredicate(SaboresIngredientes -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}

				// Realiza a buscas pelso 5 campos de id_ingredientes
				String procuraTeclado = newValue.toLowerCase();
				if (SaboresIngredientes.getIngredientes1().equals(Integer.valueOf(procuraTeclado))
				||SaboresIngredientes.getIngredientes2().equals(Integer.valueOf(procuraTeclado))
				||SaboresIngredientes.getIngredientes3().equals(Integer.valueOf(procuraTeclado))
				||SaboresIngredientes.getIngredientes4().equals(Integer.valueOf(procuraTeclado))
				||SaboresIngredientes.getIngredientes5().equals(Integer.valueOf(procuraTeclado))) {
					return true;
				} else {
					return false;
				}
				
			});
		});
		SortedList<SaboresIngredientes> sortedData = new SortedList<>(filtrarDados);
		sortedData.comparatorProperty().bind(tableViewSaboresIngredientes.comparatorProperty());
		tableViewSaboresIngredientes.setItems(sortedData);	
	}


	
}

