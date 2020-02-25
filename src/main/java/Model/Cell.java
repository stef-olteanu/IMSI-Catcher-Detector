package Model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;

import Controller.CellController;
import Utils.MConstants;

public class Cell {
    //region Constructor
    public Cell(){

    }

    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.P)
    public Cell(Context mContext) throws IOException, InterruptedException {
        mainContext = mContext;
        mCellController = new CellController(mainContext);
        //region Cell Identity Initialize
        mArfcn = mCellController.GetArfcn();
        mBsic = mCellController.GetBsic();
        mCid = mCellController.GetCid();
        mLac = mCellController.GetLac();
        mMcc = mCellController.GetMcc();
        mMnc = mCellController.GetMnc();
        mNetworkOperator = mCellController.getNetworkOperator();
        //endregion

        //region Signal Strength Initialize

        mSignalDbm = mCellController.GetSignalDbm();mAsuLevel = mCellController.GetAsuLevel();
        mSignalLevel = mCellController.GetSignalLevel();
        mTimingAdvance = mCellController.GetTimingAdvance();
        //endregion

        //region Cell Coordinates Initialize
        mCellLat = mCellController.GetCellLat();
        mCellLong = mCellController.GetCellLong();
        mCellAddress = mCellController.GetCellAddress();
        //endregion

        mNeighbouringCells = mCellController.GetAllCellNeighbours();
        /*
        Status is:
            -GOOD       ->green
            -WARNING    ->orange
            -ALERT      ->red
        In the beginning we assume that all cells are good. We will change the status later if necessary.
         */
        if(mCellAddress == "Cell not found"){
            mCellStatus = MConstants.Cell.WARNING;
            Dispozitiv dispozitiv = new Dispozitiv(mainContext);
            mCellLat = dispozitiv.GetLatitude();
            mCellLong = dispozitiv.GetLongitude();
        }
        else
            mCellStatus = MConstants.Cell.GOOD;
    }
    //endregion

    //region Private Members
    //region Cell Identity Information
    private Context mainContext;
    private CellController mCellController;

    private String mArfcn;
    private String mBsic;
    private String mCid;
    private String mLac;
    private String mMcc;
    private String mMnc;
    private String mNetworkOperator;


    private String mCellStatus;

    private List<Cell> mNeighbouringCells;
    //endregion

    //region Signal Strength Information
    private String mAsuLevel;
    private String mSignalDbm;
    private String mSignalLevel;
    private String mTimingAdvance;
    //endregion

    //region Cell Coordinates
    private String mCellLat;
    private String mCellLong;
    private String mCellAddress;
    //endregion
    //endregion


    //region Public Methods
    //region Cell Identity Getters
    public String GetArcn(){
        return mArfcn;
    }

    public String GetBsic(){
        return mBsic;
    }

    public String GetCid(){
        return mCid;
    }

    public String GetLac(){
        return mLac;
    }

    public String GetMcc(){
        return mMcc;
    }

    public String GetMnc(){
        return mMnc;
    }

    public String GetNetworkOperator(){
        return mNetworkOperator;
    }

    public String getmCellStatus() {
        return mCellStatus;
    }

    public void setmCellStatus(String mCellStatus) {
        this.mCellStatus = mCellStatus;
    }

    public List<Cell> getmNeighbouringCells() {
        return mNeighbouringCells;
    }

    public void setmNeighbouringCells(List<Cell> mNeighbouringCells) {
        this.mNeighbouringCells = mNeighbouringCells;
    }

    //endregion

    //region Cel Signal Strength Getters
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetAsuLevel(){
        mAsuLevel = mCellController.GetAsuLevel();
        return mAsuLevel;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetSignalDbm(){
        mSignalDbm = mCellController.GetSignalDbm();
        return mSignalDbm;
    }

    public String GetSignalLevel(){
        return mSignalLevel;
    }

    public String GetTimingAdvance(){
        return mTimingAdvance;
    }
    //endregion

    //region Cell Coordinates Getters
    public String GetCellLat(){
        return mCellLat;
    }

    public String GetCellLong(){
        return mCellLong;
    }

    public String GetCellAddress(){
        return mCellAddress;
    }
    //endregion


    //region Cell Data Setters
    public void setmArfcn(String mArfcn) {
        this.mArfcn = mArfcn;
    }

    public void setmBsic(String mBsic) {
        this.mBsic = mBsic;
    }

    public void setmCid(String mCid) {
        this.mCid = mCid;
    }

    public void setmLac(String mLac) {
        this.mLac = mLac;
    }

    public void setmMcc(String mMcc) {
        this.mMcc = mMcc;
    }

    public void setmMnc(String mMnc) {
        this.mMnc = mMnc;
    }

    public void setmNetworkOperator(String mNetworkOperator) {
        this.mNetworkOperator = mNetworkOperator;
    }

    public void setmAsuLevel(String mAsuLevel) {
        this.mAsuLevel = mAsuLevel;
    }

    public void setmSignalDbm(String mSignalDbm) {
        this.mSignalDbm = mSignalDbm;
    }

    public void setmSignalLevel(String mSignalLevel) {
        this.mSignalLevel = mSignalLevel;
    }

    public void setmTimingAdvance(String mTimingAdvance) {
        this.mTimingAdvance = mTimingAdvance;
    }

    public void setmCellLat(String mCellLat) {
        this.mCellLat = mCellLat;
    }

    public void setmCellLong(String mCellLong) {
        this.mCellLong = mCellLong;
    }

    public void setmCellAddress(String mCellAddress) {
        this.mCellAddress = mCellAddress;
    }

    //endregion

    //endregion
}
