package ru.lifelaboratory.asi.service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.entity.FilterForDocument;

public interface DocumentService {

    @POST("/documents")
    Call<ArrayList<Document>> getDocumentsById(@Body FilterForDocument filter);
}
