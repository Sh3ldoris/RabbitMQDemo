package sk.posam.rabbitMQ.Controller;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        targetNamespace = "http://posam.sk/rabbitMQ"
)
public interface IClientController {

    @WebMethod( operationName = "generate")
    void findAndGenRecord(String identified);
}
