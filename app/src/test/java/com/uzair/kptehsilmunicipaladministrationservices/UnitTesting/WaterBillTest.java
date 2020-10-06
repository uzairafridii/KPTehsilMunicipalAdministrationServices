package com.uzair.kptehsilmunicipaladministrationservices.UnitTesting;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.uzair.kptehsilmunicipaladministrationservices.Models.WaterBillPresenterImplmenter;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.WaterBillPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.WaterBillView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WaterBillTest
{
    @Mock
    private WaterBillPresenter presenter;

    @Mock
    private WaterBillView billView;
    private DatabaseReference dbRef;
    private Context context;


    @Before
    public void setUp() throws Exception {

        presenter = new WaterBillPresenterImplmenter(billView , context);

    }

    @Test
    public void noRefNumber() throws Exception
    {
        presenter.getUserBill(dbRef, "");
        verify(billView).showErrorMessage("Please enter reference number");

    }


    @Test
    public void validRefNo() throws Exception
    {
        presenter.getUserBill(dbRef, "12324fsdf");
        verify(billView).showProgressDialog();
    }


}
