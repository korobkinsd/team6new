package com.staff.model;


import com.amazonaws.regions.Regions;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "ATTACHMENT", schema = "team6")
public class Attachment {

    public enum AttachmentType { CV, CL, PHOTO }

    public static final String CLIENT_REGION = Regions.US_EAST_2.getName();
    public static final String BUCKET_NAME = "team6new";
    public static final String S3_ACCESS_KEY = "AKIAJSR7BQE5SDXGMHRQ";
    public static final String  S3_SECRET_KEY = "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";
    public static final int FIXED_BUFFER_SIZE = 1024*1024;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="FILE_PATH", nullable = false, length = 1000)
    private String filePath;

    @NotNull(message="is required")
    @Column(name="ATTACHMENT_TYPE", nullable = false)
    private AttachmentType attachmentType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Candidate candidate;

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getAttachmentType() {
        return attachmentType.toString();
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = AttachmentType.valueOf(attachmentType);
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setfilePath(String filePath) {
        this.filePath = filePath;
    }

}
