package com.example.licentav00.CheckersViews;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import com.example.licentav00.R;

import Utils.GlobalMainContext;
import Utils.MConstants;

public class CheckerTextView extends AppCompatTextView {
    //region Private Methods
    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSet;
    private SharedPreferences mSharedPreferences;
    private String mAppLanguage;
    //endregion

    //region Constructor
    public CheckerTextView(Context context, View view) {
        super(context);
        this.mConstraintLayout = view.findViewById(R.id.checkerConstraintLayout);
        this.mConstraintSet = new ConstraintSet();
        this.mSharedPreferences = context.getSharedPreferences("AppLanguage",context.MODE_PRIVATE);
        this.mAppLanguage = mSharedPreferences.getString("Language",MConstants.AppLanguages.RO_LANG);
    }
    //endregion


    //region Public Methods
    /**
     * Function that shows the TextView related to what check is done
     * @param checkDone Specifies the check done - used to know what TextView to show
     */
    public void SetParamsTextView(String checkDone) {
        switch (checkDone) {
            case MConstants.SIGNAL_CHECKER:
                if(this.mAppLanguage.equals(MConstants.AppLanguages.RO_LANG))
                    this.setText(MConstants.SIGNAL_CHECKING_TEXT_RO);
                if(this.mAppLanguage.equals(MConstants.AppLanguages.EN_LANG))
                    this.setText(MConstants.SIGNAL_CHECKING_TEXT_EN);
                String checkStatus = MConstants.STRING_EMPTY;
                setViewParameters(checkDone,checkStatus);
                setViewConstraints(checkDone);
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                if(this.mAppLanguage.equals(MConstants.AppLanguages.RO_LANG))
                    this.setText(MConstants.PUBLIC_DB_CHECKING_TEXT_RO);
                if(this.mAppLanguage.equals(MConstants.AppLanguages.EN_LANG))
                    this.setText(MConstants.PUBLIC_DB_CHECKING_TEXT_EN);
                checkStatus = MConstants.STRING_EMPTY;
                setViewParameters(checkDone,checkStatus);
                setViewConstraints(checkDone);
                break;
            case MConstants.INTERNAL_DB_CHECKER:
                if(this.mAppLanguage.equals(MConstants.AppLanguages.RO_LANG))
                    this.setText(MConstants.INTERNAL_DB_CHECKING_TEXT_RO);
                if(this.mAppLanguage.equals(MConstants.AppLanguages.EN_LANG))
                    this.setText(MConstants.INTERNAL_DB_CHECKING_TEXT_EN);
                checkStatus = MConstants.STRING_EMPTY;
                setViewParameters(checkDone,checkStatus);
                setViewConstraints(checkDone);
                break;

        }
    }

    public void SetParamsTextView(String checkDone, String checkStatus) {
            if(this.mAppLanguage.equals(MConstants.AppLanguages.EN_LANG)) {
                switch (checkStatus) {
                    case MConstants.TEST_FAILED_RO:
                        checkStatus = MConstants.TEST_FAILED_EN;
                        break;
                    case MConstants.TEST_PASSED_RO:
                        checkStatus = MConstants.TEST_PASSED_EN;
                        break;
                    case MConstants.TEST_NEUTRAL_RO:
                        checkStatus = MConstants.TEST_NEUTRAL_EN;
                        break;
                }
            }
        this.setText(checkStatus);
        setViewParameters(checkDone,checkStatus);
        setViewConstraints(checkDone,checkStatus);
    }

    public void ShowTextView(){
        this.mConstraintSet.applyTo(this.mConstraintLayout);
    }
    //endregion


    //region Private Methods

