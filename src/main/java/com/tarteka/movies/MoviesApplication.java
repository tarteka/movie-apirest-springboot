package com.tarteka.movies;

import com.tarteka.movies.entity.PermissionEntity;
import com.tarteka.movies.entity.RoleEntity;
import com.tarteka.movies.entity.RoleTypeEnum;
import com.tarteka.movies.entity.UserEntity;
import com.tarteka.movies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Value("${TEST_PASSWORD}")  // Inyecta la variable de entorno
	private String testPassword;

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			// create PERMISSIONS
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();

			// create ROLES
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleTypeEnum.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleTypeEnum.USER)
					.permissionList(Set.of(createPermission, readPermission))
					.build();

			RoleEntity roleGuest = RoleEntity.builder()
					.roleEnum(RoleTypeEnum.GUEST)
					.permissionList(Set.of(readPermission))
					.build();

			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleTypeEnum.DEV)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
					.build();

			// create USERS
			UserEntity userAdmin = UserEntity.builder()
					.username("tarteka")
					.password(new BCryptPasswordEncoder().encode(testPassword))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userUsuario = UserEntity.builder()
					.username("usuario")
					.password(new BCryptPasswordEncoder().encode(testPassword))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
					.build();

			UserEntity userGuest = UserEntity.builder()
					.username("guest")
					.password(new BCryptPasswordEncoder().encode(testPassword))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleGuest))
					.build();

			UserEntity userDev = UserEntity.builder()
					.username("dev")
					.password(new BCryptPasswordEncoder().encode(testPassword))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();

			userRepository.saveAll(List.of(userAdmin, userUsuario, userGuest, userDev));
		};
	}
}
