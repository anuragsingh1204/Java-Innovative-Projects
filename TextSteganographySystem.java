import java.io.*;
import java.util.Scanner;

public class TextSteganographySystem {

    // Invisible Unicode Characters
    static final char ZERO_WIDTH_SPACE = '\u200B';
    static final char ZERO_WIDTH_NON_JOINER = '\u200C';

    // Encode Secret Message
    public static String encodeMessage(String coverText, String secret) {

        StringBuilder binary = new StringBuilder();

        // Convert secret message into binary
        for (char ch : secret.toCharArray()) {
            String bin = String.format("%8s", Integer.toBinaryString(ch)).replace(' ', '0');
            binary.append(bin);
        }

        // Convert binary into invisible characters
        StringBuilder hiddenData = new StringBuilder();
        for (char bit : binary.toString().toCharArray()) {
            if (bit == '0') {
                hiddenData.append(ZERO_WIDTH_SPACE);
            } else {
                hiddenData.append(ZERO_WIDTH_NON_JOINER);
            }
        }

        // Append invisible data directly to the end of cover text
        return coverText + hiddenData;
    }

    // Decode Hidden Message
    public static String decodeMessage(String encodedText) {

        StringBuilder binary = new StringBuilder();

        for (char ch : encodedText.toCharArray()) {
            if (ch == ZERO_WIDTH_SPACE) {
                binary.append("0");
            } else if (ch == ZERO_WIDTH_NON_JOINER) {
                binary.append("1");
            }
        }

        StringBuilder secret = new StringBuilder();

        // Convert binary back to text
        for (int i = 0; i + 8 <= binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int ascii = Integer.parseInt(byteStr, 2);
            secret.append((char) ascii);
        }

        return secret.toString();
    }

    // Read File
    public static String readFile(String fileName) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }

        br.close();
        return content.toString();
    }

    // Write File
    public static void writeFile(String fileName, String content) throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(content);
        bw.close();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Input cover file
            System.out.print("Enter Cover File Name (e.g., cover.txt): ");
            String coverFile = sc.nextLine();

            // Read cover text
            String coverText = readFile(coverFile);

            // Secret message
            System.out.print("Enter Secret Message: ");
            String secret = sc.nextLine();

            // Encode
            String encodedText = encodeMessage(coverText, secret);

            // Save encoded file
            String outputFile = "encoded_output.txt";
            writeFile(outputFile, encodedText);

            System.out.println("\nMessage Hidden Successfully!");
            System.out.println("Encoded File Saved As: " + outputFile);

            // Read encoded file
            String encodedFileText = readFile(outputFile);

            // Decode message
            String decodedMessage = decodeMessage(encodedFileText);

            System.out.println("\nRetrieved Hidden Message:");
            System.out.println(decodedMessage);

        } catch (FileNotFoundException e) {
            System.out.println("Error: The cover file you specified could not be found. Please make sure it exists.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}