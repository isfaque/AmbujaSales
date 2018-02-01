package com.lnsel.ambujaneotiasales.utils;

import android.content.Context;
import android.content.Intent;

import com.lnsel.ambujaneotiasales.views.Dashboard.MainActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.AroundMeScreen.AroundMeActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.AttendanceScreen.AttendanceActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ChangePasswordScreen.ChangePasswordActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactAddScreen.ContactAddActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactsScreen.ContactsActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomerAddScreen.CustomerAddActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomerEditScreen.CustomerEditActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomersScreen.CustomersActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpenseAddScreen.ExpenseAddActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpenseEditScreen.ExpenseEditActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpensesScreen.ExpensesActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestAddScreen.LeaveRequestAddActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestEditScreen.LeaveRequestEditActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingAddScreen.MeetingAddActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingExpenseScreen.MeetingExpenseActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingNearShopScreen.MeetingNearShopActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingOrderScreen.MeetingOrderActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingPictureScreen.MeetingPictureActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingSignatureScreen.MeetingSignatureActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingsScreen.MeetingsActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.OrderEditScreen.OrderEditActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.OrdersScreen.OrdersActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.PerformanceScreen.PerformanceActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.PerformanceSelectionScreen.PerformanceSelectionActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.TodosScreen.TodosActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactEditScreen.ContactEditActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomerDetailsScreen.CustomerDetailsActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestScreen.LeaveRequestActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingDetailsScreen.MeetingDetailsActivity;
import com.lnsel.ambujaneotiasales.views.LoginScreen.LoginActivity;

/**
 * Created by apps2 on 4/20/2017.
 */
public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startAttendanceActivity() {
        Intent intent = new Intent(context, AttendanceActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMeetingsActivity() {
        Intent intent = new Intent(context, MeetingsActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startTodosActivity() {
        Intent intent = new Intent(context, TodosActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startExpensesActivity() {
        Intent intent = new Intent(context, ExpensesActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startLeaveRequestActivity() {
        Intent intent = new Intent(context, LeaveRequestActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startCustomersActivity() {
        Intent intent = new Intent(context, CustomersActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startCustomerEditActivity() {
        Intent intent = new Intent(context, CustomerEditActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMeetingDetailsActivity() {
        Intent intent = new Intent(context, MeetingDetailsActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startExpenseAddActivity() {
        Intent intent = new Intent(context, ExpenseAddActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMeetingPictureActivity(){
        Intent intent = new Intent(context, MeetingPictureActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public void startMeetingSignatureActivity(){
        Intent intent = new Intent(context, MeetingSignatureActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public void startExpenseEditActivity() {
        Intent intent = new Intent(context, ExpenseEditActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMeetingAddActivity() {
        Intent intent = new Intent(context, MeetingAddActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startCustomerDetailsActivity() {
        Intent intent = new Intent(context, CustomerDetailsActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startCustomerAddActivity() {
        Intent intent = new Intent(context, CustomerAddActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startLeaveRequestAddActivity() {
        Intent intent = new Intent(context, LeaveRequestAddActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startAroundMeActivity() {
        Intent intent = new Intent(context, AroundMeActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startContactsActivity() {
        Intent intent = new Intent(context, ContactsActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startLeaveRequestEditActivity() {
        Intent intent = new Intent(context, LeaveRequestEditActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMeetingOrderActivity() {
        Intent intent = new Intent(context, MeetingOrderActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startOrdersActivity() {
        Intent intent = new Intent(context, OrdersActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startOrderEditActivity() {
        Intent intent = new Intent(context, OrderEditActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMeetingNearShopActivity() {
        Intent intent = new Intent(context, MeetingNearShopActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startPerformanceActivity() {
        Intent intent = new Intent(context, PerformanceActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startContactAddActivity() {
        Intent intent = new Intent(context, ContactAddActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startContactEditActivity() {
        Intent intent = new Intent(context, ContactEditActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startPerformanceSelectionActivity() {
        Intent intent = new Intent(context, PerformanceSelectionActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startMeetingExpenseActivity() {
        Intent intent = new Intent(context, MeetingExpenseActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void startChangePasswordActivity() {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
