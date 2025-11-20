import java.util.Date;
enum PriorityType
{
    REGULAR,
    URGENT,
    IMPORTANT,
}

public class BoardMessage extends Message {

    private PriorityType priority;
    private int viewsCount;

    public BoardMessage(String sender, String content, Date sendDate, boolean isRead, PriorityType priority, int viewsCount)
    {
        super(sender, content, sendDate, isRead); // מפעיל את הבנאי של המחלקה האב
        setPriority(priority);
        setViewsCount(viewsCount);
    }

    public PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        if (viewsCount < 0) {
            throw new IllegalArgumentException("viewsCount cannot be negative");
        }
        this.viewsCount = viewsCount;
    }

    @Override
    public String generatePreview() {
        String previewContent = getContent();
        if (previewContent.length() > 15) {
            previewContent = previewContent.substring(0, 15) + "...";
        }
        return "[Board] " + getSender() + ": " + previewContent;
    }

    public BoardMessage(String sender, String content, boolean isRead, int viewsCount)
    {
        this(sender, content, new Date(), isRead, PriorityType.REGULAR, viewsCount);
    }

    public void incrementViewsCount() {
        viewsCount ++;
    }

    @Override
   public String toString()
    {
        return super.toString() +",priority=" + getPriority() + ",viewsCount=" + getViewsCount();
    }
}

