import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class ATM {
    private double balance = 1000.0;
    private ArrayList<String> history = new ArrayList<>();
    private JFrame frame;
    private String password = "0000";

    public ATM() {
        frame = new JFrame("ATM Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Full screen mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        JLabel titleLabel = new JLabel("ATM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(102, 102, 102));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JButton balanceButton = new JButton("Balance Inquiry");
        JButton historyButton = new JButton("Transaction History");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton resetPasswordButton = new JButton("Reset Password");
        JButton quitButton = new JButton("Quit");

        Dimension buttonSize = new Dimension(30, 10); // Reduce size to 75% of default

        balanceButton.setPreferredSize(buttonSize);
        historyButton.setPreferredSize(buttonSize);
        withdrawButton.setPreferredSize(buttonSize);
        depositButton.setPreferredSize(buttonSize);
        transferButton.setPreferredSize(buttonSize);
        resetPasswordButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);

        buttonPanel.add(balanceButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(resetPasswordButton);
        buttonPanel.add(quitButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        balanceButton.addActionListener(e -> balanceInquiry());
        historyButton.addActionListener(e -> checkHistory());
        withdrawButton.addActionListener(e -> withdraw());
        depositButton.addActionListener(e -> deposit());
        transferButton.addActionListener(e -> transfer());
        resetPasswordButton.addActionListener(e -> resetPassword());
        quitButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    private boolean authenticate() {
        String input = JOptionPane.showInputDialog(frame, "Enter your password:");
        return input != null && input.equals(password);
    }

    private void balanceInquiry() {
        if (authenticate()) {
            showMessage("Your current balance is: $" + balance, "Balance Inquiry");
        } else {
            showMessage("Incorrect password", "Error");
        }
    }

    private void checkHistory() {
        if (authenticate()) {
            StringBuilder historyStr = new StringBuilder();
            for (String entry : history) {
                historyStr.append(entry).append("\n");
            }
            showMessage(historyStr.length() > 0 ? historyStr.toString() : "No transactions yet", "Transaction History");
        } else {
            showMessage("Incorrect password", "Error");
        }
    }

    private void withdraw() {
        if (authenticate()) {
            double amount = getAmount("Withdraw");
            if (amount > 0) {
                if (amount > balance) {
                    showMessage("Insufficient funds", "Error");
                } else {
                    balance -= amount;
                    history.add("Withdraw: $" + amount);
                    showMessage("$" + amount + " withdrawn", "Success");
                }
            }
        } else {
            showMessage("Incorrect password", "Error");
        }
    }

    private void deposit() {
        if (authenticate()) {
            double amount = getAmount("Deposit");
            if (amount > 0) {
                balance += amount;
                history.add("Deposit: $" + amount);
                showMessage("$" + amount + " deposited", "Success");
            }
        } else {
            showMessage("Incorrect password", "Error");
        }
    }

    private void transfer() {
        if (authenticate()) {
            double amount = getAmount("Transfer");
            if (amount > 0) {
                if (amount > balance) {
                    showMessage("Insufficient funds", "Error");
                } else {
                    String accountNumber = JOptionPane.showInputDialog(frame, "Enter account number:");
                    String ifscCode = JOptionPane.showInputDialog(frame, "Enter IFSC code:");
                    if (accountNumber != null && ifscCode != null) {
                        balance -= amount;
                        history.add("Transfer: $" + amount + " to Account: " + accountNumber + " IFSC: " + ifscCode);
                        showMessage("$" + amount + " transferred", "Success");
                    }
                }
            }
        } else {
            showMessage("Incorrect password", "Error");
        }
    }

    private void resetPassword() {
        if (authenticate()) {
            String newPassword = JOptionPane.showInputDialog(frame, "Enter new 4-digit password:");
            if (newPassword != null && newPassword.matches("\\d{4}")) {
                password = newPassword;
                showMessage("Password reset successfully", "Success");
            } else {
                showMessage("Invalid password. It must be 4 digits.", "Error");
            }
        } else {
            showMessage("Incorrect password", "Error");
        }
    }

    private void showMessage(String message, String title) {
        JFrame messageFrame = new JFrame(title);
        messageFrame.setSize(400, 300);
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        messageFrame.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("<html><body style='text-align: center;'>" + message.replace("\n", "<br>") + "</body></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageFrame.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> messageFrame.dispose());
        messageFrame.add(okButton, BorderLayout.SOUTH);

        messageFrame.setVisible(true);
    }

    private double getAmount(String action) {
        String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to " + action.toLowerCase() + ":");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) throw new NumberFormatException();
                return amount;
            } catch (NumberFormatException e) {
                showMessage("Invalid amount", "Error");
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATM::new);
    }
}

