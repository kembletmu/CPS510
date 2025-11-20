public class SQL {

    // DROP TABLES
    public static final String[] DROP_TABLES = {
        "DROP TABLE IF EXISTS RoomAssignment;",
        "DROP TABLE IF EXISTS ServiceRequests;",
        "DROP TABLE IF EXISTS Invoices;",
        "DROP TABLE IF EXISTS Reservations;",
        "DROP TABLE IF EXISTS Rooms;",
        "DROP TABLE IF EXISTS RoomType;",
        "DROP TABLE IF EXISTS Staff;",
        "DROP TABLE IF EXISTS Guests;",
        "DROP TABLE IF EXISTS ServiceTypes;",
        "DROP TABLE IF EXISTS PostalAddress;",
        "DROP TABLE IF EXISTS ProvinceCountry;"
    };

    // CREATE TABLES
    public static final String[] CREATE_TABLES = {

        // ProvinceCountry
        "CREATE TABLE ProvinceCountry (" +
        "  province TEXT PRIMARY KEY," +
        "  country  TEXT NOT NULL" +
        ");",

        // PostalAddress
        "CREATE TABLE PostalAddress (" +
        "  postal_code TEXT PRIMARY KEY," +
        "  city        TEXT NOT NULL," +
        "  province    TEXT NOT NULL," +
        "  FOREIGN KEY(province) REFERENCES ProvinceCountry(province)" +
        ");",

        // Guests
        "CREATE TABLE Guests (" +
        "  guest_id       INTEGER PRIMARY KEY," +
        "  guest_name     TEXT NOT NULL," +
        "  guest_email    TEXT UNIQUE," +
        "  phone_number   TEXT," +
        "  card_details   TEXT," +
        "  accommodations TEXT," +
        "  preferences    TEXT," +
        "  street         TEXT," +
        "  postal_code    TEXT," +
        "  FOREIGN KEY(postal_code) REFERENCES PostalAddress(postal_code)" +
        ");",

        // Staff
        "CREATE TABLE Staff (" +
        "  staff_id         INTEGER PRIMARY KEY," +
        "  staff_name       TEXT NOT NULL," +
        "  staff_email      TEXT UNIQUE," +
        "  phone_number     TEXT," +
        "  staff_position   TEXT," +
        "  salary           REAL," +
        "  payment_schedule TEXT," +
        "  staff_status     TEXT," +
        "  street           TEXT," +
        "  postal_code      TEXT," +
        "  FOREIGN KEY(postal_code) REFERENCES PostalAddress(postal_code)" +
        ");",

        // RoomType
        "CREATE TABLE RoomType (" +
        "  room_type        TEXT PRIMARY KEY," +
        "  room_capacity    INTEGER NOT NULL," +
        "  price_per_night  REAL NOT NULL" +
        ");",

        // Rooms
        "CREATE TABLE Rooms (" +
        "  room_number      INTEGER PRIMARY KEY," +
        "  room_type        TEXT NOT NULL," +
        "  occupancy_status TEXT," +
        "  cleaning_status  TEXT," +
        "  FOREIGN KEY(room_type) REFERENCES RoomType(room_type)" +
        ");",

        // Reservations
        "CREATE TABLE Reservations (" +
        "  reservation_id    INTEGER PRIMARY KEY," +
        "  room_number       INTEGER NOT NULL," +
        "  guest_id          INTEGER NOT NULL," +
        "  reservation_date  TEXT," +   // store dates as 'YYYY-MM-DD'
        "  reservation_status TEXT," +
        "  check_in_date     TEXT," +
        "  check_out_date    TEXT," +
        "  FOREIGN KEY(room_number) REFERENCES Rooms(room_number)," +
        "  FOREIGN KEY(guest_id)    REFERENCES Guests(guest_id)" +
        ");",

        // Invoices
        "CREATE TABLE Invoices (" +
        "  invoice_id      INTEGER PRIMARY KEY," +
        "  reservation_id  INTEGER UNIQUE," +
        "  amount          REAL," +
        "  payment_status  TEXT," +
        "  payment_method  TEXT," +
        "  FOREIGN KEY(reservation_id) REFERENCES Reservations(reservation_id)" +
        ");",

        // ServiceTypes
        "CREATE TABLE ServiceTypes (" +
        "  service_type TEXT PRIMARY KEY," +
        "  service_cost REAL NOT NULL" +
        ");",

        // ServiceRequests
        "CREATE TABLE ServiceRequests (" +
        "  service_id     INTEGER PRIMARY KEY," +
        "  reservation_id INTEGER NOT NULL," +
        "  service_type   TEXT NOT NULL," +
        "  request_date   TEXT," +
        "  request_status TEXT," +
        "  FOREIGN KEY(reservation_id) REFERENCES Reservations(reservation_id)," +
        "  FOREIGN KEY(service_type)   REFERENCES ServiceTypes(service_type)" +
        ");",

        // RoomAssignment
        "CREATE TABLE RoomAssignment (" +
        "  staff_id        INTEGER NOT NULL," +
        "  room_number     INTEGER NOT NULL," +
        "  assignment_date TEXT    NOT NULL," +
        "  PRIMARY KEY (staff_id, room_number, assignment_date)," +
        "  FOREIGN KEY(staff_id)    REFERENCES Staff(staff_id)," +
        "  FOREIGN KEY(room_number) REFERENCES Rooms(room_number)" +
        ");"
    };

    // POPULATE WITH DUMMY DATA
    public static final String[] POPULATE_DATA = {

        // ProvinceCountry
        "INSERT INTO ProvinceCountry VALUES ('Ontario', 'Canada');",

        // PostalAddress
        "INSERT INTO PostalAddress VALUES ('A1B 2C3', 'Toronto',  'Ontario');",
        "INSERT INTO PostalAddress VALUES ('M2N 8L1', 'North York','Ontario');",
        "INSERT INTO PostalAddress VALUES ('M2N 8L2', 'Toronto',   'Ontario');",
        "INSERT INTO PostalAddress VALUES ('M2N 8L3', 'Toronto',   'Ontario');",
        "INSERT INTO PostalAddress VALUES ('M2N 8L5', 'Toronto',   'Ontario');",
        "INSERT INTO PostalAddress VALUES ('M5C 1W4', 'Toronto',   'Ontario');",
        "INSERT INTO PostalAddress VALUES ('M5C 1W5', 'Toronto',   'Ontario');",
        "INSERT INTO PostalAddress VALUES ('M5C 1W6', 'Toronto',   'Ontario');",

        // Guests
        "INSERT INTO Guests VALUES (" +
        "  1," +
        "  'Jack Nguyen'," +
        "  'Jack@email.com'," +
        "  '123-456-7890'," +
        "  '1234-5678-9012-3456 | 09/26'," +
        "  NULL," +
        "  NULL," +
        "  '10 Toronto Street'," +
        "  'A1B 2C3'" +
        ");",

        "INSERT INTO Guests VALUES (" +
        "  2," +
        "  'John Doe'," +
        "  'John@email.com'," +
        "  '647-123-4567'," +
        "  '4545-1090-2302-5748 | 05/29'," +
        "  'Wheelchair'," +
        "  'Closest to elevator'," +
        "  '3 Yonge Street'," +
        "  'M2N 8L1'" +
        ");",

        "INSERT INTO Guests VALUES (" +
        "  3," +
        "  'Jane Doe'," +
        "  'janedoe@email.com'," +
        "  '416-111-1111'," +
        "  '5555-6666-7777-8888 | 06/07'," +
        "  NULL," +
        "  'Quiet room'," +
        "  '1 Bay Street'," +
        "  'M2N 8L2'" +
        ");",

        "INSERT INTO Guests VALUES (" +
        "  4," +
        "  'Michael Chen'," +
        "  'michael.chen@email.com'," +
        "  '647-222-2222'," +
        "  '3333-2222-1111-0000 | 01/02'," +
        "  'Wheelchair'," +
        "  'Lower floor near lobby'," +
        "  '2 Bloor Street'," +
        "  'M2N 8L3'" +
        ");",

        "INSERT INTO Guests VALUES (" +
        "  5," +
        "  'Rick Rolls'," +
        "  'rick.rolls@email.com'," +
        "  '647-333-3333'," +
        "  '4444-3333-2222-1111 | 04/20'," +
        "  NULL," +
        "  NULL," +
        "  '5 Bloor Street'," +
        "  'M2N 8L5'" +
        ");",

        // Staff
        "INSERT INTO Staff VALUES (" +
        "  1," +
        "  'Kemble Xie'," +
        "  'kemblexie@hotel.ca'," +
        "  '416-123-4567'," +
        "  'Cleaning Staff'," +
        "  25.00," +
        "  'Bi-Weekly'," +
        "  'Working'," +
        "  '125 Yonge Street'," +
        "  'M5C 1W4'" +
        ");",

        "INSERT INTO Staff VALUES (" +
        "  2," +
        "  'Bob Smith'," +
        "  'bobsmith@hotel.ca'," +
        "  '416-234-5678'," +
        "  'Receptionist'," +
        "  30.00," +
        "  'Bi-Weekly'," +
        "  'Working'," +
        "  '200 Queen Street'," +
        "  'M5C 1W5'" +
        ");",

        "INSERT INTO Staff VALUES (" +
        "  3," +
        "  'Alex Johnson'," +
        "  'alexjohnson@hotel.ca'," +
        "  '647-555-1111'," +
        "  'Cleaning Staff'," +
        "  22.00," +
        "  'Weekly'," +
        "  'Working'," +
        "  '50 King Street'," +
        "  'M5C 1W6'" +
        ");",

        // RoomType 
        "INSERT INTO RoomType VALUES ('Double', 2, 100.00);",
        "INSERT INTO RoomType VALUES ('Single', 1, 80.00);",
        "INSERT INTO RoomType VALUES ('Suite',  4, 250.00);",

        // Rooms 
        "INSERT INTO Rooms VALUES (101, 'Double', 'Vacant',  'Pending');",
        "INSERT INTO Rooms VALUES (102, 'Single', 'Vacant',  'Pending');",
        "INSERT INTO Rooms VALUES (103, 'Suite',  'Occupied','Clean');",
        "INSERT INTO Rooms VALUES (104, 'Double', 'Vacant',  'Clean');",

        // Reservations
        "INSERT INTO Reservations VALUES (" +
        "  1, 101, 1," +
        "  '2025-09-18 23:17:00'," +
        "  'Pending'," +
        "  '2025-09-25 10:00:00'," +
        "  '2025-09-28 10:00:00'" +
        ");",

        "INSERT INTO Reservations VALUES (" +
        "  2, 102, 2," +
        "  '2025-09-19 10:00:00'," +
        "  'Confirmed'," +
        "  '2025-09-20 15:00:00'," +
        "  '2025-09-23 10:00:00'" +
        ");",

        "INSERT INTO Reservations VALUES (" +
        "  3, 103, 3," +
        "  '2025-09-15 09:30:00'," +
        "  'Pending'," +
        "  '2025-09-25 11:00:00'," +
        "  '2025-09-28 10:00:00'" +
        ");",

        "INSERT INTO Reservations VALUES (" +
        "  4, 104, 4," +
        "  '2025-09-21 08:15:00'," +
        "  'Pending'," +
        "  '2025-09-25 13:00:00'," +
        "  '2025-09-26 09:00:00'" +
        ");",

        // Invoices
        "INSERT INTO Invoices VALUES (1, 1,  30.0,  'Pending', NULL);",
        "INSERT INTO Invoices VALUES (2, 2, 240.0,  'Paid',    'Credit Card');",
        "INSERT INTO Invoices VALUES (3, 3, 750.0,  'Pending', 'Debit');",
        "INSERT INTO Invoices VALUES (4, 4, 120.0,  'Paid',    'Cash');",

        // ServiceTypes
        "INSERT INTO ServiceTypes VALUES ('Room Cleaning', 20.0);",
        "INSERT INTO ServiceTypes VALUES ('Room Service', 40.0);",
        "INSERT INTO ServiceTypes VALUES ('Massage',      30.0);",

        // ServiceRequests 
        "INSERT INTO ServiceRequests VALUES (" +
        "  1, 1, 'Room Cleaning'," +
        "  '2025-09-26 15:11:03'," +
        "  'Complete'" +
        ");",

        "INSERT INTO ServiceRequests VALUES (" +
        "  2, 2, 'Room Service'," +
        "  '2025-09-21 08:30:00'," +
        "  'Complete'" +
        ");",

        "INSERT INTO ServiceRequests VALUES (" +
        "  3, 2, 'Massage'," +
        "  '2025-09-22 12:00:00'," +
        "  'Complete'" +
        ");",

        "INSERT INTO ServiceRequests VALUES (" +
        "  4, 3, 'Room Cleaning'," +
        "  '2025-09-26 15:00:00'," +
        "  'Pending'" +
        ");",

        "INSERT INTO ServiceRequests VALUES (" +
        "  5, 4, 'Room Cleaning'," +
        "  '2025-09-25 10:00:00'," +
        "  'Pending'" +
        ");",

        // RoomAssignment
        "INSERT INTO RoomAssignment VALUES (" +
        "  1, 101, '2025-09-26 15:30:29'" +
        ");",

        "INSERT INTO RoomAssignment VALUES (" +
        "  1, 102, '2025-09-20 10:30:00'" +
        ");",

        "INSERT INTO RoomAssignment VALUES (" +
        "  3, 103, '2025-09-24 14:00:00'" +
        ");",

        "INSERT INTO RoomAssignment VALUES (" +
        "  3, 104, '2025-09-25 09:00:00'" +
        ");"
    };
}