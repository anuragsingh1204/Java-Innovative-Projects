import java.util.Scanner;
import java.security.SecureRandom;

public class PasswordGenerator {

    // Method to generate password
    public static String generatePassword(String name, String pan, String dob) {

        SecureRandom random = new SecureRandom();
        String specialChars = "<{?@#&!*}>)(}][_=~^";

        // Extracting parts from user data
        String namePart = name.substring(0, 2).toUpperCase();
        String panPart = pan.substring(5, 8);
        String dobPart = dob.replace("/", "");

        // Random special character
        char specialChar1 = specialChars.charAt(random.nextInt(specialChars.length()));
        char specialChar2 = specialChars.charAt(random.nextInt(specialChars.length()));
        char specialChar3 = specialChars.charAt(random.nextInt(specialChars.length()));
        
        // Random 2-digit number
        int randomNum = random.nextInt(90) + 10;

        // Final password
        String password = randomNum + namePart + specialChar1 + panPart 
                + specialChar2 + dobPart.substring(0, 4) + specialChar3;

        return password;
    }

    // Method to check password strength
    public static String checkStrength(String password) {

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else hasSpecial = true;
        }

        int score = 0;

        if (hasUpper) score++;
        if (hasLower) score++;
        if (hasDigit) score++;
        if (hasSpecial) score++;

        if (password.length() >= 12 && score == 4)
            return "Strong";
        else if (password.length() >= 8 && score >= 3)
            return "Medium";
        else
            return "Weak";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of users: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nUser " + i);

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter PAN Number: ");
            String pan = sc.nextLine();

            System.out.print("Enter Date of Birth (DD/MM/YYYY): ");
            String dob = sc.nextLine();

            String password = generatePassword(name, pan, dob);

            System.out.println("Generated Password: " + password);

            String strength = checkStrength(password);

            System.out.println("Password Strength: " + strength);
        }

        sc.close();
    }
}