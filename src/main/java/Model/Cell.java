package Model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;

import Controller.CellController;
import Utils.MConstants;

public class Cell implements Parcelable {
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
        mSignalDbm = mCellController.GetSignalDbm();
        mAsuLevel = mCellController.GetAsuLevel();
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
            Device dispozitiv = new Device(mainContext);
            mCellLat = dispozitiv.GetLatitude();
            mCellLong = dispozitiv.GetLongitude();
        }
        else {
            mCellStatus = MConstants.Cell.GOOD;
            mCellController.addSignalStrengthToDB(Integer.parseInt(GetCid()),Integer.parseInt(GetSignalDbm()));
        }
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


    protected Cell(Parcel in) {
        mArfcn = in.readString();
        mBsic = in.readString();
        mCid = in.readString();
        mLac = in.readString();
        mMcc = in.readString();
        mMnc = in.readString();
        mNetworkOperator = in.readString();
        mCellStatus = in.readString();
        mNeighbouringCells = in.createTypedArrayList(Cell.CREATOR);
        mAsuLevel = in.readString();
        mSignalDbm = in.readString();
        mSignalLevel = in.readString();
        mCellLat = in.readString();
        mCellLong = in.readString();
        mCellAddress = in.readString();
    }

    public static final Parcelable.Creator<Cell> CREATOR = new Parcelable.Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel in) {
            return new Cell(in);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };

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
        //mAsuLevel = mCellController.GetAsuLevel();
        return this.mAsuLevel;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetSignalDbm(){
       // mSignalDbm = mCellController.GetSignalDbm();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArfcn);
        dest.writeString(mBsic);
        dest.writeString(mCid);
        dest.writeString(mLac);
        dest.writeString(mMcc);
        dest.writeString(mMnc);
        dest.writeString(mNetworkOperator);
        dest.writeString(mCellStatus);
        dest.writeString(mAsuLevel);
        dest.writeString(mSignalDbm);
        dest.writeString(mSignalLevel);
        dest.writeString(mTimingAdvance);
        dest.writeString(mCellLat);
        dest.writeString(mCellLong);
        dest.writeString(mCellAddress);
    }

    public class Creator implements Parcelable.Creator<Cell> {

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public Cell createFromParcel(Parcel source) {
            return new Cell(source);

        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    }


    //endregion

    //endregion
}
