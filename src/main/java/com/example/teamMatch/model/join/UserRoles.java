package com.example.teamMatch.model.join;


import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Users;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRoles {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users userId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles roleId;
}
