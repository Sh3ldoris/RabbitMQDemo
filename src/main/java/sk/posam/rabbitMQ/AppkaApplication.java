package sk.posam.rabbitMQ;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import sk.posam.rabbitMQ.Controller.ClientControllerImp;
import sk.posam.rabbitMQ.Controller.IClientController;

import javax.sql.DataSource;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

@SpringBootApplication
public class AppkaApplication {

	public final static String QUEUE_DB = "queue.DB";
	public final static String QUEUE_FS = "queue.FS";
	public final static String EXCHANGE = "RabbitMqDemoExchange";

	public static void main(String[] args) {
		SpringApplication.run(AppkaApplication.class, args);
	}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), genService(), SOAPBinding.SOAP12HTTP_BINDING);
		endpoint.publish("/rabbit");
		return endpoint;
	}

	@Bean
	public IClientController genService() {
		return new ClientControllerImp();
	}


	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}


}
