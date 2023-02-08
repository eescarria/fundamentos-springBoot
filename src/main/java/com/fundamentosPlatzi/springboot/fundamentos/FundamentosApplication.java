package com.fundamentosPlatzi.springboot.fundamentos;

import com.fundamentosPlatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosPlatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosPlatzi.springboot.fundamentos.bean.MyBeanWithDependencyImplement;
import com.fundamentosPlatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosPlatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosPlatzi.springboot.fundamentos.entity.User;
import com.fundamentosPlatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosPlatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosPlatzi.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;


	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("Test transactional 1", "test1@mail.com", LocalDate.of(2023, 1, 14));
		User test2 = new User("Test transactional 2", "test2@mail.com", LocalDate.of(2022, 10, 14));
		User test3 = new User("Test transactional 3", "test1@mail.com", LocalDate.of(2022, 5, 8));
		User test4 = new User("Test transactional 4", "test4@mail.com", LocalDate.of(2022, 6, 10));

		List<User> users = Arrays.asList(test1, test2, test3, test4);

		try {
			userService.saveTransactional(users);
		}catch(Exception e){
			LOGGER.error("Esta es una excepción dentro del método transaccional" + e);
		}

		userService.getAllUsers().stream()
				.forEach(user -> LOGGER.info("Este es el usuario dentro del método transaccional" + user));

	}

	private void getInformationJpqlFromUser(){
		/*
		LOGGER.info("Usuario con el método  findByUserEmail " + userRepository.findByUserEmail("juan@mail.com")

				.orElseThrow(()-> new RuntimeException("No se encontró el usuario")));

		userRepository.findAndSort("Dani", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("User con método Sort: " + user));

		userRepository.findByName("John")
				.forEach(user -> LOGGER.info("Usuario con query method: " + user));

		LOGGER.info("Usuario con query method findByEmailAndName: "+
				userRepository.findByEmailAndName("daniela@mail.com", "Daniela")
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

		userRepository.findByNameLike("%Dani%")
				.forEach(user -> LOGGER.info("Usuario findByNameLike: "+ user));

		userRepository.findByNameOrEmail(null,"julian@mail.com")
				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail: "+ user));

		*/

		userRepository.findByBirthDateBetween(LocalDate.of(2021,3,1), LocalDate.of(2022,10,10))
				.forEach(user -> LOGGER.info("Usuario findByBirthDateBetween: "+ user));

		userRepository.findByNameLikeOrderByIdDesc("%Dani%")
				.forEach(user-> LOGGER.info("Usuario findByNameLikeOrderByIdDesc: "+ user));

		userRepository.findByNameContainingOrderByIdDesc("Dani")
				.forEach(user-> LOGGER.info("Usuario findByNameContainingOrderByIdDesc: "+ user));

		LOGGER.info("El usuario a partir del named parameter es: " +
				userRepository.getAllByBirthDateAndEmail(LocalDate.of(1990,01,14),
								"estefy@mail.com")
				.orElseThrow(() ->new RuntimeException("No se encontró el usuario a partir del named parameter")));
	}

	private void saveUsersInDataBase(){
		User user1 = new User("John", "john@mail.com", LocalDate.of(2022, 03, 14));
		User user2 = new User("Estefania", "estefy@mail.com", LocalDate.of(1990, 01, 14));
		User user3 = new User("Daniela", "daniela@mail.com", LocalDate.of(1995, 01, 14));
		User user4 = new User("Daniel", "daniel@mail.com", LocalDate.of(1999, 03, 14));
		User user5 = new User("Dani", "dani@mail.com", LocalDate.of(1997, 10, 14));
		User user6 = new User("Ana", "ana@mail.com", LocalDate.of(2000, 05, 04));
		User user7 = new User("Juan", "juan@mail.com", LocalDate.of(1987, 8, 20));
		User user8 = new User("Julian", "julian@mail.com", LocalDate.of(1980, 05, 22));
		User user9 = new User("Mateo", "mateo@mail.com", LocalDate.of(2022, 03, 01));
		User user10 = new User("Luisa", "luisa@mail.com", LocalDate.of(1991, 12, 14));
		User user11 = new User("Lucas", "lucas@mail.com", LocalDate.of(1991, 11, 04));
		User user12 = new User("Sandra", "sandra@mail.com", LocalDate.of(1980, 02, 14));

		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);
		list.forEach(userRepository::save);

	}
	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
		try{
			//error
			int value = 10/0;
			LOGGER.info("Mi valor: "+ value);
		}catch (Exception e){
			LOGGER.error("Esto es un error al dividir por 0"+ e.getMessage());
		}
	}
}
