package com.evoxon.mastermind;

import com.evoxon.mastermind.domain.MindService;
import com.evoxon.mastermind.util.KeyboardInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MastermindApplication {

	public static void main(String[] args) {

		SpringApplication.run(MastermindApplication.class, args);

		KeyboardInput keyboardInput = new KeyboardInput();
		MindService mindService = new MindService(keyboardInput);
		mindService.initializeGame();
	}

}
