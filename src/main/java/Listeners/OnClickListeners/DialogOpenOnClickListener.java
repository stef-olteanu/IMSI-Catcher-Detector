package Listeners.OnClickListeners;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.licentav00.Popups.CheckerPopUpDialog;

/**
 * This is the advice dialog that appears when you click on the advice on the checker fragment.
 */
public class DialogOpenOnClickListener implements View.OnClickListener {
    //region Private Members
    private Fragment mFragmentContext;
    //endregion

    //region Constructor
    public DialogOpenOnClickListener(Fragment fragmentContext) {
        this.mFragmentContext = fragmentContext;
    }
    //endregion


    @Override
    public void onClick(View v) {
        CheckerPopUpDialog checkerPopUpDialog = new CheckerPopUpDialog();
        checkerPopUpDialog.show(this.mFragmentContext.getFragmentManager(),"checkerpopup");
    }
}
