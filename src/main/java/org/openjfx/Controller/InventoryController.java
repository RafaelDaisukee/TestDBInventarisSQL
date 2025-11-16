package org.openjfx.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.openjfx.Model.InventoryItem;
import org.openjfx.Service.InventoryService;
import javafx.scene.layout.GridPane;


import java.util.Optional;

public class InventoryController {

    @FXML private TableView<InventoryItem> tableInventory;
    @FXML private TableColumn<InventoryItem, Number> colId;
    @FXML private TableColumn<InventoryItem, String> colNama;
    @FXML private TableColumn<InventoryItem, String> colKategori;
    @FXML private TableColumn<InventoryItem, Number> colJumlah;
    @FXML private TableColumn<InventoryItem, String> colKondisi;
    @FXML private TableColumn<InventoryItem, String> colLokasi;
    @FXML private TableColumn<InventoryItem, String> colTanggal;
    @FXML private TextField searchField;

    private final InventoryService service = new InventoryService();
    private final ObservableList<InventoryItem> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // bind columns
        colId.setCellValueFactory(cell -> cell.getValue().idProperty());
        colNama.setCellValueFactory(cell -> cell.getValue().namaProperty());
        colKategori.setCellValueFactory(cell -> cell.getValue().kategoriProperty());
        colJumlah.setCellValueFactory(cell -> cell.getValue().jumlahProperty());
        colKondisi.setCellValueFactory(cell -> cell.getValue().kondisiProperty());
        colLokasi.setCellValueFactory(cell -> cell.getValue().lokasiProperty());
        colTanggal.setCellValueFactory(cell -> cell.getValue().tanggalInputProperty());

        // load data
        refreshTable();
    }

    public void refreshTable() {
        data.setAll(service.getAll());
        tableInventory.setItems(data);
    }

    @FXML
    private void onRefresh() {
        refreshTable();
    }

    @FXML
    private void onAdd() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tambah Barang");
        // simple form using DialogPane content
        TextField nama = new TextField();
        TextField kategori = new TextField();
        TextField jumlah = new TextField();
        TextField kondisi = new TextField();
        TextField lokasi = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.addRow(0, new Label("Nama:"), nama);
        grid.addRow(1, new Label("Kategori:"), kategori);
        grid.addRow(2, new Label("Jumlah:"), jumlah);
        grid.addRow(3, new Label("Kondisi:"), kondisi);
        grid.addRow(4, new Label("Lokasi:"), lokasi);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> res = dialog.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            try {
                boolean ok = service.add(
                        nama.getText(),
                        kategori.getText(),
                        Integer.parseInt(jumlah.getText()),
                        kondisi.getText(),
                        lokasi.getText()
                );
                if (ok) refreshTable();
            } catch (NumberFormatException ex) {
                showAlert("Jumlah harus angka");
            }
        }
    }

    @FXML
    private void onEdit() {
        InventoryItem selected = tableInventory.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih baris yang ingin diedit");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Barang");
        TextField jumlah = new TextField(String.valueOf(selected.getJumlah()));
        TextField kondisi = new TextField(selected.getKondisi());

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.addRow(0, new Label("Jumlah:"), jumlah);
        grid.addRow(1, new Label("Kondisi:"), kondisi);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> res = dialog.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            try {
                boolean ok = service.update(selected.getId(), Integer.parseInt(jumlah.getText()), kondisi.getText());
                if (ok) refreshTable();
            } catch (NumberFormatException ex) {
                showAlert("Jumlah harus angka");
            }
        }
    }

    @FXML
    private void onDelete() {
        InventoryItem selected = tableInventory.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih baris yang ingin dihapus");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Hapus item: " + selected.getNama() + " ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> r = confirm.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.YES) {
            boolean ok = service.delete(selected.getId());
            if (ok) refreshTable();
        }
    }

    @FXML
    private void onSearch() {
        String q = searchField.getText();
        if (q == null || q.isEmpty()) {
            refreshTable();
            return;
        }
        ObservableList<InventoryItem> filtered = data.filtered(item ->
                item.getNama().toLowerCase().contains(q.toLowerCase()) ||
                item.getKategori().toLowerCase().contains(q.toLowerCase()));
        tableInventory.setItems(filtered);
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.showAndWait();
    }
}
