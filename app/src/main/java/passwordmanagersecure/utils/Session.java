package passwordmanagersecure.utils;

public class Session {
    private static String currentUserId;

    public static void setCurrentUserId(String userId) {
        currentUserId = userId;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static boolean isLoggedIn() {
        return currentUserId != null;
    }

    public static void logout() {
        currentUserId = null;
    }
}
