package com.br.teste.localstack.sqs_integration.sqs.connection;

import java.net.URI;
import java.net.URISyntaxException;

import javax.management.RuntimeErrorException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.br.teste.localstack.sqs_integration.sqs.QueueLeitor;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/***
 * Factory responsável por retornar um {@code SqsClient} com configurações já definidas.
 * @author VICTOR HUGO PONGOLINO
 */
public class ConexaoSQSFactory {
	
	private static Logger logger = LogManager.getLogger(QueueLeitor.class);
	
	/***
	 * Obtem um objeto de criação SQS ou {@ null} se um erro for gerado
	 */
	public static SqsClient getConnection() {
		SqsClient sqsClient = null;
		try {
			sqsClient = prepare();
		} catch(Exception e) {
			logger.error("Não foi possível estabelecer conexão com o SQS !", e);
		}
		
		return sqsClient;
	}
	
	private static SqsClient prepare() throws URISyntaxException{
		SqsClient sqsClient = SqsClient.builder()
				.endpointOverride(new URI("http://localhost:4566"))
				.region(Region.US_EAST_1)
				.build();
		
		return sqsClient;
	}
}
