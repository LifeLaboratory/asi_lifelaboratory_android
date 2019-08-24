package ru.lifelaboratory.asi.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.lifelaboratory.asi.entity.Service;

public interface ServicesService {

    @GET("/ads")
    Call<List<Service>> get();

}