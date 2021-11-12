package com.br.teste.localstack.sqs_integration.sqs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

/***
 * Responsável por operações de exclusão SQS
 * @author VICTOR HUGO PONGOLINO
 */
public class ApagarMensagensQueue {
	private static Logger logger = LogManager.getLogger(QueueLeitor.class);
	
	/***
	 * 
	 * @param sqsClient um SQS Client configurado
	 * @param queueURI URI da Fila SQS
	 * @param mensagem mensagem pura para ser apagada.
	 */
	public static void deletar (SqsClient sqsClient, String queueURI, Message mensagem) {
		logger.debug("Deletando mensagem recebida (URI {}) -> {}",queueURI, mensagem);
		DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
			.queueUrl(queueURI)
			.receiptHandle(mensagem.receiptHandle())
			.build();
		sqsClient.deleteMessage(deleteMessageRequest);
	}
}
