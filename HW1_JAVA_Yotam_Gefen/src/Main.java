import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Message> messages = new ArrayList<>();


        try {
            messages.add(new PromotionMessage("Alo Yoga", "50% Discount!", false, 50, new Date(System.currentTimeMillis() + 86400000)));
            messages.add(new PromotionMessage("Adidas", "Black Friday Deal", false, 20, new Date(System.currentTimeMillis() + 172800000)));
        } catch (PromotionException e) {
            System.out.println(e.getMessage());
        }

        messages.add(new BoardMessage("Admin", "Meeting today", new Date(), false, PriorityType.IMPORTANT, 10));
        messages.add(new BoardMessage("Manager", "New rules apply", new Date(), false, PriorityType.REGULAR, 5));

        messages.add(new EmailMessage("Support", "Welcome!", false, "Welcome message"));
        messages.add(new EmailMessage("CSDept", "Exam details attached", false, "Exam Notice"));

        int choice;

        do {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Add a message");
            System.out.println("2. Delete a message");
            System.out.println("3. Print all messages");
            System.out.println("4. Count messages containing a word");
            System.out.println("5. Print digital messages only");
            System.out.println("6. Show previews of all messages");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {


                case 1:
                    addMessage(messages, sc);
                    break;


                case 2:
                    if (messages.isEmpty()) {
                        System.out.println("No messages to delete.");
                        break;
                    }
                    System.out.print("Enter index to delete (0 - " + (messages.size() - 1) + "): ");
                    int index = sc.nextInt();
                    sc.nextLine();

                    if (index < 0 || index >= messages.size()) {
                        System.out.println("Invalid index.");
                    } else {
                        messages.remove(index);
                        System.out.println("Message deleted.");
                    }
                    break;


                case 3:
                    if (messages.isEmpty()) {
                        System.out.println("No messages available.");
                        break;
                    }
                    for (int i = 0; i < messages.size(); i++) {
                        System.out.println(i + ": " + messages.get(i));
                    }
                    break;


                case 4:
                    System.out.print("Enter a word to search: ");
                    String word = sc.nextLine();

                    int count = 0;
                    for (Message m : messages) {
                        if (m.getContent().contains(word))
                            count++;
                    }
                    System.out.println("Number of messages containing '" + word + "': " + count);
                    break;


                case 5:
                    System.out.println("Digital messages:");
                    for (Message m : messages) {
                        if (m instanceof IDigital)
                            System.out.println(m);
                    }
                    break;


                case 6:
                    System.out.println("\nMessage Previews:");
                    for (Message m : messages) {
                        System.out.println(m.generatePreview());
                    }
                    break;

                case 7:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 7);

        sc.close();
    }


    public static void addMessage(ArrayList<Message> messages, Scanner sc) {

        System.out.println("\nChoose message type:");
        System.out.println("1. EmailMessage");
        System.out.println("2. BoardMessage");
        System.out.println("3. PromotionMessage");

        int type = sc.nextInt();
        sc.nextLine();

        try {

            switch (type) {


                case 1:
                    System.out.print("Sender: ");
                    String sender = sc.nextLine();

                    System.out.print("Content: ");
                    String content = sc.nextLine();

                    System.out.print("Subject: ");
                    String subject = sc.nextLine();

                    EmailMessage email = new EmailMessage(sender, content, false, subject);
                    messages.add(email);
                    System.out.println("EmailMessage added.");
                    break;


                case 2:
                    System.out.print("Sender: ");
                    sender = sc.nextLine();

                    System.out.print("Content: ");
                    content = sc.nextLine();

                    BoardMessage board = new BoardMessage(sender, content, false, 0);
                    messages.add(board);

                    System.out.println("BoardMessage added.");
                    break;


                case 3:
                    System.out.print("Sender: ");
                    sender = sc.nextLine();

                    System.out.print("Content: ");
                    content = sc.nextLine();

                    System.out.print("Discount (0-100): ");
                    int discount = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Expiry in days from now: ");
                    int days = sc.nextInt();
                    sc.nextLine();

                    Date expiry = new Date(System.currentTimeMillis() + (long)days * 24 * 60 * 60 * 1000);

                    PromotionMessage promo = new PromotionMessage(sender, content, false, discount, expiry);

                    messages.add(promo);
                    System.out.println("PromotionMessage added.");
                    break;

                default:
                    System.out.println("Invalid message type.");
            }

        } catch (Exception e) {
            System.out.println("Error creating message: " + e.getMessage());
        }

    }

}
