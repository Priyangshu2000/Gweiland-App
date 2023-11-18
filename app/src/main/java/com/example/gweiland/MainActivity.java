package com.example.gweiland;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.gweiland.Fragments.NFT;
import com.example.gweiland.Fragments.cyptocurrency;
import com.example.gweiland.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainActivity.getRoot());


        
        setFragment(new cyptocurrency(),"");
        setClickListener();


    }

    private void setClickListener() {

        mainActivity.bottomNavBar.getMenu().findItem(R.id.exchange).setChecked(true);

        mainActivity.cryptoCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.cryptoCurrency.setTextColor(getColor(R.color.brand));
                mainActivity.nft.setTextColor(getColor(R.color.brand_light));
                setFragment(new cyptocurrency(),"");
            }
        });

        mainActivity.nft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.nft.setTextColor(getColor(R.color.brand));
                mainActivity.cryptoCurrency.setTextColor(getColor(R.color.brand_light));
                setFragment(new NFT(),"");
            }
        });


        mainActivity.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(MainActivity.this, mainActivity.filterButton);
                popup.getMenuInflater()
                        .inflate(R.menu.filter_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        String title=item.getTitle().toString();


                        if(title.equals("Price")){
                           setFragment(new cyptocurrency(),"price");
                        }else{
                            setFragment(new cyptocurrency(),"volume_24h");
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

    }

    private void setFragment(Fragment fragment,String tag) {

        Bundle bundle=new Bundle();
        bundle.putString("Tag",tag);
        fragment.setArguments(bundle);

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
    }


}