package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;
import com.archosResearch.jCHEKS.engine.mock.*;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ModelTest {
    
    private final ContactInfo aliceContactInfo = new ContactInfo("10.10.10.10", 9000, "Alice", "path1", "path2");
    private final ContactInfo bobContactInfo = new ContactInfo("9.9.9.9", 9002, "Bob", "path1", "path2");

    @Test
    public void constructor_should_create_the_model(){
        AbstractModel model = null;
        model = new Model();
        assertNotNull(model);
    }

    @Test
    public void addContact_should_notify_observer_that_a_contact_has_been_added() throws ContactAlreadyExistException, Exception{
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addObserver(observer);
        model.addContact(contact);
        assertEquals(contact.getContactInfo().getName(), observer.lastContactAdded);
    }

    @Test (expected = ContactAlreadyExistException.class)
    public void addContact_should_throw_an_exception_if_contact_already_exist() throws ContactAlreadyExistException, Exception{
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        Model model = new Model();
        model.addContact(contact);
        model.addContact(contact);
    }
    
    @Test
    public void addOutgoingMessage_should_notify_observer_that_a_message_has_been_sent() throws ContactAlreadyExistException, Exception {
        String messageContent = "Hello";
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addContact(new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem()));
        model.addObserver(observer);
        model.addOutgoingMessage(messageContent, aliceContactInfo.getName());
        assertEquals(messageContent, observer.lastMessageSent.getContent());
    }
    
    @Test
    public void addIncomingMessage_should_notify_observer_that_a_message_has_been_received() throws ContactAlreadyExistException, Exception {
        String messageContent = "Hello";
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addContact(contact);
        model.addObserver(observer);
        model.addIncomingMessage(messageContent, contact);
        assertEquals(messageContent, observer.lastMessageReceived.getContent());
    }
    
    @Test
    public void getLastOutgoingMessageBySystemId_should_return_the_last_message() throws ContactNotFoundException, ContactAlreadyExistException, Exception {
        String messageContent = "Hello";
        String messageContent2 = "Hello number 2";
        Model model = new Model();
        StubChaoticSystem sys = new StubChaoticSystem();
        model.addContact(new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), sys, new StubChaoticSystem()));
        model.addOutgoingMessage(messageContent, aliceContactInfo.getName());
        model.addOutgoingMessage(messageContent2, aliceContactInfo.getName());

        assertEquals(messageContent2, model.getLastOutgoingMessageBySystemId(sys.getSystemId()).getContent());
    }
    
    @Test
    public void findContactByReceiverSystemId_should_return_the_contact() throws ContactNotFoundException, ContactAlreadyExistException, Exception {

        Model model = new Model();
        StubChaoticSystem sys = new StubChaoticSystem();
        Contact aliceContact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), sys);
        model.addContact(aliceContact);
        model.addContact(new Contact(bobContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem()));

        Contact contact = model.findContactByReceiverSystemId(sys.getSystemId());
        System.out.println(contact.getContactInfo().getName());
        assertEquals(contact, aliceContact);
    }
    
    @Test
    public void findContactByName_should_return_the_contact() throws ContactNotFoundException, ContactAlreadyExistException, Exception {

        Model model = new Model();
        Contact aliceContact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        model.addContact(aliceContact);
        model.addContact(new Contact(bobContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem()));

        Contact contact = model.findContactByName(aliceContactInfo.getName());

        assertEquals(contact, aliceContact);
    }
    
}
