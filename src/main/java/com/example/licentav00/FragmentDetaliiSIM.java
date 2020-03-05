package com.example.licentav00;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import org.w3c.dom.Text;

import Model.Dispozitiv;
import Model.Network;
import Model.SIM;

public class FragmentDetaliiSIM extends Fragment {
    public FragmentDetaliiSIM(Context context){
        mainContext = context;
    }

    Context mainContext;
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflater transforma XML in View
        View inflaterView =  inflater.inflate(R.layout.fragment_sim_details, container, false);
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ImageView hamburger = inflaterView.findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //region Set network Information
        Network network = new Network(mainContext);

        TextView operatorNameTextView = (TextView) inflaterView.findViewById(R.id.providerNameTextView);
        operatorNameTextView.setText(network.GetProviderName());

        TextView operatorCodeTextView = (TextView) inflaterView.findViewById(R.id.operatorCodeTextView);
        operatorCodeTextView.setText(network.GetProviderCode());

        TextView typeTextView = (TextView) inflaterView.findViewById(R.id.typeTextView);
        typeTextView.setText(network.GetProviderType());

        TextView lacTextView = (TextView) inflaterView.findViewById(R.id.lacTextView);
        lacTextView.setText(network.GetProviderLAC());

        TextView cidTextView = (TextView) inflaterView.findViewById(R.id.cidTextView);
        cidTextView.setText(network.GetProviderCID());

        //endregion


        //region Set SIM Information
        SIM sim = new SIM(mainContext);

        TextView countryTextView = (TextView) inflaterView.findViewById(R.id.countryTextView);
        countryTextView.setText(sim.GetSIMCountry());

        TextView codeTextView = (TextView) inflaterView.findViewById(R.id.codeTextView);
        codeTextView.setText(sim.GetOperatorID());

        TextView nameTextView = (TextView) inflaterView.findViewById(R.id.simOpNameTextView);
        nameTextView.setText(sim.GetOperatorName());

        TextView imsiTextView = (TextView) inflaterView.findViewById(R.id.imsiCodeTextView);
        imsiTextView.setText(sim.GetIMSI());

        TextView serialTextView = (TextView) inflaterView.findViewById(R.id.simSerialTextView);
        serialTextView.setText(sim.GetSerial());
        //endregion

        //region Set Device Information
        Dispozitiv device = new Dispozitiv(mainContext);
        TextView phoneTypeTextView = (TextView) inflaterView.findViewById(R.id.phoneTypeTextView);
        phoneTypeTextView.setText(device.getPhoneType());

        TextView imeiTextView = (TextView) inflaterView.findViewById(R.id.imeiTextView);
        imeiTextView.setText(device.getIMEI());

        return inflaterView;
    }

}
