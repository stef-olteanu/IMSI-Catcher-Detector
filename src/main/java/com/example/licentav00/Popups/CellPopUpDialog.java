package com.example.licentav00.Popups;



import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.licentav00.R;

import Model.Cell;

public class CellPopUpDialog extends DialogFragment {

    public CellPopUpDialog(Cell cell){
        mCell = cell;
    }

    private Cell mCell;

    @Nullable
    @Override
    /**
     * Creates a popup view in the moment the user clicks on one cell on the map. It will display information about the cell it receives in the constructor.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cell_popup,container,false);
        ImageView circleImageView = view.findViewById(R.id.circlePopImageView);
        switch (mCell.getmCellStatus()){
            case "GOOD":
                circleImageView.setImageResource(R.drawable.circlegreen);
                break;
            case "WARNING":
                circleImageView.setImageResource(R.drawable.circleorange);
                break;
            case "ALERT":
                circleImageView.setImageResource(R.drawable.circle);
                break;
        }

        Button okButton = view.findViewById(R.id.okButtonTextView);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        TextView cidTextView = view.findViewById(R.id.cidPopTextView);
        cidTextView.setText(mCell.GetCid());

        TextView lacTextView = view.findViewById(R.id.lacPopTextView);
        lacTextView.setText(mCell.GetLac());

        TextView mccTextView = view.findViewById(R.id.mccPopTextView);
        mccTextView.setText(mCell.GetMcc());

        TextView mncTextView = view.findViewById(R.id.mncPopTextView);
        mncTextView.setText(mCell.GetMnc());

        TextView latTextView = view.findViewById(R.id.latPopTextView);
        latTextView.setText(mCell.GetCellLat());

        TextView lonTextView = view.findViewById(R.id.lonPopTextView);
        lonTextView.setText(mCell.GetCellLong());

        TextView statusTextView = view.findViewById(R.id.statusPopTextView);
        statusTextView.setText(mCell.getmCellStatus());


        return view;
    }
}
