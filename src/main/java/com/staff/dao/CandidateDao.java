package com.staff.dao;

import com.staff.model.Attachment;
import com.staff.model.Candidate;
import com.staff.util.filtering.CandidateFilter;

import java.io.IOException;
import java.util.List;

public interface CandidateDao {

        Candidate get(Long id);

        List<Candidate> list(CandidateFilter filter);

        Long saveOrUpdate(Candidate candidate);

        void delete(Long id);

        String getAttachemnt(Long candidateId, Attachment.AttachmentType attachmentType) throws IOException;

        void saveAttachment(Long candidateId, Attachment attachment);

        //void deleteAttachment(Long candidateId, Attachment.AttachmentType attachmentType);

}


