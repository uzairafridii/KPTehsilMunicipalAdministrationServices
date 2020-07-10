package com.uzair.kptehsilmunicipaladministrationservices.Views;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.TaxesDetails.TaxesModel;

import java.util.List;

public interface TaxesView
{
    void setAdapter(List<TaxesModel> taxesList);

    void hideNotFoundLayout();

    void showNotFoundLayout();

}
