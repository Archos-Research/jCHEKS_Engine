package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.encrypter.AbstractEncrypter;
import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Contact {
    private ContactInfo contactInfo;
    private final AbstractCommunicator communicator;
    private final AbstractEncrypter encrypter;
    private final AbstractChaoticSystem chaoticSystem;

    public Contact(ContactInfo contactInfo, AbstractCommunicator communicator, AbstractEncrypter encrypter, AbstractChaoticSystem chaoticSystem) {
        this.contactInfo = contactInfo;
        this.communicator = communicator;
        this.encrypter = encrypter;
        this.chaoticSystem = chaoticSystem;
    }

    public ContactInfo getContactInfo() {
        return this.contactInfo;
    }

    public AbstractCommunicator getCommunicator() {
        return this.communicator;
    }
    
    public AbstractEncrypter getEncrypter() {
        return this.encrypter;
    }
    
    public AbstractChaoticSystem getChaoticSystem() {
        return this.chaoticSystem;
    }
}
