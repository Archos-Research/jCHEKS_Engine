package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.engine.mock.ModelExceptionThrower;
import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunication;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.ioManager.InputOutputManager;
import com.archosResearch.jCHEKS.engine.mock.StubIOManager;
import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.engine.mock.StubModel;
import com.archosResearch.jCHEKS.engine.model.AbstractModel;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class EngineTest {
    
    //Add assert when it will be possible.

    @Test
    public void constructor_should_construct_the_engine() throws ContactAlreadyExistException {
        Engine engine = null;
        StubCommunicator communicator = new StubCommunicator();
        StubModel model = new StubModel();
        StubIOManager ioManager = new StubIOManager();
        engine = new Engine(communicator, model, ioManager, "Alice");
        assertNotNull(engine);
    }
    
    @Test
    public void testAckReceived() throws ContactAlreadyExistException {
        StubCommunicator communicator = new StubCommunicator();
        StubModel model = new StubModel();
        StubIOManager ioManager = new StubIOManager();
        Engine engine = new Engine(communicator, model, ioManager, "Alice");
        engine.ackReceived();
    }

    @Test
    public void communicationReceived_should_not_cause_any_error_with_stub() throws ContactAlreadyExistException {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new StubModel();
        InputOutputManager ioManager = new StubIOManager();
        Engine engine = new Engine(communicator, model, ioManager, "Alice");
        AbstractCommunication communication = new Communication("cipher","cipherCheck","sysId");
        engine.communicationReceived(communication);
    }
    
    @Test
    public void communicationReceived_should_catch_AddIncomingMessageException_if_thrown() throws ContactAlreadyExistException {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new ModelExceptionThrower();
        InputOutputManager ioManager = new StubIOManager();
        Engine engine = new Engine(communicator, model, ioManager, "Alice");
        AbstractCommunication communication = new Communication("cipher","cipherCheck","sysId");
        engine.communicationReceived(communication);
    }

    @Test
    public void handleOutgoingMessage_should_not_cause_any_error_with_stub() throws ContactAlreadyExistException {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new StubModel();
        InputOutputManager ioManager = new StubIOManager();
        Engine engine = new Engine(communicator, model, ioManager, "Alice");
        engine.handleOutgoingMessage("Message content", "Alice");
    }
    
    @Test
    public void handleOutgoingMessage_should_catch_Exceptions() throws ContactAlreadyExistException {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new ModelExceptionThrower();
        InputOutputManager ioManager = new StubIOManager();
        Engine engine = new Engine(communicator, model, ioManager, "Alice");
        engine.handleOutgoingMessage("Message content", "Alice");
    }
    
}