package com.br.teste.localstack.sqs_integration.sqs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

/***
 * Responsável pelo acesso do conteúdo dentro de uma FILA SQS e seu consumo.
 * @author VICTOR HUGO PONGOLINO
 */
public class QueueLeitor {
	private static Logger logger = LogManager.getLogger(QueueLeitor.class);
	
	/***
	 * Obtem os conteúdos enviados na fila sem excluí-los.
	 * Use: {@code consumir(...)} se for para este propósito.
	 * @param sqsClient Um objeto SQS Client configurado.
	 * @param queueURI URI da Queue
	 * @return Lista de mensagens
	 */
	public static List<Message> obterMensagens (SqsClient sqsClient, String queueURI) {
		logger.debug("Obtendo mensagens da URI {}", queueURI); //ALERTA SOLID EM S.
		
		ReceiveMessageRequest messageRequest = ReceiveMessageRequest.builder()
				.queueUrl(queueURI)
				.build();
		return sqsClient.receiveMessage(messageRequest).messages();
	}
	
	/***
	 * Lê e apaga os conteúdos de dentro de uma fila.
	 * @return Retorna as mensagens de dentro da fila.
	 */
	public static List<Message> consumir (SqsClient sqsClient, String queueURI) {
		logger.debug("Consumindo URI {}", queueURI); 
		
		List<Message> mensagens = obterMensagens(sqsClient, queueURI);
		logger.debug("Recebido {} mensagens do SQS !", mensagens.size());
		
		mensagens.forEach(msg -> {
			logger.debug("Lendo mensagem: \n\n{}",msg);
			ApagarMensagensQueue.deletar(sqsClient, queueURI, msg);
		});
		
		logger.debug("Finalizado consumo de mensagens, retornando lista");
		
		return mensagens;
	}
	
	
}
