import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PimaCorrelation {

    // Method to calculate correlation coefficient
    public static double Correlation(ArrayList<Double> xs, ArrayList<Double> ys) {
        // Check if both ArrayLists are non-null and of the same length
        if (xs == null || ys == null || xs.size() != ys.size()) {
            throw new IllegalArgumentException("Input arrays must not be null and must have the same length.");
        }

        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;

        int n = xs.size();  // Get the size of the ArrayLists

        // Loop through the elements in the ArrayLists
        for (int i = 0; i < n; ++i) {
            double x = xs.get(i);
            double y = ys.get(i);

            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }

        // Covariance
        double cov = sxy / n - sx * sy / n / n;
        // Standard deviation of x
        double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
        // Standard deviation of y
        double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

        // Correlation is the normalized covariance
        return cov / sigmax / sigmay;
    }

    public static void main(String[] args) {
        String filename = "diabetes.csv";
        FileInputStream file = null;
        try {
            file = new FileInputStream(filename);
        }
        catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found. Exiting program.");
            System.exit(1);
        }
        int col = 1;
        Scanner reader = new Scanner(file);
        String line = reader.nextLine();
        String[] data = line.split(",");
        System.out.println("Reading column \"" + data[col] + "\" and \"Outcome\"");

        int sampleSize = 0;
        ArrayList<Double> X = new ArrayList<>();
        ArrayList<Double> Y = new ArrayList<>();
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            data = line.split(",");
            if (!data[col].equals("0")) {
                X.add(Double.parseDouble(data[col]));
                Y.add(Double.parseDouble(data[8]));
                //System.out.println(X.getLast() + ", " + Y.getLast());
                sampleSize++;
            }
        }

        System.out.println("Sample Size: " + sampleSize);
        // Function call to correlationCoefficient
        System.out.printf("%6f\n", Correlation(X, Y));
    }
}