package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.br.projetopizzaria.db.DbException;
import com.br.projetopizzaria.model.entities.Cadastro;
import com.br.projetopizzaria.model.service.AlterarDadosService;

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

public class MainClientesController implements Initializable, DataChangeListener {

	private AlterarDadosService alterar;

	@FXML
	private TextField txtProcurar;

	@FXML
	private Button btNovoCliente;

	@FXML
	private Button btEditar;

	@FXML
	private Button btAtualizar;

	@FXML
	private TableView<Cadastro> tableViewClientes;

	@FXML
	private TableColumn<Cadastro, Long> tableColumnId;

	@FXML
	private TableColumn<Cadastro, String> tableColumnNome;

	@FXML
	private TableColumn<Cadastro, String> tableColumnSobrenome;

	@FXML
	private TableColumn<Cadastro, String> tableColumnTelefone;

	@FXML
	private TableColumn<Cadastro, Cadastro> tableColumnEdit;

	@FXML
	private TableColumn<Cadastro, Cadastro> tableColumnRemove;

	private ObservableList<Cadastro> obsList = FXCollections.observableArrayList();

	// Ao clicar no botao, irá abrir a tela de cadastro de cliente.
	@FXML
	public void onBtNovoCliente(ActionEvent event) {
		createDialogForm(new Cadastro(), "/gui/Cadastro.fxml", Utils.currentStage(event));
	}

	// Injetar depedencia manualmente
	public void setAlterarDados(AlterarDadosService alterar) {
		this.alterar = alterar;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	// Popular os dados das tabelas, ação que ira ocorrer ao abrir a tela.
	public void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<Cadastro, Long>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<Cadastro, String>("nome"));
		tableColumnSobrenome.setCellValueFactory(new PropertyValueFactory<Cadastro, String>("sobrenome"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<Cadastro, String>("telefone"));
		
		setAlterarDados(new AlterarDadosService());
		updateTableView();
		pesquisarDados();
		initEditButtons();
		initRemoveButtons();
	}
	
	//Pesquisa na tabela os dados informados na barra de pesquisa
	public void pesquisarDados() {
		FilteredList<Cadastro> filtrarDados = new FilteredList<>(obsList, b -> true);
		txtProcurar.textProperty().addListener((observable, oldValue, newValue) -> {
			filtrarDados.setPredicate(Cadastro -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}

				String procuraTeclado = newValue.toLowerCase();
				if (Cadastro.getNome().toLowerCase().contains(procuraTeclado)) {
					return true;
				} else if (Cadastro.getSobrenome().toLowerCase().contains(procuraTeclado)) {
					return true;
				} else if (Cadastro.getTelefone().toLowerCase().contains(procuraTeclado)) {
					return true;
				}
				return false;
			});
		});
		SortedList<Cadastro> sortedData = new SortedList<>(filtrarDados);
		sortedData.comparatorProperty().bind(tableViewClientes.comparatorProperty());
		tableViewClientes.setItems(sortedData);	
	}

	// Atualizar a tabela de Clientes
	public void updateTableView() {
		setAlterarDados(new AlterarDadosService());
		if (alterar == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Cadastro> list = alterar.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClientes.setItems(obsList);

	}

	// Abrir uma janela ao clicar em algum botao
	private void createDialogForm(Cadastro obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroController controller = loader.getController();
			controller.setCadastro(obj);
			controller.setAlterardados(new AlterarDadosService());
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

	public void onDataChanged() {
		updateTableView();
		pesquisarDados();
	}

	// Comando para criar o botao editar e abrir uma janela com os dados já
	// preenchidos
	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Cadastro, Cadastro>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Cadastro obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/Cadastro.fxml", Utils.currentStage(event)));
			}
		});
	}

	//Iniciar o botao remover
	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Cadastro, Cadastro>() {
			private final Button button = new Button("Delete");

			@Override
			protected void updateItem(Cadastro obj, boolean empty) {
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
	private void removeEntity(Cadastro obj) {
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

}
