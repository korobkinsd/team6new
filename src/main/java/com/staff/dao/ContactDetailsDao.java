package com.staff.dao;

import com.staff.model.ContactDetails;
import java.util.List;

public interface ContactDetailsDao {

    void save(ContactDetails contactDetails);

    ContactDetails get(ContactDetails contactDetails);

    List<ContactDetails> list(long idCandidate);

    void update(ContactDetails contactDetails);

    void delete(ContactDetails contactDetails);

}


