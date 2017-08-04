package com.offcn;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

public class CreateTable {
	ProcessEngine processEngine;
	/****
	 * 创建流程表
	 * */
	@Before
	public void createTable() {
		 processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
				.buildProcessEngine();
		
		System.out.println("------processEngine:" + processEngine); 
	}
	
	@Test
	public void deployFlow(){
		Deployment deployment=processEngine.getRepositoryService()
		.createDeployment()
		.name("第一个流程")
		.addClasspathResource("diagrams/HelloWorld.bpmn")
		.addClasspathResource("diagrams/HelloWorld.png")
		.deploy();
		
		System.out.println("deployment ID:"+deployment.getId());
		System.out.println("deployment Name:"+deployment.getName());
		
	}
	
	@Test
	public void FlowStart(){
		RuntimeService runtimeservice = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeservice.startProcessInstanceByKey("HelloWorldKey");
	
	   System.out.println(processInstance.getId());
	   System.out.println(processInstance.getDeploymentId());
	   
	   RepositoryService repositoryService = processEngine.getRepositoryService();
	   ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInstance.getDeploymentId());
	  
	   System.out.println(processDefinition.getId());
	   System.out.println(processDefinition.getKey());
	
	}
	
	@Test
	public void FindMyTask(){
		String assgin="岳总";
		List<Task> tasklist = processEngine.getTaskService()
				                               .createTaskQuery()
				                               .taskAssignee(assgin)
				                               .list();
	
		if(tasklist!=null&&tasklist.size()>0){
			for(Task task:tasklist){
				System.out.println("待办任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务创建时间:"+task.getCreateTime());
				System.out.println("任务办理人:"+task.getAssignee());
				System.out.println("流程实例ID:"+task.getProcessInstanceId());
				System.out.println("执行对象ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
			}
		}
	}
	
	@Test
	public void compleTask(){
		String taskid="10002";
		processEngine.getTaskService()
		.complete(taskid);//完成任务ID
		System.out.println("完成任务ID:"+taskid);
	}
}
