//Balucan
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class StudentFeedback {

    public static void main(String[] args) {

        int totalFeedbacks = 0;
        int totalRatings = 0;

        int excellent = 0;
        int good = 0;
        int average = 0;
        int poor = 0;
        int veryPoor = 0;

        StringBuilder fileContent = new StringBuilder();
        fileContent.append("--- Student Feedback Records ---\n\n");

        int choice;

        do {
            //Input
            String name = JOptionPane.showInputDialog("Enter Student Name:");
            String course = JOptionPane.showInputDialog("Enter Course/Subject:");
            String feedback = JOptionPane.showInputDialog("Enter Feedback Message:");

            int rating = 0;
            boolean valid = false;

            //Validate rating (1–5)
            while (!valid) {
                try {
                    rating = Integer.parseInt(
                        JOptionPane.showInputDialog("Enter Rating (1-5):")
                    );

                    if (rating >= 1 && rating <= 5) {
                        valid = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a number from 1 to 5.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Enter a number.");
                }
            }

            //Category
            String category = "";
            switch (rating) {
                case 5:
                    category = "Excellent";
                    excellent++;
                    break;
                case 4:
                    category = "Good";
                    good++;
                    break;
                case 3:
                    category = "Average";
                    average++;
                    break;
                case 2:
                    category = "Poor";
                    poor++;
                    break;
                case 1:
                    category = "Very Poor";
                    veryPoor++;
                    break;
            }

            //Update totals
            totalFeedbacks++;
            totalRatings += rating;

            //Append entry
            fileContent.append("Student Name: ").append(name).append("\n");
            fileContent.append("Course: ").append(course).append("\n");
            fileContent.append("Feedback: ").append(feedback).append("\n");
            fileContent.append("Rating: ").append(rating)
                       .append(" (").append(category).append(")\n\n");

            //Ask to continue
            choice = JOptionPane.showConfirmDialog(
                null,
                "Do you want to enter another feedback?",
                "Continue?",
                JOptionPane.YES_NO_OPTION
            );

        } while (choice == JOptionPane.YES_OPTION);

        //Calculations
        double averageRating = (double) totalRatings / totalFeedbacks;

        //Bonus 
        String message;
        if (averageRating >= 4.5) {
            message = "Outstanding Feedback!";
        } else if (averageRating >= 3.5) {
            message = "Good Feedback!";
        } else if (averageRating >= 2.5) {
            message = "Average Feedback";
        } else {
            message = "Needs Improvement";
        }

        //Append
        fileContent.append("--------------------------\n");
        fileContent.append("Total Feedbacks: ").append(totalFeedbacks).append("\n");
        fileContent.append("Average Rating: ").append(String.format("%.1f", averageRating)).append("\n\n");

        fileContent.append("Rating Summary:\n");
        fileContent.append("Excellent: ").append(excellent).append("\n");
        fileContent.append("Good: ").append(good).append("\n");
        fileContent.append("Average: ").append(average).append("\n");
        fileContent.append("Poor: ").append(poor).append("\n");
        fileContent.append("Very Poor: ").append(veryPoor).append("\n");
        fileContent.append("--------------------------\n");

        // Display results
        JOptionPane.showMessageDialog(
            null,
            "Total Feedbacks: " + totalFeedbacks +
            "\nAverage Rating: " + String.format("%.1f", averageRating) +
            "\n\n" + message
        );

        // Write to file (append mode)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            writer.write(fileContent.toString());
            writer.newLine();

            JOptionPane.showMessageDialog(null, "Feedback successfully saved to feedback.txt!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file: " + e.getMessage());
        }

        System.exit(0);
    }
}