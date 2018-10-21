package contact.rizki.com.samplecontact.rest;

/**
 * Created by PERSONAL on 10/18/2018.
 */



import contact.rizki.com.samplecontact.model.ContactModel;
import contact.rizki.com.samplecontact.model.GetContactSResponse;
import contact.rizki.com.samplecontact.model.GetDetailContactResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiInterface {
    @GET("contact")
    Call<GetContactSResponse> getContacts();
    @GET("contact/{id}")
    Call<GetDetailContactResponse> getContactDetail(@Path("id") String id);
    @PUT("contact/{id}")
    Call<GetDetailContactResponse> editContact(@Path("id") String id, @Body ContactModel body);
    @POST("contact")
    Call<GetDetailContactResponse> addContact(@Body ContactModel body);
    @DELETE("contact/{id}")
    Call<GetDetailContactResponse> deleteContact(@Path("id") String id);


}
