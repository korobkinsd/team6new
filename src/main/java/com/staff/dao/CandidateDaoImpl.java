package com.staff.dao;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.staff.metamodel.Candidate_;
import com.staff.model.Attachment;
import com.staff.model.Candidate;
import com.staff.model.ContactDetails;
import com.staff.util.filtering.CandidateFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CandidateDaoImpl implements CandidateDao {

    @Autowired
    private SessionFactory sessionFactory;

    private final Logger logger = LoggerFactory.getLogger(CandidateDaoImpl.class);

    @Override
    public Candidate get(Long id) {
        Candidate candidate = sessionFactory.getCurrentSession().get(Candidate.class, id);
        int attachmentSize = candidate.getAttachmentList().size();
        logger.debug("AttachmentSize=" + attachmentSize);
        return candidate;
    }

    @Override
    public List<Candidate> list(CandidateFilter filter) {
        //logger.debug("CandidateDaoImpl.list() start filter: " + filter.toString());
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> root = cq.from(Candidate.class);
        cq.select(root);
        List<Predicate> predicates = new ArrayList<>();

        String filterName = filter.getName();
        //logger.debug("CandidateDaoImpl.list() filterName: " + filterName);
        if (filterName!=null && !"".equals(filterName) ) {
            predicates.add( cb.like( cb.lower( root.<String>get( Candidate_.NAME )),"%"+filterName.toLowerCase()+"%" ) );
        }
        cq.where(
                cb.and(
                        predicates.toArray(new Predicate[predicates.size()])

                ));
        if (filter.getOrder().toUpperCase().equals("ASC")) {
            cq.orderBy(cb.asc(root.get(filter.getSortColumnName())));
        } else {
            cq.orderBy(cb.desc(root.get(filter.getSortColumnName())));
        }
        Query<Candidate> query = session.createQuery(cq);
        query.setFirstResult((filter.getPage()-1) * filter.getPagesize());
        query.setMaxResults(filter.getPagesize());
        //logger.debug("CandidateDaoImpl.list() done");
        return query.getResultList();
    }

    @Override
    @Transactional
    public Long saveOrUpdate(Candidate candidate) {
        Session session = sessionFactory.getCurrentSession();
        Long id = candidate.getId();
        Candidate candidateForUpdate;
        List<ContactDetails> contactDetailsList = candidate.getContactDetailsList();
        if (id == null) {
            // add new
            candidate.setContactDetailsList( new ArrayList<ContactDetails>());
            session.save(candidate);
            id = candidate.getId();
            candidateForUpdate = session.get(Candidate.class, id);
        } else {
            //update old
            candidateForUpdate = session.get(Candidate.class,id);
            candidateForUpdate.setName(candidate.getName());
            candidateForUpdate.setSurname(candidate.getSurname());
            candidateForUpdate.setSalary(candidate.getSalary());
            candidateForUpdate.setBirthday(candidate.getBirthday());
            candidateForUpdate.setCandidateState(candidate.getCandidateState());
            for ( ContactDetails contactDetailsOne : candidateForUpdate.getContactDetailsList() ) {
                contactDetailsOne.setCandidate(null);   // remove reference to candidate
                session.delete(contactDetailsOne);     // deletes child from List
            }
            candidateForUpdate.getContactDetailsList().clear();  //
        }
        for ( ContactDetails contactDetailsOne : contactDetailsList) {
            contactDetailsOne.setCandidate(candidateForUpdate); // add reference to candidate
            candidateForUpdate.getContactDetailsList().add(contactDetailsOne);
        }
        session.flush();
        return id;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Candidate delObj = session.byId(Candidate.class).load(id);
        session.delete(delObj);
        session.flush();
    }

    @Override
    @Transactional
    public String getAttachemnt(Long candidateId, Attachment.AttachmentType attachmentType) throws IOException {
        Session session = sessionFactory.getCurrentSession();
        Candidate candidate = session.get(Candidate.class, candidateId);
        List<Attachment> attachments = candidate.getAttachmentList();
        //File file = null;
        for (Attachment attachment : attachments) {
            if (attachmentType.equals( attachment.getAttachmentType()) ) {
                S3Object fileReturned = null;

                try {
                    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                            .withRegion(Attachment.CLIENT_REGION)
                            .withCredentials(new ProfileCredentialsProvider())
                            .build();
                    fileReturned = s3Client.getObject(new GetObjectRequest(Attachment.BUCKET_NAME, attachment.getFilePath()));
                    logger.debug( "Content-Type: " + fileReturned.getObjectMetadata().getContentType());
                    InputStream input = fileReturned.getObjectContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String line = null;
                    StringBuffer msg = new StringBuffer(10000 );
                    while ((line = reader.readLine()) != null) {
                        msg.append(line);
                    }
                    return msg.toString();
                }
                catch(AmazonServiceException e) {
                    // The call was transmitted successfully, but Amazon S3 couldn't process
                    // it, so it returned an error response.
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
                catch(SdkClientException e) {
                    // Amazon S3 couldn't be contacted for a response, or the client
                    // couldn't parse the response from Amazon S3.
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
                finally {
                    // To ensure that the network connection doesn't remain open, close any open input streams.
                    if(fileReturned != null) {
                        fileReturned.close();
                    }
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void saveAttachment(Long candidateId, Attachment attachment) {
        Session session = sessionFactory.getCurrentSession();
        Candidate candidate = session.get(Candidate.class, candidateId);
        attachment.setCandidate(candidate);



        //candidate.getAttachmentList().add(attachment);
        /*List<Attachment> attachments = candidate.getAttachmentList();
        Iterator<Attachment> iter = attachments.iterator();
        while (iter.hasNext()) {
            Attachment attachmentOld = iter.next();
            if (attachmentOld.getAttachmentType().equals(attachment.getAttachmentType())) {
                attachmentOld.setCandidate(null);
                iter.remove();
                //TODO: remove file from s3!
            }
        }*/
        //attachments.add(attachment);
        //session.update(attachments);

        //candidate = session.get(Candidate.class, candidateId);
        candidate.getAttachmentList().add(attachment);
        //attachments.add(attachment);
        //candidate.getAttachmentList().clear();
        //candidate.setAttachmentList(attachments);
        //session.(candidate);
        session.flush();
    }

    /*@Override
    @Transactional
    void deleteAttachment(Long candidateId, Attachment.AttachmentType attachmentType){

    }*/


}
