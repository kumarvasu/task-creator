
/* 
* Copyright 2024 - 2024 the original author or authors.
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
* https://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.task.agentic;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// ------------------------------------------------------------
// EVALUATOR-OPTIMIZER
// ------------------------------------------------------------

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ChatClient.Builder chatClientBuilder) {
		var chatClient = chatClientBuilder.build();
		return args -> {
			String response = new TaskCreator(chatClient).createTask("""
					<user input>
					{
					    "task_id": 100,
					    "approver_name": "Andavar Surulinathan",
					    "approver_email": "BH01126630@devcorptenant.com"
					    "requestor_name": "Praveen Kalla",
					    "task_type": "One Cert",
					    "task_details": "Ad group: fglbcadasfdf, owner: G234sfsad",
					    "task_creation_date": "29-May-2025",
					    "due_date": "01-June-2025"
					  }.
					</user input>
					""");

			System.out.println("FINAL OUTPUT:\n : " + response);
		};
	}
}
