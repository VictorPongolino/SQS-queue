package com.br.teste.localstack.sqs_integration.teste;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.br.teste.localstack.sqs_integration.sqs.QueueCriacao;
import com.br.teste.localstack.sqs_integration.sqs.QueueLeitor;
import com.br.teste.localstack.sqs_integration.sqs.connection.ConexaoSQSFactory;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

public class Teste {
	
	private static Logger logger = LogManager.getLogger(QueueLeitor.class);
	
	private static final SqsClient client = ConexaoSQSFactory.getConnection();
	
	public static void main(String[] args) throws InterruptedException {
		logger.debug("Log4j appender configurado");
		
		String nomeQueue = "sqs";
		logger.debug("Criando Queue de nome '{}'", nomeQueue);
		QueueCriacao.criar(client, nomeQueue);
		logger.debug("Servidor aguardando mensagens SQS ...!");
		
		while (true) {
			Thread.sleep(3000);
			List<Message> conteudo = QueueLeitor.consumir(client, nomeQueue);
			conteudo.stream().forEach(System.out::println);
		}
	}

}
