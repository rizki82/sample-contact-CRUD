package contact.rizki.com.samplecontact.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PERSONAL on 10/20/2018.
 */

public class GetDetailContactResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ContactModel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContactModel getData() {
        return data;
    }

    public void setData(ContactModel data) {
        this.data = data;
    }
}
