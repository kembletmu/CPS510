public class Queries {

    public static void listGuests() {
        String sql = "SELECT guest_id, guest_name, guest_email, phone_number " +
                     "FROM Guests ORDER BY guest_id;";
        DB.runQuery(sql);
    }

    public static void searchGuestByName(String name) {
        if (name == null) return;
        // basic escaping for single quotes
        String safe = name.replace("'", "''");
        String sql = "SELECT guest_id, guest_name, guest_email, phone_number " +
                     "FROM Guests " +
                     "WHERE LOWER(guest_name) LIKE LOWER('%" + safe + "%');";
        DB.runQuery(sql);
    }

    public static void listReservations() {
        String sql =
            "SELECT r.reservation_id, g.guest_name, r.room_number, " +
            "       r.check_in_date, r.check_out_date " +
            "FROM Reservations r " +
            "JOIN Guests g ON r.guest_id = g.guest_id " +
            "ORDER BY r.reservation_id;";
        DB.runQuery(sql);
    }

    public static void updateGuestEmail(int guestId, String newEmail) {
        if (newEmail == null) return;
        String safe = newEmail.replace("'", "''");
        String sql = "UPDATE Guests SET guest_email = '" + safe + "' " +
                     "WHERE guest_id = " + guestId + ";";
        DB.runUpdate(sql);
    }

    public static void deleteGuest(int guestId) {
        String sql = "DELETE FROM Guests WHERE guest_id = " + guestId + ";";
        DB.runUpdate(sql);
    }
}