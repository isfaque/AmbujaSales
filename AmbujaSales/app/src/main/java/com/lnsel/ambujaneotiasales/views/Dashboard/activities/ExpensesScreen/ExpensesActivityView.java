package com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpensesScreen;

/**
 * Created by apps2 on 5/3/2017.
 */
public interface ExpensesActivityView {
    void startMainActivity();
    void startExpenseAddActivity();

    void errorInfo(String msg);

    void errorExpenseDeleteInfo(String msg);
    void successExpenseDeleteInfo(String msg);

    void errorExpenseCompletedInfo(String msg);
    void successExpenseCompletedInfo(String msg);

    void startGetExpenses();
}
