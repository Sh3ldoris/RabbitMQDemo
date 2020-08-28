package sk.posam.rabbitMQ.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sk.posam.rabbitMQ.Service.IClientService;

import javax.jws.WebService;

@WebService(
        portName = "PeopleServiceSoap",
        targetNamespace = "http://posam.sk/rabbitMq",
        endpointInterface= "sk.posam.rabbitMQ.Controller.IClientController"
)
public class ClientControllerImp implements IClientController {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientControllerImp.class);

    @Autowired
    private IClientService clientService;

    @Override
    public void findAndGenRecord(String identified) {
        clientService.genRecord(identified);
    }
}
