package com.alibu2.spring_security_asymetric_encryption2.role;

import com.alibu2.spring_security_asymetric_encryption2.common.BaseEntity;
import com.alibu2.spring_security_asymetric_encryption2.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Builder
@Table(name = "ROLES")
@SuperBuilder
//@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
