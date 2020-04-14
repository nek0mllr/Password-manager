import java.util.ArrayList;

public class PasswordManager<T> {
    private ArrayList<PasswordStorage> passwordList = new ArrayList<>();

    public boolean addPassword(PasswordStorage passwordStorage) {
        if (findEntry(passwordStorage.getPasswordUsage()) >= 0) {
            System.out.println("Eintrag ist bereits vorhanden!");
            return false;
        } else {
            passwordList.add(passwordStorage);
            return true;
        }

    }

    public void printList() {
        if (passwordList.size() == 0) {
            System.out.println("Keine Einträge vorhanden");
        } else {
            for (int i = 0; i < passwordList.size(); i++) {
                System.out.println((i + 1) + ". " + passwordList.get(i).getPasswordUsage() + ": " +
                        passwordList.get(i).getUsername() + ": " + changePassword(i));
            }
        }
    }

    private int findEntry(PasswordStorage passwordStorage) {
        return this.passwordList.indexOf(passwordStorage);
    }

    private int findEntry(String entryName) {
        for (int i = 0; i < this.passwordList.size(); i++) {
            PasswordStorage passwordStorage = this.passwordList.get(i);
            if (passwordStorage.getUsername().equals(entryName)) {
                return i;
            }
        }
        return -1;
    }

    private String changePassword(int i) {
        return passwordList.get(i).getPassword().replaceAll("[A-Za-z0-9]", "*");
    }

    public void removeEntry(String toRemove) {
        try {
            if (toRemove.equals(passwordList.get(findEntry(toRemove)).getUsername())) {
                passwordList.remove(toRemove);
                System.out.println(toRemove + " wurde entfernt");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Der Benutzer " + toRemove + " ist nicht vorhanden.");
        }
    }

    public void showPassword(String username) {
        try {
            if (username.equals(passwordList.get(findEntry(username)).getUsername())) {
                System.out.println("Das Passwort lautet: " + passwordList.get(findEntry(username)).getPassword());
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Der Benutzer " + username + " ist nicht vorhanden");
        }
    }
}

class PasswordStorage {
    private String passwordUsage;
    private String username;
    private String password;

    public PasswordStorage(String passwordUsage, String username, String password) {
        this.password = password;
        this.passwordUsage = passwordUsage;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordUsage() {
        return passwordUsage;
    }

    public String getUsername() {
        return username;
    }

    public static PasswordStorage createNewEntry(String passwordUsage, String username, String password) {
        return new PasswordStorage(passwordUsage, username, password);
    }
}

class PasswordManagerMain {

    private static PasswordManager<PasswordStorage> store = new PasswordManager<>();

    public static void main(String[] args) {
        boolean quit = false;
        printMenu();

        while (!quit) {
            System.out.println("Bitte Aktion auswählen: ");
            int action = new java.util.Scanner(System.in).nextInt();

            switch (action) {
                case 0:
                    printMenu();
                    break;
                case 1:
                    addEntry();
                    break;
                case 2:
                    store.printList();
                    break;
                case 3:
                    showPassword();
                    break;
                case 4:
                    removeEntry();
                    break;
                case 5:
                    quit = true;
                    break;
            }

        }

    }

    private static void addEntry() {
        System.out.println("Bitte Seitenname: ");
        String passwordUsage = new java.util.Scanner(System.in).nextLine();
        System.out.println("Benutzername angeben: ");
        String username = new java.util.Scanner(System.in).nextLine();
        System.out.println("Bitte Passwort eingeben: ");
        String password = new java.util.Scanner(System.in).nextLine();
        PasswordStorage newPassword = PasswordStorage.createNewEntry(passwordUsage, username, password);

        if (store.addPassword(newPassword)) {
            System.out.println("Eintrag wurde erfolgreich erschaffen");
        }
    }

    private static void showPassword() {
        System.out.println("Geben sie den Benutzernamen ein: ");
        String username = new java.util.Scanner(System.in).nextLine();

        store.showPassword(username);
    }

    private static void removeEntry() {
        System.out.println("Bitte geben Sie den zu löschenden Benutzernamen ein: ");
        String toRemove = new java.util.Scanner(System.in).nextLine();

        store.removeEntry(toRemove);
    }


    private static void printMenu() {
        System.out.println("0 - rufe Menü auf.\n" +
                "1 - Lege neuen Passwort Eintrag an\n" +
                "2 - Zeige alle Einträge an.\n" +
                "3 - Passwort anzeigen lassen.\n" +
                "4 - Entfernen eines Eintrags\n" +
                "5 - Beenden des Programms\n");
    }
}