package com.example.shoppingwise2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SupabaseApi {
    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json",
            "Prefer: return=minimal"
    })
    @POST("utilizador")
    Call<Void> createUser(@Body Utilizador utilizador);
}
