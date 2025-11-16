package org.openjfx.Model;

import javafx.beans.property.*;

public class InventoryItem {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final IntegerProperty jumlah = new SimpleIntegerProperty();
    private final StringProperty kondisi = new SimpleStringProperty();
    private final StringProperty lokasi = new SimpleStringProperty();
    private final StringProperty tanggalInput = new SimpleStringProperty();

    public InventoryItem() {}

    public InventoryItem(int id, String nama, String kategori, int jumlah, String kondisi, String lokasi, String tanggalInput) {
        setId(id);
        setNama(nama);
        setKategori(kategori);
        setJumlah(jumlah);
        setKondisi(kondisi);
        setLokasi(lokasi);
        setTanggalInput(tanggalInput);
    }

    // id
    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty idProperty() { return id; }

    // nama
    public String getNama() { return nama.get(); }
    public void setNama(String value) { nama.set(value); }
    public StringProperty namaProperty() { return nama; }

    // kategori
    public String getKategori() { return kategori.get(); }
    public void setKategori(String value) { kategori.set(value); }
    public StringProperty kategoriProperty() { return kategori; }

    // jumlah
    public int getJumlah() { return jumlah.get(); }
    public void setJumlah(int value) { jumlah.set(value); }
    public IntegerProperty jumlahProperty() { return jumlah; }

    // kondisi
    public String getKondisi() { return kondisi.get(); }
    public void setKondisi(String value) { kondisi.set(value); }
    public StringProperty kondisiProperty() { return kondisi; }

    // lokasi
    public String getLokasi() { return lokasi.get(); }
    public void setLokasi(String value) { lokasi.set(value); }
    public StringProperty lokasiProperty() { return lokasi; }

    // tanggalInput
    public String getTanggalInput() { return tanggalInput.get(); }
    public void setTanggalInput(String value) { tanggalInput.set(value); }
    public StringProperty tanggalInputProperty() { return tanggalInput; }
}
