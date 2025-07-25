package owl.humanresourcesmanagement.constant;

public class EndPoints {
	public static final String VERSION = "/v1";
	public static final String API = "/api";
	public static final String DEV = "/dev";
	public static final String AUTH = "/auth";
	public static final String ROOT = VERSION + DEV;
	
	// Classes - Controller
	public static final String ADMIN = API + "/admin";
	public static final String COMPANY = API + "/company";
	public static final String EMPLOYEE = API + "/employee";
	public static final String USER = API + "/users";
	public static final String PUBLIC_API = API + "/public-api";
	public static final String AUTHENTICATION = API + AUTH;
	
	// Admin
	public static final String ADMIN_DASHBOARD = "/dashboard"; // api/admin/dashboard
	public static final String ADMIN_LOGIN = "/admin-login"; // api/admin/admin-login
	public static final String PENDING_COMPANY = "/pending-company"; // api/admin/pending-company
	public static final String ACCEPTED_COMPANY = "/accepted-company"; // api/admin/accepted-company
	public static final String DENIED_COMPANY = "/denied-company"; // api/admin/denied-company
	public static final String DELETED_COMPANY = "/deleted-company"; // api/admin/deleted-company
	public static final String CHANGE_COMPANY_STATUS = "/change-company-status"; // api/admin/change-company-status
	public static final String ADMIN_DEACTIVATE = "/admin-deactivate"; // api/admin/deactivate
	public static final String FIND_ALL_COMPANY = "/find-all-company"; // api/admin/find-all-company
	public static final String IS_ACCEPTED_COMPANY = "/is-accepted-company"; // api/admin/is-accepted-company
	
	// Auth
	public static final String REGISTER = "/register"; // api/auth/register
	public static final String LOGIN = "/login"; // api/auth/login
	public static final String VERIFY_MAIL = "/verify-mail"; // api/auth/verify-mail
	public static final String FORGOT_PASSWORD = "/forgot-password"; // api/auth/forgot-password
	public static final String RESET_PASSWORD = "/reset-password"; // api/auth/reset-password
	public static final String LOGOUT = "/logout"; // api/auth/logout
	public static final String REFRESH_ACCESS_TOKEN = "/refresh_access_token"; // api/auth/refresh_authentication_token
	
	// Public Api
	public static final String HOMEPAGE_CONTENT = "/homepage-content"; // api/public-api/homepage-content
	public static final String PLATFORM_FEATURES = "/platform-features"; // api/public-api/platform-features
	public static final String HOW_IT_WORKS = "/how-it-works"; // api/public-api/how-it-works
	public static final String CURRENT_YEAR_HOLIDAYS = "/current-year-holidays"; // api/public-api/current-year-holidays
	
	// Company
	public static final String COMPANY_DASHBOARD = "/dashboard"; // api/company/dashboard
	public static final String PERSONALS = "/personals"; // GET api/company/personals
	public static final String PERSONAL_DETAILS = "/personal-details/{id}"; // GET api/company/personals-details/{id}
	public static final String ADD_PERSONAL = "/add-personal"; // POST api/company/add-personal
	public static final String UPDATE_PERSONAL = "/update-personal/{id}"; // PUT api/company/update-personal/{id}
	public static final String DELETED_PERSONAL = "/deleted-personal"; // GET /api/company/deleted-personal
	public static final String ACTIVE_PERSONAL = "/active-personal"; // GET /api/company/active-personal
	public static final String PASSIVE_PERSONAL = "/inactive-personal"; // GET /api/company/inactive-personal
	public static final String PENDING_PERSONAL = "/pending-personal"; // GET /api/company/pending-personal
	public static final String MAKE_DELETED_PERSONAL = "/employee/{id}/deleted"; // DELETE /api/company/employee/{id}/deleted
	public static final String MAKE_PERSONAL_STATUS_IN_ACTIVE = "/employee/{id}/active"; // PUT /api/company/employee/{id}/active
	public static final String MAKE_PERSONAL_STATUS_IN_PASSIVE = "/employee/{id}/passive"; // PUT /api/company/employee/{id}/passive
	public static final String CHANGE_PERSONAL_STATUS = "/employee/{id}/change-personal-status"; // PUT /api/company/employee/{id}/change-personal-status
	
