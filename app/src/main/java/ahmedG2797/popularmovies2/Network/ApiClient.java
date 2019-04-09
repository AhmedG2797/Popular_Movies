package ahmedG2797.popularmovies2.Network;

import ahmedG2797.popularmovies2.Activities.MainActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ahmedG2797.popularmovies2.Activities.MainActivity.BASE_URL;

public final class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
