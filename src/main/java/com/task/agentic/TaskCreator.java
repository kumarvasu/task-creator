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
			Your goal is to create a task message based on the input. 
						
			Input consists of following information. 
			1. Task details in the JSON format. 
			2. Detailed description about every field in the JSON
			3. Historical information of the tasks that was acted by the task owner. It provides whether the task owner has approved or rejected the task and the rationale he has provided for making that decision.
			4. Provide the output in the following format with below default fields. 
			5. Add any additional information as per Task Personalization configuration as part of Key Info

			Title: Approval Needed: [Title]
			Summary: [TaskSummary]
			Key Info:
			- Requester Name: [Requestor]
			- Priority: [priority]
			- AI Suggestion: [SuggestedAction]
			- Decision Rationale: [DecisionRationale]
			- Decision Comment: [DecisionComment]
			
			Extract the details from the input json and populate the field value as per below logic.
			Title - Generate a suitable title using task type, requestor and the parameters provided for the task type
			Task Summary - Summarize the task details highlighting key information
			Requestor - Extract from requestor_name field
			Priority - if no priority field provided, then assign "Normal" priority
			AI Suggestion - Provide suggested action (either approve or reject) based on the historical information and parameters provided to consider for making the decision
			Decision Rationale - Provide a short summary of how this decision has been arrived at.
			Decision Comment - Provide a short comment to be added along with the decision.
			
			6. Apply any field filtering if provided. Add any additional information as per Task Personalization configuration as part of Key Info. 

			Return the output as single String - not as JSON. 
Video settings



			Here is the exact format to follow, including all quotes and braces:
Video settings



			Example:
			Input:
			{
			    "task_id": 100,
			    "approver_name": "Kumar Vasudevan",
			    "requestor_name" : "Rajesh V"
			    "task_type": "One Cert",
			    "task_details": "Ad group: fglbcadasfdf, owner: G234sfsad",
			    "task_creation_date": "29-May-2025",
			    "due_date": "01-June-2025"
			  }
			  
			Output:
			 			
			Title: Approval Needed: One Cert Task Requested By Rajesh V
			Summary: One Cert task requiring attestation that these AD groups are still active and owned by the given owner.
			Key Info:
			- Requester Name: Rajesh V
			- Priority: Normal
			- AI Suggestion: Approve
			- Decision Rationale: AD group and the owner details are matching as per OneAD and there is no change in the previous request that was approved by you.
			- Decision Comment: Approved - AD group ownership verified.
						
			
			""";

	private final ChatClient chatClient;

	private final String systemPrompt;

	private final ContextProvider contextProvider;

	public TaskCreator(ChatClient chatClient) {
		this(chatClient, SYSTEM_PROMPT);
	}

	public TaskCreator(ChatClient chatClient, String systemPrompt) {
		Assert.notNull(chatClient, "ChatClient must not be null");
		Assert.hasText(systemPrompt, "Generator prompt must not be empty");

		this.chatClient = chatClient;
		this.systemPrompt = systemPrompt;
		this.contextProvider = new ContextProvider();
	}

	public String createTask(String task) {
		List<String> memory = new ArrayList<>();

		return createTask(task, contextProvider.getContext("One Cert", "Kumar Vasudevan"), memory);
	}

	private String createTask(String task, String context, List<String> memory) {

		String response = generate(task, context);
		memory.add(response);

		return response;
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
	private String generate(String task, String context) {
		String response = chatClient.prompt()
				.user(u -> u.text("{prompt}\n{context}")
						.param("prompt", this.systemPrompt+"\\n"+task)
						.param("context", context))
	//					.param("task", task))
				.call()
				.content();

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
