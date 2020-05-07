package CallBacks;

import Responses.CheckerResponse;

public interface ConnectivityCheckCallBack {
    void onCheckCompleted(CheckerResponse checkerResponse);
}