	// User
	public static final String USER_PROFILE = USER + "/profile"; // GET /api/users/profile
	public static final String USER_UPDATE_PROFILE = USER + "/update-profile"; // PUT /api/users/update-profile
	public static final String USER_CHANGE_EMAIL = USER + "/change-email"; // PUT /api/users/change-email
	public static final String USER_VERIFY_EMAIL = USER + "/verify-email"; // PUT /api/users/change-email
	public static final String USER_CHANGE_PASSWORD = USER + "/change-password"; // PUT /api/users/change-password
	public static final String USER_DEACTIVATE = USER + "/deactivate"; // PUT /api/users/deactivate
	
	public static final String EMPLOYEE_DASHBOARD = EMPLOYEE + "/dashboard"; // api/employee/dashboard
	public static final String UPDATE_PERSONAL_PROFILE = EMPLOYEE + "/update"; // PUT api/company/update
	
	// Permission
	public static final String REQUEST_LEAVE = COMPANY +  "/request-leave"; // GET /api/company/request-leave
	public static final String APPROVED_LEAVE = COMPANY + "/approved-leave"; // GET /api/company/request-leave
	public static final String IS_APPROVED_REQUEST_LEAVE = COMPANY + "/leave/{id}/approved-request-leave"; // PUT /api/company/leave/{id}/approve
	
	public static final String LEAVES = EMPLOYEE + "/all-leaves"; //GET /api/employee/all-leaves
	public static final String CREATE_LEAVE = EMPLOYEE + "/create-leave"; // POST /api/employee/leaves
	public static final String LEAVES_DETAIL = EMPLOYEE + "/leaves-detail/{id}"; //GET /api/employee/leaves/{id}
	public static final String LEAVES_BALANCE = EMPLOYEE + "/leaves-balance"; //GET /api/employee/leaves/balance
	public static final String ANNUAL_LEAVE_DETAILS = EMPLOYEE + "/annual-leave-details"; // GET /api/employee/annual-leave-details
	
	// Expenses - EmployeeInformation
	public static final String EXPENSES = "/expenses"; // GET /api/employee/expenses
	public static final String EXPENSE_DETAIL = "/expenses/{id}"; // GET /api/employee/expenses/{id}
	public static final String CREATE_EXPENSE = "/expenses"; // POST /api/employee/expenses
	public static final String UPDATE_EXPENSE = "/expenses/{id}"; // PUT /api/employee/expenses/{id}
	public static final String DELETE_EXPENSE = "/expenses/{id}"; // DELETE /api/employee/expenses/{id} -(PENDING)
	public static final String EXPENSES_MONTHLY_SUMMARY = "/expenses/monthly-summary"; // GET /api/employee/expenses/monthly-summary
	
	// Expenses - Manager
	public static final String PENDING_EXPENSES_MANAGER = "/expenses/pending"; // GET /api/company/expenses/pending
	public static final String APPROVED_EXPENSES_MANAGER = "/expenses/approved"; // GET /api/company/expenses/approved
	public static final String REJECTED_EXPENSES_MANAGER = "/expenses/rejected"; // GET /api/company/expenses/rejected
	public static final String EXPENSES_LIST_MANAGER = "/expenses"; // GET /api/company/expenses
	public static final String EXPENSE_DETAILS_MANAGER = "/expenses/{id}"; // GET /api/company/expenses/{id}
	public static final String APPROVE_REJECT_EXPENSE = "/expenses/approve-reject"; // PUT /api/company/expenses/approve-reject
	public static final String EXPENSES_USER_MONTHLY_SUMMARY = "/expenses/{userId}/monthly-summary";  // GET /api/company/expenses/{userId}/monthly-summary
	
