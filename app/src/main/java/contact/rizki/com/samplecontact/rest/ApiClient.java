package contact.rizki.com.samplecontact.rest;

/**
 * Created by PERSONAL on 10/18/2018.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public  static final String BASE_URL="https://simple-contact-crud.herokuapp.com/";
    private static Retrofit retrofit = null;

    public static Retrofit contactAPI(){
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return  retrofit;
    }

}
