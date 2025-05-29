package lt.asirtu.manoFilmuSvetaine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignupRequestDTO {

    
    @Size(min = 3, max = 50)
    private String username;

    private Set<String> role;

    
    @Size(min = 6, max = 50)
    private String password;

    public  @Size(min = 3, max = 50) String getUsername() {
        return username;
    }

    public void setUsername( @Size(min = 3, max = 50) String username) {
        this.username = username;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public  @Size(min = 6, max = 50) String getPassword() {
        return password;
    }

    public void setPassword( @Size(min = 6, max = 50) String password) {
        this.password = password;
    }
}
