package org.example.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    Long id;
    String name;
    String email;

    @Override
    public String toString() {
        return "id " + id +
                ", " + name +
                ", email: " + email + "\n";
    }
}
