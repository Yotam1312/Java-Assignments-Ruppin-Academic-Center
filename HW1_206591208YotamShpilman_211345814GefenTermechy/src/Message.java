import java.util.Date;
import java.util.ArrayList;

public abstract class Message
{
    private String sender;
    private String content;
    private Date sendDate;
    public boolean isRead;

    public Message(String sender, String content, Date sendDate,boolean isRead)
    {
        setSender(sender);
        setContent(content);
        setSendDate(sendDate);
        setRead(isRead);
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        if(sender == null || sender.isEmpty())
        {
            throw new IllegalArgumentException("sender cannot be null or empty");
        }
        this.sender = sender;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        if(content == null || content.isEmpty())
        {
            throw new IllegalArgumentException("content cannot be null or empty");
        }
        this.content = content;
    }

    public Date getSendDate()
    {
        return sendDate;
    }

    public void setSendDate(Date sendDate)
    {
        if(sendDate == null)
        {
            throw new IllegalArgumentException("sendDate cannot be null");
        }
        this.sendDate = sendDate;
    }

    public boolean isRead()
    {
        return isRead;
    }

    public void setRead(boolean read)
    {
        this.isRead = read;
    }

    public Message(String sender, String content,boolean isRead)
    {
        setSender(sender);
        setContent(content);
        setRead(isRead);
    }

    public boolean find(ArrayList<String> words)
    {
        if (words == null || content == null)
        {
            return false;
        }

        for (String word : words)
        {
            if (word != null && content.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public void markAsRead()
    {
        this.isRead = true;
    }

    public abstract String generatePreview();

    @Override
    public String toString()
    {
        return "Message[" + "sender :'" + sender + '\'' + ", content : '" + content + '\'' + ", sendDate :" + sendDate + ", isRead? : " + isRead + ']';
    }

}