package com.lnsel.ambujaneotiasales.utils;

/**
 * Created by apps2 on 5/5/2017.
 */
public class UrlUtil {

    //public static String MAIN_URL = "http://app.lnsel.in/ambujaneotiasales/";
    public static String MAIN_URL = "http://app.lnsel.in/ambujaneotiasalesdev/";
    //public static String MAIN_URL = "http://61.16.131.205/ambujasales/";
    public static String BASE_URL = MAIN_URL+"Api/";

    //for login
    public static String LOGIN_URL = BASE_URL+"user_login";

    //for logout
    public static String UPDATE_LOGOUT_RECORD = BASE_URL+"update_logout_record";

    //for get meeting list
    public static String GET_MEETINGS_URL = BASE_URL+"get_meetings";

    //for get meeting details
    public static String GET_MEETING_DETAILS_URL = BASE_URL+"get_meeting_details";

    //for update meeting visited
    public static String MEETING_VISITED_UPDATE_URL = BASE_URL+"update_meeting_visited";

    //for update meeting remarks
    public static String MEETING_REMARKS_UPDATE_URL = BASE_URL+"update_meeting_remarks";

    //for update meeting next visit
    public static String NEXT_MEETING_UPDATE_URL = BASE_URL+"update_next_visit";

    //for update meeting signature
    public static String MEETING_SIGNATURE_UPDATE_URL = BASE_URL+"update_meeting_signature";

    //for update meeting picture
    public static String MEETING_PICTURE_UPDATE_URL = BASE_URL+"update_meeting_picture";

    //for update meeting remarks
    public static String MEETING_COMPLETED_UPDATE_URL = BASE_URL+"update_meeting_completed";

    //for add expense
    public static String ADD_EXPENSE_URL = BASE_URL+"add_expense";

    //for get expenses list
    public static String GET_EXPENSES_URL = BASE_URL+"get_expenses";

    //for delete expense
    public static String DELETE_EXPENSE_URL = BASE_URL+"delete_expense";

    //for delete leave request
    public static String DELETE_LEAVE_REQUEST_URL = BASE_URL+"delete_leave_request";

    //for update expense
    public static String UPDATE_EXPENSE_URL = BASE_URL+"update_expense";

    //for add new customer meeting
    public static String ADD_NEW_CUSTOMER_MEETING = BASE_URL+"add_customer";
    public static String ADD_NEW_CUSTOMER = BASE_URL+"add_new_customer";
    public static String EDIT_NEW_CUSTOMER = BASE_URL+"edit_new_customer";

    //for get all customers
    public static String GET_ALL_CUSTOMERS = BASE_URL+"get_all_customers";
    public static String GET_CUSTOMER_MEETINGS = BASE_URL+"get_customer_meetings";
    public static String GET_CUSTOMER_COMPLETED_MEETINGS = BASE_URL+"get_customer_completed_meetings";

    //for add expense
    public static String ADD_CUSTOMER_MEETING_URL = BASE_URL+"add_customer_meeting";

    //for add leave request
    public static String ADD_LEAVE_REQUEST_URL = BASE_URL+"add_leave_request";

    //for update expense completed
    public static String EXPENSE_COMPLETED_UPDATE_URL = BASE_URL+"update_expense_completed";

    //for get leave request
    public static String GET_LEAVE_REQUEST_URL = BASE_URL+"get_leave_request";

    //for get attendance
    public static String GET_ATTENDANCE_URL = BASE_URL+"get_user_login_record";

    //for updated leave request
    public static String UPDATE_LEAVE_REQUEST_URL = BASE_URL+"update_leave_request";

    //for add meeting order
    public static String ADD_MEETING_ORDER_URL = BASE_URL+"add_order";

    //for update order
    public static String UPDATE_ORDER_URL = BASE_URL+"update_order";

    //for delete order
    public static String DELETE_ORDER_URL = BASE_URL+"delete_order";

    //for get orders
    public static String GET_ORDERS_URL = BASE_URL+"get_orders";

    public static String UPDATE_START_TRIP = BASE_URL+"update_start_trip_record";
    public static String UPDATE_STOP_TRIP = BASE_URL+"update_stop_trip_record";

    //for add contact
    public static String ADD_CONTACT_URL = BASE_URL+"add_contact";

    //for get contacts
    public static String GET_CONTACTS_URL = BASE_URL+"get_contacts";

    //for delete contact
    public static String DELETE_CONTACT_URL = BASE_URL+"delete_contact";

    //for update contact
    public static String UPDATE_CONTACT_URL = BASE_URL+"update_contact";

    //for update password
    public static String UPDATE_PASSWORD_URL = BASE_URL+"update_user_password";

    //for get performance
    public static String GET_PERFORMANCE_URL = BASE_URL+"get_visits_and_orders_analysis_by_user";
    public static String GET_PERFORMANCE_LIST_URL = BASE_URL+"get_visit_order_list_analysis_by_user";

    public static String LOCATION_UPDATE_URL = BASE_URL+"update_current_location";

    public static String GET_COUNTRIES = MAIN_URL+"customermanagement/get_all_countries";
    public static String GET_STATES_BY_ID = MAIN_URL+"customermanagement/get_states_by_id";
    public static String GET_CITIES_BY_ID = MAIN_URL+"customermanagement/get_cities_by_id";
    public static String GET_CUSTOMER_TYPES = MAIN_URL+"customermanagement/get_all_customer_types";
    public static String GET_INDUSTRY_TYPES = MAIN_URL+"customermanagement/get_all_industry_types";
    public static String GET_GENDERS = MAIN_URL+"customermanagement/get_all_genders";
    public static String GET_COMPANIES = MAIN_URL+"customermanagement/get_all_companies";
    public static String GET_DEPARTMENTS = MAIN_URL+"customermanagement/get_all_departments";
    public static String GET_DESIGNATIONS = MAIN_URL+"customermanagement/get_all_designations";
    public static String GET_AREAS = MAIN_URL+"customermanagement/get_all_areas";
    public static String GET_MEETING_TYPES = MAIN_URL+"meetingmanagement/get_all_meeting_types";
    public static String GET_ORDER_STATUS_TYPES = MAIN_URL+"ordermanagement/get_all_order_status_types";


    //for get user units
    public static String GET_USER_UNITS_URL = BASE_URL+"get_user_units";

    //for get venues by unit
    public static String GET_VENUES_BY_UNIT_URL = BASE_URL+"get_venues_by_unit";


    //for firebase token update
    public static String TOKEN_UPDATE_URL = BASE_URL+"update_user_push_token";
}
