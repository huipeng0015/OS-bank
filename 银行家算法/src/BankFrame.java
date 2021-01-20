import javax.swing.*;
import java.awt.*;

public class BankFrame extends JFrame {

    public BankFrame() {
        this.setLayout(new BorderLayout(10, 5));
        MainShow mainShow = new MainShow();
        JScrollPane scroll = new JScrollPane(mainShow);
        this.add(scroll, BorderLayout.CENTER);
        this.setTitle("银行家算法");
        this.setResizable(true);
        this.setSize(800, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
