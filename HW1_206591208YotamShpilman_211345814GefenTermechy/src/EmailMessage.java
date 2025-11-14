import java.util.ArrayList;
import java.util.Date;

public class EmailMessage extends Message implements IDigital
{
    private String subject;
    private ArrayList<File> attachments;

    public String  getSubject()
    {
        return subject;
    }
    public void setSubject(String subject)
    {
        if(subject == null || subject.isEmpty())
            {
            throw new IllegalArgumentException("subject cannot be null or empty");
          }
        this.subject = subject;
    }

    public ArrayList<File> getAttachments()
    {
        return attachments;
    }

    public void setAttachments(ArrayList<File> attachments)
    {
        this.attachments = attachments;
    }

    public EmailMessage(String sender, String content, Date sendDate, boolean isRead,String subject,ArrayList<File> attachments)
    {
        super (sender,content,sendDate,isRead);
        setSubject(subject);
        setAttachments(attachments);
    }

    public EmailMessage(String sender, String content, boolean isRead, String subject)
    {
        this(sender, content, new Date(), isRead, subject, new ArrayList<File>());
    }
    @Override
    public String toString() {
        return super.toString() + ", subject='" + subject + '\'' + ", attachments=" + attachments + '}';
    }

    @Override
    public String printCommunicationMethod() {
        return "Sent via Email Server";
    }

    @Override
    public String generatePreview() {
        return "[Email] Subject: " + subject + " | From: " + getSender();
    }

    public void addAttachment(File Afile) {
        if (Afile == null)
        {
            throw new IllegalArgumentException("file cannot be null");
        }
        this.attachments.add(Afile);
    }

    public void removeAttachment(File rfile) throws AttachmentException {
        if (rfile == null) {
            throw new IllegalArgumentException("The email does not contain any files");
        }

        if(this.attachments == null || this.attachments.isEmpty())
            throw new AttachmentException("The email does not contain any files");

        boolean removed = this.attachments.remove(rfile);

        if (!removed)
        {
            throw new AttachmentException("File not found: " + rfile.getFileName());
        }

        else
            System.out.println("Removed file: " + rfile.getFileName());
    }

}
