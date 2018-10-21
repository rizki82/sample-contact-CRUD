package contact.rizki.com.samplecontact.model;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PERSONAL on 10/19/2018.
 */

public class GetContactSResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<ContactModel> data;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ContactModel> getData() {
        return data;
    }

    public void setData(List<ContactModel> data) {
        this.data = data;
    }

}
