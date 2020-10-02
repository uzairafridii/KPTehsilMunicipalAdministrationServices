package com.uzair.kptehsilmunicipaladministrationservices.AppModules.TaxesDetails;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.Models.TaxesPresenterImplmenter;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.TaxesPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.TaxesView;

import java.util.List;

public class Taxes extends AppCompatActivity implements TaxesView
{

    private Toolbar mToolbar;
    private RecyclerView taxesRv;
    private LinearLayout notFoundLayout;
    private AdapterForTaxesRv taxAdapter;
    private TaxesPresenter presenter;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxes);
        initViews();

        presenter.getTaxesList(dbRef);

    }

    private void initViews()
    {
        //  app tool bar
        mToolbar = findViewById(R.id.taxes_tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.backicon);
        setTitle("Taxes Detail");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        notFoundLayout = findViewById(R.id.notFoundData);

        taxesRv = findViewById(R.id.taxesDetails);
        taxesRv.setLayoutManager(new LinearLayoutManager(this));

        dbRef  = FirebaseDatabase.getInstance().getReference().child("TaxDetails");

        presenter = new TaxesPresenterImplmenter(this);

    }

    @Override
    public void setAdapter(List<TaxesModel> taxesList) {
        taxAdapter = new AdapterForTaxesRv(taxesList , this , this);
        taxesRv.setAdapter(taxAdapter);
    }

    @Override
    public void hideNotFoundLayout()
    {
        notFoundLayout.setVisibility(View.GONE);

    }

    @Override
    public void showNotFoundLayout() {
      notFoundLayout.setVisibility(View.VISIBLE);
    }
}
