package nl.krijnschelvis.place2beserver;

public class PostResponse {

    private static boolean success;
    private static String errorMessage = "Exception: No error message provided";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        PostResponse.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        PostResponse.errorMessage = "Exception: " + errorMessage;
    }
}
