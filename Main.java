import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Hotel DBMS - Assignment 9");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(0, 1, 5, 5));

        JButton dropBtn        = new JButton("Drop Tables");
        JButton createBtn      = new JButton("Create Tables");
        JButton populateBtn    = new JButton("Populate Tables");
        JButton listGuestsBtn  = new JButton("List All Guests");
        JButton searchGuestBtn = new JButton("Search Guest by Name");
        JButton listResBtn     = new JButton("List Reservations");
        JButton updateEmailBtn = new JButton("Update Guest Email");
        JButton deleteGuestBtn = new JButton("Delete Guest");
        JButton exitBtn        = new JButton("Exit");

        frame.add(dropBtn);
        frame.add(createBtn);
        frame.add(populateBtn);
        frame.add(listGuestsBtn);
        frame.add(searchGuestBtn);
        frame.add(listResBtn);
        frame.add(updateEmailBtn);
        frame.add(deleteGuestBtn);
        frame.add(exitBtn);

        // --- Actions ---
        dropBtn.addActionListener(e -> {
            DB.runBatch(SQL.DROP_TABLES);
            JOptionPane.showMessageDialog(frame, "Tables dropped (if they existed).");
        });

        createBtn.addActionListener(e -> {
            DB.runBatch(SQL.CREATE_TABLES);
            JOptionPane.showMessageDialog(frame, "Tables created.");
        });

        populateBtn.addActionListener(e -> {
            DB.runBatch(SQL.POPULATE_DATA);
            JOptionPane.showMessageDialog(frame, "Dummy data inserted.");
        });

        listGuestsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Guest list printed to console (Terminal).");
            Queries.listGuests();
        });

        searchGuestBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame,
                    "Enter part of the guest name:");
            if (name != null && !name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Search results printed to console.");
                Queries.searchGuestByName(name.trim());
            }
        });

        listResBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Reservations printed to console.");
            Queries.listReservations();
        });

        updateEmailBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(frame,
                    "Enter guest_id to update email:");
            if (idStr == null) return;
            try {
                int id = Integer.parseInt(idStr.trim());
                String email = JOptionPane.showInputDialog(frame,
                        "Enter new email:");
                if (email != null && !email.trim().isEmpty()) {
                    Queries.updateGuestEmail(id, email.trim());
                    JOptionPane.showMessageDialog(frame, "Email updated (if guest exists).");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid guest_id.");
            }
        });

        deleteGuestBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(frame,
                    "Enter guest_id to delete:");
            if (idStr == null) return;
            try {
                int id = Integer.parseInt(idStr.trim());
                Queries.deleteGuest(id);
                JOptionPane.showMessageDialog(frame, "Guest deleted (if existed).");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid guest_id.");
            }
        });

        exitBtn.addActionListener(e -> System.exit(0));

        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);
    }
}