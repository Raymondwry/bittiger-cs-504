package demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class UserInfo {
    @JsonIgnore
    private Long id;

    private final String username;
    private final String address;

    public UserInfo() {
            this.username = null;
            this.address = null;
    }

    public UserInfo (
            @JsonProperty("username") String userName,
            @JsonProperty("address") String userAddress
    ) {
        this.username = userName;
        this.address = userAddress;
    }
}