    /**
     * This function sets the necessary parameters so that the text would look like in the rest of the app.
     */
    private void setViewParameters(String checkDone,String checkStatus) {
        this.setTypeface(null, Typeface.NORMAL);
        this.setTypeface(ResourcesCompat.getFont(GlobalMainContext.getMainContext(),R.font.share_tech_mono));
        this.setTextSize(14);
        if(checkStatus.equals(MConstants.STRING_EMPTY))
            this.setTextColor(Color.parseColor(MConstants.TextViewParameters.BLACK_COLOR_ID));
        else {
            switch (checkStatus) {
                case MConstants.TEST_PASSED_RO:
                    this.setTextColor(Color.parseColor(MConstants.TextViewParameters.GREEN_COLOR_ID));
                    break;
                case MConstants.TEST_FAILED_RO:
                    this.setTextColor(Color.parseColor(MConstants.TextViewParameters.RED_COLOR_ID));
                    break;
                case MConstants.TEST_PASSED_EN:
                    this.setTextColor(Color.parseColor(MConstants.TextViewParameters.GREEN_COLOR_ID));
                    break;
                case MConstants.TEST_FAILED_EN:
                    this.setTextColor(Color.parseColor(MConstants.TextViewParameters.RED_COLOR_ID));
                    break;
                case MConstants.TEST_NEUTRAL_RO:
                    this.setTextColor(Color.parseColor(MConstants.TextViewParameters.BLEU_COLOR_ID));
                    break;
                case MConstants.TEST_NEUTRAL_EN:
                    this.setTextColor(Color.parseColor(MConstants.TextViewParameters.BLEU_COLOR_ID));
                    break;
            }
        }
        switch (checkDone) {
            case MConstants.SIGNAL_CHECKER:
                this.setId(MConstants.SIGNAL_CHECKING_TEXT_VIEW_ID);
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                this.setId(MConstants.PBDB_CHECKING_TEXT_VIEW_ID);
                break;
            case MConstants.INTERNAL_DB_CHECKER:
                this.setId(MConstants.INTERNAL_CHECKING_TEXT_VIEW_ID);
                break;

        }
        if(!checkStatus.equals(MConstants.STRING_EMPTY)) {
            switch (checkDone) {
                case MConstants.SIGNAL_CHECKER:
                    this.setId(MConstants.SIGNAL_CHECKING_STATUS_ID1);
                    break;
                case MConstants.PUBLIC_DB_CHECKER:
                    this.setId(MConstants.PBDB_CHECKING_STATUS_ID1);
                    break;
                case MConstants.INTERNAL_DB_CHECKER:
                    this.setId(MConstants.INTERNAL_CHECKING_STATUS_ID1);
                    break;

            }
        }

    }



    /**
     * This function sets all the necessary constrains to show the view on the fragment successfully
     * @param checkDone I need to set constraints which depend on the check done
     */
    private void setViewConstraints(String checkDone) {
        this.setGeneralConstraints();
        this.mConstraintSet.connect(this.getId(),ConstraintSet.LEFT, R.id.checkerConstraintLayout,ConstraintSet.LEFT,20);
        switch (checkDone) {
            case MConstants.SIGNAL_CHECKER:
                this.mConstraintSet.connect(this.getId(),ConstraintSet.TOP,R.id.checkerStartButtonView,ConstraintSet.BOTTOM,30);
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                this.mConstraintSet.connect(this.getId(),ConstraintSet.TOP,MConstants.SIGNAL_CHECKING_TEXT_VIEW_ID,ConstraintSet.BOTTOM, 30);
                break;
            case MConstants.INTERNAL_DB_CHECKER:
                this.mConstraintSet.connect(this.getId(),ConstraintSet.TOP,MConstants.PBDB_CHECKING_TEXT_VIEW_ID,ConstraintSet.BOTTOM,30);
                break;
        }
    }

    private void setViewConstraints(String checkDone, String checkStatus) {
       this.setGeneralConstraints();
        this.mConstraintSet.connect(this.getId(),ConstraintSet.RIGHT, R.id.checkerConstraintLayout,ConstraintSet.RIGHT,20);
        switch (checkDone) {
            case MConstants.SIGNAL_CHECKER:
                this.mConstraintSet.connect(this.getId(),ConstraintSet.TOP,R.id.checkerStartButtonView,ConstraintSet.BOTTOM,30);
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                this.mConstraintSet.connect(this.getId(),ConstraintSet.TOP,MConstants.SIGNAL_CHECKING_STATUS_ID1,ConstraintSet.BOTTOM,30);
                break;
            case MConstants.INTERNAL_DB_CHECKER:
                this.mConstraintSet.connect(this.getId(),ConstraintSet.TOP,MConstants.PBDB_CHECKING_STATUS_ID1,ConstraintSet.BOTTOM,30);
                break;
        }
    }

    private void setGeneralConstraints() {
        this.mConstraintLayout.addView(this,0);
        this.mConstraintSet.clone(this.mConstraintLayout);
        this.mConstraintSet.constrainWidth(this.getId(), ConstraintSet.WRAP_CONTENT);
        this.mConstraintSet.constrainHeight(this.getId(),ConstraintSet.WRAP_CONTENT);
    }

    //endregion

}
