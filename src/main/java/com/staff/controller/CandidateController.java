package com.staff.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.staff.dao.CandidateDao;
import com.staff.model.Attachment;
import com.staff.model.Candidate;
import com.staff.model.Candidate_;
import com.staff.modelDto.CandidateDto;
import com.staff.util.filtering.CandidateFilter;
//import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.ContentDisposition;
//import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
//import javax.ws.rs.FormParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CandidateController {
    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private ModelMapper modelMapper;

    public void setCandidateDao(CandidateDao candidateDao){
        this.candidateDao = candidateDao;
    }

    private final Logger logger = LoggerFactory.getLogger(CandidateController.class);

    @PostMapping(value = "/candidate", consumes="application/json;charset=UTF-8" )
    public ResponseEntity<CandidateDto> save(@RequestBody CandidateDto candidateDto)  throws ParseException {
        Long id = candidateDao.saveOrUpdate( convertToEntity(candidateDto) );
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            return ResponseEntity.ok().body(convertToDto(candidate));
        }
    }

    @GetMapping(value = "/candidate/{id}", produces="application/json;charset=UTF-8")
    public ResponseEntity<CandidateDto> get(@PathVariable("id") Long id) {
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(convertToDto(candidate));
        }
    }

    @GetMapping("/candidate")
    public List<CandidateDto> list(
                                           @RequestParam(value = "name", defaultValue = "")String name,
                                           @RequestParam(value = "surname", defaultValue = "")String surname,
                                           @RequestParam(value = "birthdayFrom", defaultValue = "")String birthdayFrom,
                                           @RequestParam(value = "birthdayTo", defaultValue = "")String birthdayTo,
                                           @RequestParam(value = "salaryFrom", defaultValue = "")String salaryFrom,
                                           @RequestParam(value = "salaryTo", defaultValue = "")String salaryTo,
                                           @RequestParam(value = "listCandidateStates", defaultValue = "")List<Candidate.CandidateState> listCandidateStates,
                                           @RequestParam(value = "page", defaultValue = "1")String page,
                                           @RequestParam(value = "pageSize", defaultValue = "10")String pageSize,
                                           @RequestParam(value = "sortColumnName", defaultValue = Candidate_.ID)String sortColumnName,
                                           @RequestParam(value = "order", defaultValue = "ASC")String order
    ) {
        CandidateFilter candidateFilter = new CandidateFilter();
        candidateFilter.setSalaryFrom(salaryFrom);
        candidateFilter.setSalaryTo(salaryTo);
        candidateFilter.setBirthdayFrom(birthdayFrom);
        candidateFilter.setBirthdayTo(birthdayTo);
        candidateFilter.setName(name);
        candidateFilter.setSurname(surname);
        candidateFilter.setPage(page != null && !"".equals(page) ? Integer.parseInt(page) : 1);
        candidateFilter.setPagesize(pageSize != null && !"".equals(pageSize) ? Integer.parseInt(pageSize) : 10);
        candidateFilter.setSortColumnName(sortColumnName);
        candidateFilter.setOrder(order);
        candidateFilter.setCandidateStates(listCandidateStates);
        List<Candidate> candidates = candidateDao.list(candidateFilter);
        return candidates.stream()
                .map(candidate -> convertToDto(candidate))
                .collect(Collectors.toList());
    }

    @PutMapping(value = "/candidate/{id}", consumes="application/json;charset=UTF-8")
    public ResponseEntity<CandidateDto> update(@PathVariable("id") Long id, @RequestBody CandidateDto candidateDto) throws ParseException {
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            candidateDto.setId(id);
            //candidateDao.update(id, convertToEntity(candidateDto));
            candidateDao.saveOrUpdate(convertToEntity(candidateDto));
            candidate = candidateDao.get(id);
            return ResponseEntity.ok().body(convertToDto(candidate));
        }
    }

    @DeleteMapping("/candidate/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            candidateDao.delete(id);
            return ResponseEntity.ok().body(null);
        }
    }

    @PostMapping(value = "/candidate/{id}/attachment" )
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({ MediaType.APPLICATION_JSON })
    public ResponseEntity<String> saveAttachment(@PathVariable("id") Long candidateId,
                                                 @DefaultValue ("CV") @FormDataParam("attachmentType") String attachmentType,
                                                 @FormDataParam("file") FormDataBodyPart content,
                                                 @FormDataParam("file") InputStream uploadedInputStream
                                                 //@FormDataParam("file") FormDataContentDisposition fileDetail
                                                  ) throws IOException {
        logger.debug("Starting saveAttachment() with attachmentType:" + attachmentType + " and candidateId: " + candidateId.toString());
        Candidate candidate = candidateDao.get(candidateId);
        if(candidate == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found!");

        if (uploadedInputStream == null )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty file!");
        //if (content == null)
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid file!");
        //content.getContentDisposition()
        ContentDisposition fileDetail = content.getFormDataContentDisposition();
        StringBuilder fileName = new StringBuilder();
        fileName.append( attachmentType + "Candidate" + candidateId.toString() );

        if (fileDetail != null)
            fileName.append(fileDetail.getFileName());

        //ContentDisposition fileDetail = content.getValueAs(FormDataContentDisposition.class);
        //if (fileDetail == null)
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fileDetail is NULL!");
        //logger.debug("fileName=" + fileDetail.getFileName() + " size=" + fileDetail.getSize());
        Attachment attachment = new Attachment();
        attachment.setfilePath( fileName.toString() );
        attachment.setAttachmentType(attachmentType);
        candidateDao.saveAttachment(candidateId, attachment);
        //logger.debug("candidateDao.saveAttachment() done! file: " + fileDetail.getFileName());

        logger.debug("Starting store file to S3...");
        AWSCredentials credentials = new BasicAWSCredentials( Attachment.S3_ACCESS_KEY, Attachment.S3_SECRET_KEY);

        byte[] byteBuffer = new byte[Attachment.FIXED_BUFFER_SIZE];

        try {
            AmazonS3 s3client = new AmazonS3Client(credentials);

            int bytesRead = 0;
            while ((bytesRead = uploadedInputStream.read(byteBuffer)) != -1) {
                InputStream toWriteToS3 = new ByteArrayInputStream(byteBuffer);
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentLength( bytesRead );
                s3client.putObject(new PutObjectRequest(Attachment.BUCKET_NAME, attachment.getFilePath(), toWriteToS3, meta)
                        //.withCannedAcl(CannedAccessControlList.PublicRead)
                );
                toWriteToS3.close();
            }
            // Upload a file as a new object with ContentType and title specified.
            //PutObjectRequest request = new PutObjectRequest(Attachment.BUCKET_NAME, attachment.getFilePath(), new File(attachment.getFilePath())  );
            //ObjectMetadata metadata = new ObjectMetadata();
            //metadata.setContentType( "plain/text" );
            //metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            //request.setMetadata(metadata);
            //s3Client.putObject(request);
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
        } finally {
            uploadedInputStream.close();
        }
        logger.debug("File to S3 saved!");
        return ResponseEntity.ok().body("Attachment saved!");
    }

    @GetMapping(value = "/candidate/{id}/attachment/all", produces="application/json;charset=UTF-8")
    public ResponseEntity<List<Attachment>> getAttachment(@PathVariable("id") Long candidateId) {
        Candidate candidate = candidateDao.get(candidateId);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(candidate.getAttachmentList());
        }
    }

    @GetMapping(value = "/candidate/{id}/attachment", produces="application/json;charset=UTF-8")
    public ResponseEntity<String> getAttachment(@PathVariable("id") Long candidateId,
                                      @RequestParam(value = "attachType", defaultValue = "CV")String at) {

        Candidate candidate = candidateDao.get(candidateId);
        try {
            String textReturned = candidateDao.getAttachemnt(candidateId, Attachment.AttachmentType.valueOf(at));
            if (candidate == null || textReturned == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate or File not Found!");
            } else {
                return ResponseEntity.ok().body(textReturned);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    private CandidateDto convertToDto(Candidate candidate) {
        CandidateDto candidateDto = modelMapper.map(candidate, CandidateDto.class);
        candidateDto.setCandidateStateAsString(candidate.getCandidateState().getDescription());
        return candidateDto;
    }

    private Candidate convertToEntity(CandidateDto candidateDto) {
        Candidate candidate = modelMapper.map(candidateDto, Candidate.class);
        candidate.setCandidateState(candidateDto.getCandidateStateAsString());
        return candidate;
    }
}
