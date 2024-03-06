package AdminView;

import AdminView.CardInfo.*;
import AdminView.Deposit.*;
import AdminView.UserInfo.*;
import LoginView.LoginView;
import AdminView.TradeInfo.*;

import java.awt.event.*;
import java.sql.SQLException;

public class AdminListener implements ActionListener {
    AdminView adminView;
    public void setView(AdminView adminView){
        this.adminView = adminView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==adminView.addUserBtn){
            new AddUserView(adminView);
        } else if (e.getSource()==adminView.updUserBtn) {
            new UpdUserView(adminView);
        } else if (e.getSource()==adminView.delUserBtn) {
            new DelUserView(adminView);
        } else if (e.getSource()==adminView.selUserBtn) {
            new SelUserView(adminView);
        } else if (e.getSource()==adminView.addCardBtn) {
            try {
                new AddCardView(adminView);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==adminView.updCardBtn) {
            new UpdCardView(adminView);
        } else if (e.getSource()==adminView.delCardBtn) {
            new DelCardView(adminView);
        } else if (e.getSource()==adminView.selCardBtn) {
            new SelCardView(adminView);
        } else if (e.getSource()==adminView.exitBtn) {
            adminView.dispose();
            new LoginView();
        } else if (e.getSource()==adminView.selTradeBtn) {
            new TradeView(adminView);
        } else if (e.getSource()==adminView.addDepositBtn) {
            new AddDepositView(adminView);
        } else if (e.getSource()==adminView.updDepositBtn) {
            new UpdDepositView(adminView);
        } else if (e.getSource()==adminView.delDepositBtn) {
            new DelDepositView(adminView);
        }
    }
}
