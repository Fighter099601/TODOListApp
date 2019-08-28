package net.emrekalkan.todolistapp.utils;

public class Constants {

    public static class TodoStatus {
        public static final int TODO_ACTIVE = 0;
        public static final int TODO_COMPLETED = 1;
        public static final int TODO_EXPIRED = 2;
    }

    public static class LoginLayoutType {
        public static final int SIGN_IN = 0;
        public static final int SIGN_UP = 1;
    }

    public static class SharedPreferences {
        public static class User{
            public static String USER_PREFERENCES = "User";
            public static final String id = "ID";
            public static final String username = "USERNAME";
            public static final String password = "PASSWORD";
        }
    }

    public static class Arguments {
        public static final String LIST_ID = "listId";
    }

    public static class TodoItemViewType {
        public static final String FILTER_TYPE = "filterType";
        public static final int ALL = 0;
        public static final int COMPLETED = 1;
        public static final int ACTIVE = 2;
        public static final int EXPIRED = 3;

        public static final String ORDER_TYPE = "orderType";
        public static final int NAME = 4;
        public static final int DEADLINE = 5;
        public static final int STATUS = 6;
        public static final int CREATE_DATE = 7;
    }
}
