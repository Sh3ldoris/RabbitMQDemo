package sk.posam.rabbitMQ.Service;

import org.apache.camel.util.xml.StringSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import sk.posam.rabbitMQ.AppkaApplication;
import sk.posam.rabbitMQ.model.Person;
import sk.posam.rabbitMQ.repository.IPersonRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;
import org.apache.commons.lang3.StringEscapeUtils;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

@Service
public class ClientServiceImp implements IClientService {

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImp.class);
    private static final HtmlCompressor compressor = new HtmlCompressor();
    private static final String PERSON_XSL_FILE_REF = "src/main/resources/PersonToHTML.xsl";
    public static final String ROUTING_KEY = "queue.DB.queue.FS";

    private AmqpTemplate rabbitmqTemplate;
    private IPersonRepository personRepository;

    public ClientServiceImp(AmqpTemplate rabbitmqTemplate, IPersonRepository personRepository) {
        this.rabbitmqTemplate = rabbitmqTemplate;
        this.personRepository = personRepository;
    }

    @Override
    public void genRecord(String birthNumber) {
        String encodedRecord = "";
        Person foundedPerson = personRepository.findPerson(birthNumber);

        if (foundedPerson != null) {
            String personXML = toXML(foundedPerson);

            String htmlRecord = null;
            if (!personXML.equals("")) {
                htmlRecord = toHTML(personXML);
            }

            if (htmlRecord != null || !htmlRecord.equals("")) {
                byte[] base64Html = Base64.encodeBase64(htmlRecord.getBytes()); // Base64.encodeBase64(compressHtml(htmlRecord).getBytes());
                encodedRecord = new String(base64Html);
            }
        }

        rabbitmqTemplate.convertAndSend(AppkaApplication.EXCHANGE, ROUTING_KEY, encodedRecord);
    }

    private String toXML(Object data) {
        String xml = "";
        try {
            LOGGER.info("Generating xml for: " + data.getClass().getName());
            JAXBContext jaxbContext = JAXBContext.newInstance(data.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(data, sw);
            xml = sw.toString();
        } catch (JAXBException e) {
            LOGGER.error("Could not create XML!");
        }
        return xml;
    }

    private String toHTML(String XMLSource) {
        String html = "";
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File(PERSON_XSL_FILE_REF));
            Transformer transformer = factory.newTransformer(xslt);

            StringSource transformationSource = new StringSource(XMLSource);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            transformer.transform(transformationSource, result);

            html = writer.toString();
        } catch (TransformerConfigurationException e) {
            LOGGER.error("Could not create XML!");
            e.printStackTrace();
        } catch (TransformerException e) {
            LOGGER.error("Could not create XML!");
            e.printStackTrace();
        }
        return html;
    }

    private String compressHtml(String html) {
        html = StringEscapeUtils.escapeHtml4(html);
        compressor.setRemoveIntertagSpaces(true);
        return compressor.compress(html);
    }
}
