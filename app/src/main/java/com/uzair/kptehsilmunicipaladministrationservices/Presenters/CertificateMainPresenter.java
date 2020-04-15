package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface CertificateMainPresenter
{
    void showBottomSheet();

    void retrieveAllCertificates(DatabaseReference dbRef , FirebaseAuth fbAuth);

}
