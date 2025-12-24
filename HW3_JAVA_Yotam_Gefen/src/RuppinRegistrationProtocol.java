import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuppinRegistrationProtocol
{
    private static List<Client> clientState = Collections.synchronizedList(new ArrayList<>());
    //        הגדרת המצבים (STATES)
    private static final int WAITING = 0;

    // --- מצבי הרשמה ---
    private static final int REG_ENTER_USERNAME = 1;
    private static final int REG_ENTER_PASSWORD = 2;
    private static final int REG_ENTER_STATUS = 3;
    private static final int REG_ENTER_YEARS = 4;

    // --- מצבי התחברות ועדכון ---
    private static final int LOGIN_ENTER_USERNAME = 10;
    private static final int LOGIN_ENTER_PASSWORD = 11;
    private static final int ASK_IF_UPDATE = 12;
    private static final int ASK_CHANGE_NAME = 13;
    private static final int UPDATE_NAME_INPUT = 14;
    private static final int ASK_CHANGE_PASS = 15;
    private static final int UPDATE_PASS_INPUT = 16;
    private static final int ASK_CHANGE_YEARS = 17;
    private static final int UPDATE_YEARS_INPUT = 18;

    private int state = WAITING;

    private String tempUsername;
    private String tempPassword;
    private String tempStatus;

    private Client currentClient = null;

    public String processInput(String theInput) {

        if (theInput == null)
        {
            return "Do you want to register? (yes/no)";
        }

        if (state == WAITING)
        {
            if (theInput.equalsIgnoreCase("yes"))
            {
                state = REG_ENTER_USERNAME; // עוברים למצב קליטת שם להרשמה
                return "Enter a username:";
            } else if (theInput.equalsIgnoreCase("no"))
            {
                state = LOGIN_ENTER_USERNAME; // עוברים למצב קליטת שם להתחברות
                return "Username:";
            } else
            {
                return "Please type 'yes' or 'no'.";
            }
        }
        // מסלול הרשמה (New User)
        else if (state == REG_ENTER_USERNAME)
        {
            Client tempCheck = new Client(theInput, "", "", 0);
            synchronized (clientState)
            {
                if (clientState.contains(tempCheck))
                {
                    return "Name not OK. Username exists. Choose a different name:";
                }
            }
            this.tempUsername = theInput;
            state = REG_ENTER_PASSWORD;
            return "Checking name... OK. Enter a strong password:";
        }

        else if (state == REG_ENTER_PASSWORD)
        {
            if (checkPasswordStrong(theInput))
            {
                this.tempPassword = theInput;
                state = REG_ENTER_STATUS;
                return "Password accepted. What is your academic status? (student/teacher/other)";
            } else {
                return "Password weak (need 9 chars, 1 upper, 1 lower, 1 digit). Try again:";
            }
        }

        else if (state == REG_ENTER_STATUS)
        {
            if (isValidStatus(theInput))
            {
                this.tempStatus = theInput;
                state = REG_ENTER_YEARS;
                return "How many years have you been at Ruppin?";
            } else
            {
                return "Invalid choice. Please choose: student/teacher/other";
            }
        }

        else if (state == REG_ENTER_YEARS)
        {
            try
            {
                int years = Integer.parseInt(theInput);
                Client newClient = new Client(tempUsername, tempPassword, tempStatus, years);

                synchronized (clientState)
                {
                    clientState.add(newClient);
                    //גיבוי לקובץ CSV כל 3 משתמשים
                    if (clientState.size() % 3 == 0) createBackupFile();
                }

                state = WAITING;
                return "Registration complete.";

            } catch (NumberFormatException e) {
                return "Please enter a number only.";
            }
        }
        //      מסלול התחברות (Login & Update)
        else if (state == LOGIN_ENTER_USERNAME)
        {
            synchronized (clientState)
            {
                for (Client c : clientState)
                {
                    if (c.getUsername().equals(theInput))
                    {
                        this.currentClient = c;
                        break;
                    }
                }
            }
            if (this.currentClient != null)
            {
                state = LOGIN_ENTER_PASSWORD;
                return "Password:";
            } else
            {
                state = WAITING;
                return "User not found. Bye.";
            }
        }

        else if (state == LOGIN_ENTER_PASSWORD)
        {
            if (this.currentClient.getPassword().equals(theInput))
            {
                state = ASK_IF_UPDATE;
                return "Welcome back, " + currentClient.getUsername() + ".\n" +
                        "Last time you defined yourself as " + currentClient.getAcademicStatus() +
                        " for " + currentClient.getYearsAtRuppin() + " years.\n" +
                        "Do you want to update your information? (yes/no)";
            } else
            {
                state = WAITING;
                return "Wrong password. Bye.";
            }
        }

        else if (state == ASK_IF_UPDATE)
        {
            if (theInput.equalsIgnoreCase("yes"))
            {
                state = ASK_CHANGE_NAME;
                return "Do you want to change your username? (yes/no)";
            } else
            {
                state = WAITING;
                return "Bye.";
            }
        }

        else if (state == ASK_CHANGE_NAME)
        {
            if (theInput.equalsIgnoreCase("yes"))
            {
                state = UPDATE_NAME_INPUT;
                return "Enter new username:";
            } else
            {
                state = ASK_CHANGE_PASS; // מדלגים לשאלה הבאה
                return "Do you want to change your password? (yes/no)";
            }
        }

        else if (state == UPDATE_NAME_INPUT)
        {
            boolean exists = false;
            synchronized (clientState)
            {
                for (Client c : clientState)
                {
                    if (c.getUsername().equals(theInput) && !c.equals(currentClient))
                    {
                        exists = true;
                        break;
                    }
                }
            }
            if (exists)
            {
                return "Username exists. Choose different name:";
            } else
            {
                currentClient.setUsername(theInput);
                state = ASK_CHANGE_PASS;
                return "Username updated successfully.\nDo you want to change your password? (yes/no)";
            }
        }

        else if (state == ASK_CHANGE_PASS)
        {
            if (theInput.equalsIgnoreCase("yes"))
            {
                state = UPDATE_PASS_INPUT;
                return "Enter new password:";
            } else
            {
                state = ASK_CHANGE_YEARS;
                return "Do you want to update your years of study? (yes/no)";
            }
        }

        else if (state == UPDATE_PASS_INPUT)
        {
            if (checkPasswordStrong(theInput))
            {
                currentClient.setPassword(theInput);
                state = ASK_CHANGE_YEARS;
                return "Password updated successfully.\nDo you want to update your years of study? (yes/no)";
            } else
            {
                return "Password weak (min 9 chars, 1 upper, 1 lower, 1 digit). Try again:";
            }
        }

        else if (state == ASK_CHANGE_YEARS)
        {
            if (theInput.equalsIgnoreCase("yes"))
            {
                state = UPDATE_YEARS_INPUT;
                return "Enter number of years:";
            } else
            {
                state = WAITING;
                return "Thanks. Your information has been updated.";
            }
        }

        else if (state == UPDATE_YEARS_INPUT)
        {
            try
            {
                int newYears = Integer.parseInt(theInput);
                currentClient.setYearsAtRuppin(newYears);
                state = WAITING;
                return "Years of study updated successfully.\nThanks. Your information has been updated.";
            } catch (NumberFormatException e) {
                return "Please enter a valid number.";
            }
        }

        return "Error.";
    }

    private boolean checkPasswordStrong(String pass)
    {
        if (pass.length() < 9) return false;
        boolean upper = false, lower = false, digit = false;
        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) upper = true;
            if (Character.isLowerCase(c)) lower = true;
            if (Character.isDigit(c)) digit = true;
        }
        return upper && lower && digit;
    }

    private boolean isValidStatus(String s)
    {
        return s.equalsIgnoreCase("student") || s.equalsIgnoreCase("teacher") || s.equalsIgnoreCase("other");
    }

    private void createBackupFile()
    {
        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = "backup_" + dateStr + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName)))
        {
            writer.println("Username,Password,Status,Years");
            for (Client c : clientState)
            {
                writer.println(c.toString());
            }
            System.out.println("Backup created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}