package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;
import com.archosResearch.jCHEKS.engine.mock.ObserverMock;
import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ModelTest {
    
    private final ContactInfo aliceContactInfo = new ContactInfo("10.10.10.10", 9000, "Alice", "sysId");
    
    @Test
    public void constructor_should_create_the_model(){
        AbstractModel model = null;
        model = new Model();
        assertNotNull(model);
    }

    @Test
    public void addContact_should_notify_observer_that_a_contact_has_been_added() throws ContactAlreadyExistException{
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator());
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addObserver(observer);
        model.addContact(contact);
        assertEquals(contact.getContactInfo().getName(), observer.lastContactAdded);
    }

    @Test (expected = ContactAlreadyExistException.class)
    public void addContact_should_throw_an_exception_if_contact_already_exist() throws ContactAlreadyExistException{
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator());
        Model model = new Model();
        model.addContact(contact);
        model.addContact(contact);
    }
    
    @Test
    public void addOutgoingMessage_should_notify_observer_that_a_message_has_been_sent() throws ContactAlreadyExistException {
        String messageContent = "Hello";
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addContact(new Contact(aliceContactInfo, new StubCommunicator()));
        model.addObserver(observer);
        model.addOutgoingMessage(messageContent, aliceContactInfo.getName());
        assertEquals(messageContent, observer.lastMessageSent.getContent());
    }
    
    @Test
    public void addIncomingMessage_should_notify_observer_that_a_message_has_been_received() throws ContactAlreadyExistException {
        String messageContent = "Hello";
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator());
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addContact(contact);
        model.addObserver(observer);
        model.addIncomingMessage(messageContent, contact);
        assertEquals(messageContent, observer.lastMessageReceived.getContent());
    }
    
}