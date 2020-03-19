package Managers;

import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.licentav00.CheckersViews.CheckerTextView;
import com.example.licentav00.R;

import org.w3c.dom.Text;

import Utils.GlobalMainContext;
import Utils.MConstants;

public class CheckerViewManager {
    //region Private Members
    private View mView;
    private SharedPreferences mSharedPreferences;
    private String mAppLanguage;
    //endregion


    //region Constructor
    public CheckerViewManager(View view) {
        this.mView = view;
        this.mSharedPreferences = GlobalMainContext.getMainContext().getSharedPreferences("AppLanguage",GlobalMainContext.getMainContext().MODE_PRIVATE);
        this.mAppLanguage = mSharedPreferences.getString("Language",MConstants.AppLanguages.RO_LANG);
    }
    //endregion


    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    /**
     * Function that creates view when the checker stops
     * @param checkDone
     * @param checkStatus
     */
    public void CreateView(String checkDone, String checkStatus) {
        ImageView imageView = null;
        TextView textView = null;
        switch (checkDone) {
            case MConstants.SIGNAL_CHECKER:
                imageView = this.mView.findViewById(R.id.imageSignal);
                selectImage(imageView,checkStatus);
                imageView.setVisibility(View.VISIBLE);
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                imageView = this.mView.findViewById(R.id.imagePBDB);
                selectImage(imageView,checkStatus);
                imageView.setVisibility(View.VISIBLE);
                break;
            case MConstants.INTERNAL_DB_CHECKER:
                imageView = this.mView.findViewById(R.id.imageINTDB);
                selectImage(imageView,checkStatus);
                imageView.setVisibility(View.VISIBLE);
                break;
            case MConstants.NEIGHBOUR_LIST_CHECKER:
                imageView = this.mView.findViewById(R.id.imageNeigh);
                selectImage(imageView,checkStatus);
                imageView.setVisibility(View.VISIBLE);
                break;
            case MConstants.CELL_CONSISTENCY_CHECKER:
                imageView = this.mView.findViewById(R.id.imageCell);
                selectImage(imageView,checkStatus);
                imageView.setVisibility(View.VISIBLE);
                break;
            case MConstants.OVERALL_CHECKER:
                textView = this.mView.findViewById(R.id.finalResult);
                selectTextView(textView,checkStatus);
                textView.setVisibility(View.VISIBLE);
                break;
            case MConstants.CONNECTIVITY_CHECKER:
                imageView = this.mView.findViewById(R.id.conectivityImageView);
                this.selectImage(imageView,checkStatus);
                imageView.setVisibility(View.VISIBLE);
                break;
        }


    }
    //endregion

    //Private Methods
    private void selectImage(ImageView resultImage,String checkStatus) {
        switch (checkStatus) {
            case MConstants.TEST_PASSED_RO:
                resultImage.setImageResource(R.drawable.check);
                break;
            case MConstants.TEST_FAILED_RO:
                resultImage.setImageResource(R.drawable.x);
                break;
        }
    }

    private void selectTextView(TextView resultText,String checkStatus) {
        if(this.mAppLanguage.equals(MConstants.AppLanguages.EN_LANG)) {
            switch (checkStatus) {
                case MConstants.OVERALL_FAILED_RO:
                    checkStatus = MConstants.OVERALL_FAILED_EN;
                    resultText.setTextColor(GlobalMainContext.getMainContext().getResources().getColor(R.color.red));
                    break;
                case MConstants.OVERALL_PASSED_RO:
                    checkStatus = MConstants.OVERALL_PASSED_EN;
                    resultText.setTextColor(GlobalMainContext.getMainContext().getResources().getColor(R.color.green));
                    break;
            }
        } else {
            switch (checkStatus) {
                case MConstants.OVERALL_FAILED_RO:
                    resultText.setTextColor(GlobalMainContext.getMainContext().getResources().getColor(R.color.red));
                    break;
                case MConstants.OVERALL_PASSED_RO:
                    resultText.setTextColor(GlobalMainContext.getMainContext().getResources().getColor(R.color.green));
                    break;
            }
        }
        resultText.setText(checkStatus);
    }
    //endregion
}
