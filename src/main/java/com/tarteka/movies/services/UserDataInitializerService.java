package com.tarteka.movies.services;

import com.tarteka.movies.entity.PermissionEntity;
import com.tarteka.movies.entity.RoleEntity;
import com.tarteka.movies.entity.RoleTypeEnum;
import com.tarteka.movies.entity.UserEntity;
import com.tarteka.movies.repositories.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDataInitializerService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${TEST_PASSWORD}")  // Inyecta la variable de entorno
    private String testPassword;

    public UserDataInitializerService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initUserAndRoles();
    }

    private void initUserAndRoles() {

        // create PERMISSIONS
        PermissionEntity createPermission = createNewPermission("CREATE");
        PermissionEntity readPermission = createNewPermission("READ");
        PermissionEntity updatePermission = createNewPermission("UPDATE");
        PermissionEntity deletePermission = createNewPermission("DELETE");
        PermissionEntity refactorPermission = createNewPermission("REFACTOR");

        // create ROLES
        RoleEntity roleAdmin = createRole(
                RoleTypeEnum.ADMIN,
                Set.of(createPermission, readPermission, updatePermission, deletePermission));

        RoleEntity roleUser = createRole(
                RoleTypeEnum.USER,
                Set.of(createPermission, readPermission));

        RoleEntity roleGuest = createRole(
                RoleTypeEnum.GUEST,
                Set.of(readPermission));

        RoleEntity roleDeveloper = createRole(
                RoleTypeEnum.DEV,
                Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

        // create USERS
        UserEntity userAdmin = createUser("tarteka", testPassword, Set.of(roleAdmin));
        UserEntity userUsuario = createUser("usuario", testPassword, Set.of(roleUser));
        UserEntity userGuest = createUser("guest", testPassword, Set.of(roleGuest));
        UserEntity userDev = createUser("dev", testPassword, Set.of(roleDeveloper));

        userRepository.saveAll(List.of(userAdmin, userUsuario, userGuest, userDev));
    }

    private PermissionEntity createNewPermission(String name) {
        return PermissionEntity.builder()
                .name(name)
                .build();
    }

    private RoleEntity createRole(RoleTypeEnum roleType, Set<PermissionEntity> permissions) {
        return RoleEntity.builder()
                .roleEnum(roleType)
                .permissionList(permissions)
                .build();
    }

    private UserEntity createUser(String username, String rawPassword, Set<RoleEntity> roles) {
        return UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(roles)
                .build();
    }

}
