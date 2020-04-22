package payloads;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

	private String accessToken;
    private String tokenType = "Bearer";

    Long id;

    public JwtAuthenticationResponse(String accessToken, Long id) {
        this.accessToken = accessToken;
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
