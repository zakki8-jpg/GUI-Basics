import javax.swing.JOptionPane;

public class MyHotelReservationSystemGUI {

    public static void main(String[] args) {
        
        // Initialize the hotel room array (7 floors, 5 rooms per floor)
        int[][] hotel = new int[7][5];

        int choice;

        while (true) {
            // Display main menu using JOptionPane
            String menu = "\n=================================\n" +
                          "   HOTEL RESERVATION SYSTEM\n" +
                          "=================================\n" +
                          "1. View Rooms\n" +
                          "2. Check In\n" +
                          "3. Check Out\n" +
                          "4. Exit\n" +
                          "Enter choice (1-4):";
            
            choice = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (choice) {
                case 1:
                    // Show room status
                    StringBuilder roomStatus = new StringBuilder("\n--- ROOM STATUS ---\n");
                    for (int i = 6; i >= 0; i--) { // Floor 7 to Floor 1
                        roomStatus.append("Floor " + (i + 1) + ": ");
                        for (int j = 0; j < 5; j++) {
                            roomStatus.append("[" + hotel[i][j] + "]");
                        }
                        roomStatus.append("\n");
                    }
                    JOptionPane.showMessageDialog(null, roomStatus.toString());
                    break;

                case 2:
                    // Check-in
                    int inFloor = Integer.parseInt(JOptionPane.showInputDialog("Enter floor (1-7):"));
                    int inRoom = Integer.parseInt(JOptionPane.showInputDialog("Enter room (1-5):"));

                    if (inFloor < 1 || inFloor > 7 || inRoom < 1 || inRoom > 5) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Please try again.");
                        break;
                    }

                    if (hotel[inFloor - 1][inRoom - 1] == 0) {
                        hotel[inFloor - 1][inRoom - 1] = 1;
                        JOptionPane.showMessageDialog(null, "Check-in successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Room already occupied!");
                    }
                    break;

                case 3:
                    // Check-out
                    int outFloor = Integer.parseInt(JOptionPane.showInputDialog("Enter floor (1-7):"));
                    int outRoom = Integer.parseInt(JOptionPane.showInputDialog("Enter room (1-5):"));

                    if (outFloor < 1 || outFloor > 7 || outRoom < 1 || outRoom > 5) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Please try again.");
                        break;
                    }

                    if (hotel[outFloor - 1][outRoom - 1] == 1) {
                        hotel[outFloor - 1][outRoom - 1] = 0;
                        JOptionPane.showMessageDialog(null, "Check-out successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Room is already empty!");
                    }
                    break;

                case 4:
                    // Exit the system
                    JOptionPane.showMessageDialog(null, "Exiting system... Goodbye!");
                    return;

                default:
                    // Handle invalid choice
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1-4 only.");
            }
        }
    }
}