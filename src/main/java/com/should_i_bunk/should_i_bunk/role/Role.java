package com.should_i_bunk.should_i_bunk.role;

import com.should_i_bunk.should_i_bunk.common.BaseEntity;
import com.should_i_bunk.should_i_bunk.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ROLES")
@Entity
public class Role extends BaseEntity {


    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
