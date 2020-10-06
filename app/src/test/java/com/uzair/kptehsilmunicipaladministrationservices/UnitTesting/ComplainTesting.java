package com.uzair.kptehsilmunicipaladministrationservices.UnitTesting;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.uzair.kptehsilmunicipaladministrationservices.Models.AddComplaintsPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.AddComplaintsPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.AddComplaintsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ComplainTesting
{
    @Mock
    private AddComplaintsPresenter presenter;
    @Mock
    private AddComplaintsView complaintsView;
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private StorageReference storage;
    private List<Uri> list = new ArrayList<>();
    private Context context;


    @Before
    public void setUp() throws Exception
    {
        presenter = new AddComplaintsPresenterImplementer(complaintsView, context);
    }



    @Test
    public void shouldFailNoDataEnter()
    {
        presenter.storeComplaintDataToFirebase(dbRef, auth, storage , "","","",list,0,0);

        verify(complaintsView).showMessage("Sorry! select image and fill the fields", "info");
    }

    @Test
    public void shouldWorkIfDataEntered()
    {

        presenter.storeComplaintDataToFirebase(dbRef, auth, storage , "title","Des","Field",list,23.2,44.3);

        verify(complaintsView).showProgressBar();
    }

}
