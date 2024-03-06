package AdminView.Deposit;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;

public class AddDepositView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel addDepositLabel = new JLabel("存款类型：",JLabel.RIGHT);
    JTextField addDeposit = new JTextField(10);
    JButton addDepositBtn = new JButton("添加");
    AddDepositListener addDepositListener = new AddDepositListener();
    public AddDepositView(AdminView adminView){
        super(adminView,"添加存款类型界面",true);

        Container contentPane = getContentPane();
        jPanel.add(addDepositLabel);
        jPanel.add(addDeposit);
        jPanel.add(addDepositBtn);
        contentPane.add(jPanel);

        //监听器
        addDepositListener = new AddDepositListener();
        addDepositListener.setAddDepositView(this);
        addDepositBtn.addActionListener(addDepositListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

