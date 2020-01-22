package CallBacks;

public interface CheckerCallBack {
    void OnCheckStarted(String checkDone);
    void OnCheckCompleted(String checkDone, String checkStatus);
}
