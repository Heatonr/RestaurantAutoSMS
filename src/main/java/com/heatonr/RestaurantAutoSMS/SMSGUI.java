package com.heatonr.RestaurantAutoSMS;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.*;

public class SMSGUI extends Application {
    private SMSSender sender;
    private TableView<Customer> table;
    private Slider waitTimeSlider;
    private Customer selectedCustomer;
    static ObservableList<Customer> customerObservableList;

    public double getWaitTime() {
        return waitTimeSlider.getValue();
    }

    @Override
        public void start(Stage pStage) {
            sender = new SMSSender(this);
            DocSaver saver = new DocSaver(sender, this);
            Group optionsGroup = new Group();
            customerObservableList = FXCollections.observableArrayList();
            table = new TableView<>();
            waitTimeSlider = new Slider(10, 80, 20);
            Label waitTimeLabel = new Label("Wait Time:");

            saver.start();

            Scene scene = new Scene(new Group());
            pStage.setTitle("SMS Manager");
            pStage.setWidth(600);
            pStage.setHeight(500);
            pStage.setOnCloseRequest(event -> saver.stopWork());

            final Label label = new Label("Current Orders");
            label.setFont(new Font("Arial", 20));

            table.setEditable(false);

            TableColumn<Customer, CheckBox> completedCol = new TableColumn<>("Done");
            completedCol.setCellValueFactory(new PropertyValueFactory<>("completed"));
            TableColumn<Customer, String> phoneNumberCol = new TableColumn<>("Phone number");
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Customer, Long> waitTimeCol = new TableColumn<>("Wait time");
            waitTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            TableColumn<Customer, Boolean> sendbuttonCol = new TableColumn<>("Send\nmessage");
            sendbuttonCol.setCellValueFactory(new PropertyValueFactory<>("sendFinished"));

            completedCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
            phoneNumberCol.prefWidthProperty().bind(table.widthProperty().multiply(0.325));
            nameCol.prefWidthProperty().bind(table.widthProperty().multiply(.325));
            waitTimeCol.prefWidthProperty().bind(table.widthProperty().multiply(.15));
            sendbuttonCol.prefWidthProperty().bind(table.widthProperty().multiply(.1));

            sendbuttonCol.setCellFactory(
                    p -> new ButtonCell());
            table.setItems(customerObservableList);
            table.getColumns().addAll(completedCol, phoneNumberCol, nameCol, waitTimeCol, sendbuttonCol);

            table.setRowFactory(tv -> {
                TableRow<Customer> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY) {
                        selectedCustomer = row.getItem();
                    }
                });
                return row ;
            });

            Button removeButton = new Button("Finish Order");
            removeButton.setOnAction(actionEvent ->  {
                    if(selectedCustomer != null) {
                        customerObservableList.remove(selectedCustomer);
                        selectedCustomer = null;
                        table.refresh();
                    }
                }
            );

            final HBox hbox = new HBox();
            optionsGroup.getChildren().add(removeButton);

            waitTimeSlider.setPrefSize(250,30);
            waitTimeSlider.setMajorTickUnit(10);
            waitTimeSlider.setMinorTickCount(1);
            waitTimeSlider.setSnapToTicks(true);
            waitTimeSlider.setShowTickMarks(true);
            waitTimeSlider.setShowTickLabels(true);

            optionsGroup.getChildren().add(waitTimeSlider);
            optionsGroup.getChildren().add(waitTimeLabel);

            waitTimeSlider.relocate(100,20);
            waitTimeLabel.relocate(102,0);
            removeButton.relocate(0,5);
            hbox.getChildren().add(optionsGroup);
            hbox.setSpacing(3);

            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.prefWidthProperty().bind(pStage.widthProperty().subtract(20));
            vbox.prefHeightProperty().bind(pStage.heightProperty().subtract(20));
            vbox.setPadding(new Insets(10, 10, 15, 10));
            vbox.getChildren().addAll(label, table, hbox);

            ((Group) scene.getRoot()).getChildren().addAll(vbox);

            pStage.setScene(scene);
            pStage.show();
            TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
            header.setMouseTransparent(true);


            new Timer(60000, e -> {
                for (Customer c:
                     customerObservableList) {
                    c.setStartTime(c.getStartTime() + 1);
                }
                table.refresh();
            }).start();

            new Timer(500, e -> table.refresh()).start();

            colorFactory(waitTimeCol);
            checkBoxFactory(completedCol);
        }
    private void colorFactory(TableColumn<Customer, Long> waitTimeColumn) {
        waitTimeColumn.setCellFactory(column -> new TableCell<Customer, Long>() {
            @Override
            protected void updateItem(Long waitTime, boolean empty) {
                super.updateItem(waitTime, empty);

                setText(empty ? "" : getItem().toString());
                TableRow currentRow = getTableRow();

                if (!isEmpty() && (!currentRow.getStyle().equals("-fx-background-color: lightgreen"))) {
                    if(waitTime > SMSGUI.this.waitTimeSlider.getValue())
                        currentRow.setStyle("-fx-background-color:lightcoral");
                    else
                        currentRow.setStyle("-fx-background-color:gold");
                }
            }
        });
    }
    private void checkBoxFactory(TableColumn<Customer, CheckBox> completedColumn){
            completedColumn.setCellFactory(column -> new TableCell<Customer, CheckBox>() {
                @Override
                protected void updateItem(CheckBox completed, boolean empty){
                    super.updateItem(completed, empty);

                    setGraphic(getItem());
                    TableRow currentRow = getTableRow();
                    if(!empty) {
                        if (!completed.isSelected()) {
                            currentRow.setStyle("-fx-background-color: lightgreen");
                        } else
                            currentRow.setStyle(null);
                    }
                }
            });
    }

    private class ButtonCell extends TableCell<Customer, Boolean> {
        final Button cellButton = new Button("Send");
        ButtonCell(){
            cellButton.setOnAction(t -> {
                Customer sendCustomer;
                Object obj = getTableRow().getItem();
                if(obj instanceof Customer){
                    sendCustomer = (Customer) obj;
                    sender.sendMessage(sendCustomer.getPhoneNumber(), "Your order is ready.");
                    customerObservableList.remove(sendCustomer);
                    table.refresh();
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

    public void printError(String errorMessage){
        new Alert(Alert.AlertType.ERROR, errorMessage).showAndWait();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
