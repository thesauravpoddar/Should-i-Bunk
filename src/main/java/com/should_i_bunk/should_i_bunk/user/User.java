package com.should_i_bunk.should_i_bunk.user;

import com.should_i_bunk.should_i_bunk.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "FirstName" , nullable = false)
    private String firstName;
    @Column(name = "LastName" , nullable = false)
    private String lastName;
    @Column(name = "EMAIL" , nullable = false , unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "IS_ACCOUNT_LOCKED")
    private boolean locked;
    @Column(name = "IS_CRENDETIAL_EXPIRED")
    private boolean credentialsExpired;

    @Column(name = "ENABLED_AT")
    private LocalDateTime createdAt;

    @Column(name = "LASTMODIFIEDDATE")
    private LocalDateTime lastModifiedDate;

    @ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE} ,
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "USERS_ROLES" , joinColumns =
            {
                    @JoinColumn(name = "users_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roles_id")
            }
    )
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(CollectionUtils.isEmpty(this.roles)){
            return List.of();
        }
       return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
}