	// Embezzlement
	public static final String EMBEZZLEMENT_LIST = COMPANY + "/embezzlement-list";  // GET /api/company/assets
	public static final String EMBEZZLEMENT_DETAILS = COMPANY + "/embezzlement-details/{embezzlementId}";  // GET /api/company/assets
	public static final String ASSIGNED_EMBEZZLEMENT_LIST = COMPANY + "/assigned-embezzlement-list";  // GET /api/company/assets
	public static final String REJECTED_EMBEZZLEMENT_LIST = COMPANY + "/rejected-embezzlement-list";  // GET /api/company/assets
	public static final String UNASSIGNED_EMBEZZLEMENT_LIST = COMPANY + "/unassigned-embezzlement-list";  // GET /api/company/assets
	public static final String UPDATE_EMBEZZLEMENT = COMPANY + "/employee/update-embezzlement/{embezzlementId}";
	public static final String DELETE_EMBEZZLEMENT = COMPANY + "/employee/delete-embezzlement";
	public static final String CREATE_EMBEZZLEMENT = COMPANY + "/create-embezzlement";  //POST /api/company/assets
	public static final String ASSIGN_EMBEZZLEMENT = COMPANY + "/employee/assign-embezzlement"; // manager send -> personal will approve/reject
	public static final String MANAGER_CONFIRM_REJECT = COMPANY + "/manager-confirm-reject"; // manager will approve or reject
	
	public static final String MY_EMBEZZLEMENT_LIST = EMPLOYEE + "/my-embezzlement-list";
	public static final String CONFIRM_REJECT = EMPLOYEE + "/confirm-reject"; // personal will approve or reject
	public static final String REQUEST_EMBEZZLEMENT = EMPLOYEE + "/employee/request-embezzlement"; // personal request -> manager will approve/reject
	
	// Comment
	public static final String SAVE_COMMENT = COMPANY + "/save-comment"; // POST /api/company/save-comment
	public static final String UPDATE_COMMENT = COMPANY + "/update-comment"; // PUT /api/company/update-comment
	public static final String DELETE_COMMENT = COMPANY + "/delete-comment/{id}"; // DELETE /api/company/delete-comment/{id}
	
	public static final String PUBLIC_COMMENTS = PUBLIC_API + "/public-comments"; // GET /api/public-api/public-comments
	
	public static final String COMMENT_APPROVE_OR_REJECT = ADMIN + "/comment/{id}/approve-or-reject"; // PUT /api/admin/comment/{id}/approve-or-reject
	public static final String ADMIN_PENDING_COMMENTS = ADMIN + "/pending-comments"; // GET /api/admin/pending-comments
	public static final String ADMIN_ALL_COMMENTS = ADMIN + "/all-comments"; // GET /api/admin/all-comments
	
	// Shift
	public static final String CREATE_SHIFT = COMPANY + "/create-shift"; // POST /api/company/create-shift
	public static final String ALL_SHIFTS = COMPANY + "/all-shifts"; // GET /api/company/all-shifts
	public static final String SHIFT_DETAILS = COMPANY +"/{shiftId}/shift-details"; // GET /api/company/{shiftId}/shift-details
	public static final String UPDATE_SHIFT = COMPANY + "/{shiftId}/update-shift"; // PUT /api/company/{shiftId}/update-shift
	public static final String DELETE_SHIFT = COMPANY + "/{shiftId}/delete-shift"; // DELETE /api/company/{shiftId}/delete-shift
	public static final String ASSIGN_SHIFT = COMPANY + "/employee/{userId}/assign-shift"; // POST /api/company/employee/{userId}/assign-shift
	public static final String ASSIGN_DELETE_SHIFT = COMPANY + "/employee/{userId}/assign-delete-shift/{shiftId}"; // DELETE /api/company/employee/{userId}/assign-delete-shift/{shiftId}
	public static final String PERSONAL_SHIFT = COMPANY + "/employee/{userId}/personal-shift"; // GET /api/company/employee/{userId}/personal-shift
	public static final String ALL_ASSIGNED_SHIFTS = COMPANY + "/all-assigned-shifts"; // GET /api/company/all-assigned-shifts
	public static final String MY_SHIFT =  EMPLOYEE + "/my-shift"; // GET /api/employee/my-shift
	
	// Break (?)
	public static final String ALL_BREAKS = COMPANY + "/all-breaks"; // GET /api/company/all-breaks
	public static final String BREAK_DETAILS = COMPANY + "/{breakId}/break-details"; // GET /api/company/{breakId}/break-details
	public static final String CREATE_BREAK = COMPANY + "/create-break"; // POST /api/company/create-break
	public static final String UPDATE_BREAK = COMPANY + "/{breakId}/update-break"; // PUT /api/company/{breakId}/update-break
	public static final String DELETE_BREAK = COMPANY + "/{breakId}/delete-break"; // DELETE /api/company/{breakId}/delete-break
	
}
