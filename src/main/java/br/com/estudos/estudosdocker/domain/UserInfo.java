package br.com.estudos.estudosdocker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    private long id;
    protected String username;
    @JsonIgnore
    protected String password;
    private Set<UserRole> roles = new HashSet<>();
}
