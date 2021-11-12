package com.br.teste.localstack.sqs_integration.sqs;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

/***
 * Responsável por Operações de criação SQS
 * @author VICTOR HUGO PONGOLINO
 */
public class QueueCriacao {
	
	private static Logger logger = LogManager.getLogger(QueueLeitor.class);
	
	/***
	 * Cria uma FILA SQS
	 * @param sqsClient um Objeto SQS Client
	 * @param queueName nome para a fila
	 */
	public static void criar (SqsClient sqsClient, String queueName) {

        try {
            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();

            sqsClient.createQueue(createQueueRequest);
            
        } catch (SqsException e) {
        	logger.error("Não foi possível criar uma fila SQS !", e);
        	throw e;
        }
    }
	
	public static String pegarURI(SqsClient sqsClient, String queueName) {
		GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
        String queueUrl = getQueueUrlResponse.queueUrl();
        return queueUrl;
	}
}
