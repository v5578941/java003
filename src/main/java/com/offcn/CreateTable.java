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
	 * �������̱�
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
		.name("��һ������")
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
		String assgin="����";
		List<Task> tasklist = processEngine.getTaskService()
				                               .createTaskQuery()
				                               .taskAssignee(assgin)
				                               .list();
	
		if(tasklist!=null&&tasklist.size()>0){
			for(Task task:tasklist){
				System.out.println("��������ID:"+task.getId());
				System.out.println("��������:"+task.getName());
				System.out.println("���񴴽�ʱ��:"+task.getCreateTime());
				System.out.println("���������:"+task.getAssignee());
				System.out.println("����ʵ��ID:"+task.getProcessInstanceId());
				System.out.println("ִ�ж���ID:"+task.getExecutionId());
				System.out.println("���̶���ID:"+task.getProcessDefinitionId());
			}
		}
	}
	
	@Test
	public void compleTask(){
		String taskid="10002";
		processEngine.getTaskService()
		.complete(taskid);//�������ID
		System.out.println("�������ID:"+taskid);
	}
}
