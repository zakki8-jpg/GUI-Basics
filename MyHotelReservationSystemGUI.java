//Balucan
import javax.swing.JOptionPane;

public class MyHotelReservationSystemGUI {

    public static void main(String[] args) {
        
        //flr&rms
        int[][] hotel = new int[7][5];

        int choice;

        while (true) {
            //MainMenu
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
                    //ShowRmStatus
                    StringBuilder roomStatus = new StringBuilder("\n--- ROOM STATUS ---\n");
                    for (int i = 6; i >= 0; i--) { //Flr 7 to Flr 1
                        roomStatus.append("Floor " + (i + 1) + ": ");
                        for (int j = 0; j < 5; j++) {
                            roomStatus.append("[" + hotel[i][j] + "]");
                        }
                        roomStatus.append("\n");
                    }
                    JOptionPane.showMessageDialog(null, roomStatus.toString());
                    break;

                case 2:
                    //ChkIn
                    int inFloor = Integer.parseInt(JOptionPane.showInputDialog("Enter floor (1-7):"));
                    int inRoom = Integer.parseInt(JOptionPane.showInputDialog("Enter room (1-5):"));

                    if (inFloor < 1 || inFloor > 7 || inRoom < 1 || inRoom > 5) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Please try again.");
                        break;
                    }

                    if (hotel[inFloor - 1][inRoom - 1] == 0) {
                        //ConfirmChkIn
                        int confirmCheckIn = JOptionPane.showConfirmDialog(
                                null, 
                                "Are you sure you want to check in to Floor " + inFloor + ", Room " + inRoom + "?",
                                "Confirm Check-In", 
                                JOptionPane.YES_NO_OPTION);
                        
                        if (confirmCheckIn == JOptionPane.YES_OPTION) {
                            hotel[inFloor - 1][inRoom - 1] = 1;  // Mark room as occupied
                            JOptionPane.showMessageDialog(null, "Check-in successful!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Check-in cancelled.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Room already occupied!");
                    }
                    break;

                case 3:
                    // ChkOut
                    int outFloor = Integer.parseInt(JOptionPane.showInputDialog("Enter floor (1-7):"));
                    int outRoom = Integer.parseInt(JOptionPane.showInputDialog("Enter room (1-5):"));

                    if (outFloor < 1 || outFloor > 7 || outRoom < 1 || outRoom > 5) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Please try again.");
                        break;
                    }

                    if (hotel[outFloor - 1][outRoom - 1] == 1) {
                        //ConfirmChkOut
                        int confirmCheckOut = JOptionPane.showConfirmDialog(
                                null, 
                                "Are you sure you want to check out from Floor " + outFloor + ", Room " + outRoom + "?",
                                "Confirm Check-Out", 
                                JOptionPane.YES_NO_OPTION);
                        
                        if (confirmCheckOut == JOptionPane.YES_OPTION) {
                            hotel[outFloor - 1][outRoom - 1] = 0;  // Mark room as available
                            JOptionPane.showMessageDialog(null, "Check-out successful!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Check-out cancelled.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Room is already empty!");
                    }
                    break;

                case 4:
                    // Exit
                    JOptionPane.showMessageDialog(null, "Exiting system... Goodbye!");
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1-4 only.");
            }
        }
    }
}