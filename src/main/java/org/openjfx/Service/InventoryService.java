package org.openjfx.Service;

import org.openjfx.Model.InventoryItem;
import org.openjfx.Util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    // READ all
    public List<InventoryItem> getAll() {
        List<InventoryItem> list = new ArrayList<>();
        String sql = "SELECT id, nama, kategori, jumlah, kondisi, lokasi, CONVERT(varchar(20), tanggal_input, 120) AS tanggal_input FROM Inventaris ORDER BY id DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                InventoryItem it = new InventoryItem(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("kategori"),
                        rs.getInt("jumlah"),
                        rs.getString("kondisi"),
                        rs.getString("lokasi"),
                        rs.getString("tanggal_input")
                );
                list.add(it);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // CREATE
    public boolean add(String nama, String kategori, int jumlah, String kondisi, String lokasi) {
        String sql = "INSERT INTO Inventaris (nama, kategori, jumlah, kondisi, lokasi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, kategori);
            ps.setInt(3, jumlah);
            ps.setString(4, kondisi);
            ps.setString(5, lokasi);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // UPDATE (contoh update jumlah dan kondisi)
    public boolean update(int id, int jumlah, String kondisi) {
        String sql = "UPDATE Inventaris SET jumlah = ?, kondisi = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setString(2, kondisi);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM Inventaris WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
