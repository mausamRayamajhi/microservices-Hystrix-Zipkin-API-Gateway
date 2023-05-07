package com.mausam.user.util;

public class API {

    public static final String DEFAULT = "/user";

    /**
     * FORGET PASSWORD
     */
    public static final String EMAIL_VALIDATION = "/EmailValidation/{email}";
    public static final String VALIDATE_EMAIL_PARA = "/ChecksEmailParameters/{vCode}/{id}/{email}";
    public static final String FOR_LOGINED_USER = "/f/rest/AuthPassReset/{oldPassword}/{newPassword}/{id}";
    public static final String FOR_ALL = "/ResetPassword/{vCode}/{id}/{email}/{password}";

    /**
     * USER
     */
    public static final String USER = "/rest/getUser";
    public static final String UPDATE_USER = "/rest/userupdate";
    public static final String BY_ID_USER = "/{id}";
    public static final String VERIFY_USER = "/verifyUser/{id}";
    public static final String BY_ROLE_USER = "/rest/userByRole/{role}/{page}/{maxData}/{sort}";
    public static final String BY_STATUS_USER = "/rest/userByStatus/{status}/{page}/{maxData}/{sort}";
    public static final String ADD_USER_GENERAL = "/user";
    public static final String ADD_USER_BY_ADMIN = "/rest/user";
    public static final String BY_PAGE_USER = "/rest/users/{page}/{maxData}/{sort}";
    public static final String SEARCH_USER = "/rest/searchUser/{searchKeyWord}/{page}/{maxData}/{sort}";

    /**
     * TOKEN
     */
    public static final String TOKEN = "/token";


}
