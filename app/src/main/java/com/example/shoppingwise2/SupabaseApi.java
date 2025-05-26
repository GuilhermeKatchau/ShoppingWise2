package com.example.shoppingwise2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SupabaseApi {
    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json",
            "Prefer: return=minimal"
    })
    @POST("utilizador")
    Call<Void> createUser(@Body Utilizador utilizador);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json",
            "Prefer: return=minimal"
    })
    @GET("utilizador")
    Call<List<Utilizador>> login(
            @Query(value = "email", encoded = true) String email,
            @Query(value = "passwd", encoded = true) String passwd
    );

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @GET("utilizador")
    Call<List<Utilizador>> getUserById(@Query("id") String id);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("produto")
    Call<List<Produto>> createProduto(@Body Produto produto);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("precoloja")
    Call<PrecoLoja> createPrecoLoja(@Body PrecoLoja precoLoja);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json",
            "Prefer: return=minimal"
    })
    @POST("historia")
    Call<Void> createHistorico(@Body Historia historia);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZqc2pieG1pbHF4bnl2bnd6ZmJ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDczMTY5MDEsImV4cCI6MjA2Mjg5MjkwMX0.1gtDdssG4A3mqRPhJh0bdFZNM_s-eWGEIZENMkM9GtQ",
            "Content-Type: application/json"
    })
    @GET("historia")
    Call<List<Historia>> getHistoricoByUserId(@Query("id_utilizador") int idUtilizador,
                                              @Query("order") String order);
}



