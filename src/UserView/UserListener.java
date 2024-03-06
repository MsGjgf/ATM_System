package UserView;

import LoginView.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserListener implements ActionListener {
    private UserView userView;

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==userView.cunkuanBtn){
            new CunkuanView(userView);
        } else if (e.getSource()==userView.qukuanBtn) {
            new QukuanView(userView);
        } else if (e.getSource()==userView.zhuanzhangBtn) {
            new ZhuanzhangView(userView);
        } else if (e.getSource()==userView.balanceBtn) {
            new SelBalanceView(userView);
        } else if (e.getSource()==userView.tradeBtn) {
            new SelTradeView(userView);
        } else if (e.getSource()==userView.exitBtn) {
            new LoginView();
            userView.dispose();
        }
    }
}
