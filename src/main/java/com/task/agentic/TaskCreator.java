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

import java.util.ArrayList;
import java.util.List;

import com.task.agentic.model.TaskDetailsDTO;
import org.springframework.ai.ResourceUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.util.Assert;

/**
 * Workflow: <b>Evaluator-optimizer</b>
 * <p/>
 * Implements the Evaluator-Optimizer workflow pattern for Large Language Model
 * (LLM) interactions. This workflow orchestrates a dual-LLM process where one
 * model
 * generates responses while another provides evaluation and feedback in an
 * iterative loop,
 * similar to a human writer's iterative refinement process.
 *
 * <p>
 * The workflow consists of two main components:
 * <ul>
 * <li>A generator LLM that produces initial responses and refines them based on
 * feedback</li>
 * <li>An evaluator LLM that analyzes responses and provides detailed feedback
 * for improvement</li>
 * </ul>
 *
 * <b>Usage Criteria</b>
 * This workflow is particularly effective in scenarios that meet the following
 * conditions:
 * <ul>
 * <li>Clear evaluation criteria exist for assessing response quality</li>
 * <li>Iterative refinement provides measurable value to the output</li>
 * <li>The task benefits from multiple rounds of critique and improvement</li>
 * </ul>
 *
 * <b>Fitness Indicators</b>
 * Two key indicators suggest this workflow is appropriate:
 * <ul>
 * <li>LLM responses can be demonstrably improved when feedback is
 * articulated</li>
 * <li>The evaluator LLM can provide substantive and actionable feedback</li>
 * </ul>
 *
 * <b>Example Applications</b>
 * <ul>
 * <li>Literary translation requiring capture of subtle nuances through
 * iterative refinement</li>
 * <li>Complex search tasks needing multiple rounds of searching and
 * analysis</li>
 * <li>Code generation where quality can be improved through systematic
 * review</li>
 * <li>Content creation requiring multiple drafts and specific improvements</li>
 * </ul>
 * 
 * @author Christian Tzolov
 * @see <a href=
 *      "https://www.anthropic.com/research/building-effective-agents">Building
 *      effective agents</a>
 */
@SuppressWarnings("null")
public class TaskCreator {

	public static final String SYSTEM_PROMPT = """
			
			
			""";

	private final ChatClient chatClient;

	private final String systemPrompt;

	private final ContextProvider contextProvider;

	private final TaskPublisher taskPublisher;

	public TaskCreator(ChatClient chatClient) {
		this(chatClient, ResourceUtils.getText("system-prompt.txt"));
	}

	public TaskCreator(ChatClient chatClient, String systemPrompt) {
		Assert.notNull(chatClient, "ChatClient must not be null");
		Assert.hasText(systemPrompt, "Generator prompt must not be empty");

		this.chatClient = chatClient;
		this.systemPrompt = systemPrompt;
		this.contextProvider = new ContextProvider();
		this.taskPublisher = new TaskPublisher();
	}

	public String createTask(String task, TaskDetailsDTO taskDetailsDTO) {
		List<Response> memory = new ArrayList<>();

		return createTask(task, contextProvider.getContext(taskDetailsDTO.getOwner(), taskDetailsDTO.getAdGroup()), memory);
	}

	private String createTask(String task, String context, List<Response> memory) {

		Response response = generate(task, context);
		memory.add(response);
		return taskPublisher.publish(response);
	}

	/**
	 * Generates or refines a solution based on the given task and feedback context.
	 * This method represents the generator component of the workflow, producing
	 * responses that can be iteratively improved through evaluation feedback.
	 * 
	 * @param task    The primary task or problem to be solved
	 * @param context Previous attempts and feedback for iterative improvement
	 * @return A Generation containing the model's thoughts and proposed solution
	 */
	private Response generate(String task, String context) {
		Response response = chatClient.prompt()
				.user(u -> u.text("{prompt}\n{context}")
						.param("prompt", this.systemPrompt+"\\n"+task)
						.param("context", context)
						.param("task", task))
				.call()
				.entity(Response.class);

		System.out.println(String.format("\nRESPONSE:\n %s\n",
				response));
		return response;
	}

	/**
	 * Evaluates if a solution meets the specified requirements and quality
	 * criteria.
	 * This method represents the evaluator component of the workflow, analyzing
	 * solutions
	 * and providing detailed feedback for further refinement until the desired
	 * quality
	 * level is reached.
	 * 
	 * @param content The solution content to be evaluated
	 * @param task    The original task against which to evaluate the solution
	 * @return An EvaluationResponse containing the evaluation result
	 *         (PASS/NEEDS_IMPROVEMENT/FAIL)
	 *         and detailed feedback for improvement
	 */
	/*private EvaluationResponse evaluate(String content, String task) {

		EvaluationResponse evaluationResponse = chatClient.prompt()
				.user(u -> u.text("{prompt}\nOriginal task: {task}\nContent to evaluate: {content}")
						.param("prompt", this.evaluatorPrompt)
						.param("task", task)
						.param("content", content))
				.call()
				.entity(EvaluationResponse.class);

		System.out.println(String.format("\n=== EVALUATOR OUTPUT ===\nEVALUATION: %s\n\nFEEDBACK: %s\n",
				evaluationResponse.evaluation(), evaluationResponse.feedback()));
		return evaluationResponse;
	}*/

}
