package ru.lifelaboratory.asi.service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;
import ru.lifelaboratory.asi.entity.Document;

public interface DocumentService {

    @POST("/documents")
    Call<ArrayList<Document>> getDocumentsById
}
