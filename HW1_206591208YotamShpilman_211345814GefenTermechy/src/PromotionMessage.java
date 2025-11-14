import java.util.Date;

public class PromotionMessage extends Message implements IDigital
{
    private int discount;
    private Date expiryDate;


    public PromotionMessage(String sender, String content, Date sendDate, boolean isRead, int discount, Date expiryDate) throws PromotionException
    {
        super(sender, content, sendDate, isRead);
        setDiscount(discount);
        setExpiryDate(expiryDate);
    }


    public PromotionMessage(String sender, String content, boolean isRead, int discount, Date expiryDate) throws PromotionException
    {
        this(sender, content, new Date(), isRead, discount, expiryDate);
    }


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) throws PromotionException
    {
        if (discount < 0 || discount > 100)
        {
            throw new PromotionException("Discount must be between 0 and 100.");
        }
        this.discount = discount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) throws PromotionException
    {
        if (expiryDate == null || expiryDate.before(new Date()))
        {
            throw new PromotionException("Expiry date must be in the future.");
        }
        this.expiryDate = expiryDate;
    }

    @Override
    public String generatePreview()
    {
        String shortContent;

        if (getContent().length() > 15) {
            shortContent = getContent().substring(0, 15) + "...";
        } else {
            shortContent = getContent();
        }

        return "[Promotion] " + getSender() + ": " + shortContent;
    }

    @Override
    public String printCommunicationMethod()
    {
        return "Digital promotion message via email or SMS";
    }

    @Override
    public String toString()
    {
        return super.toString() +
                " | Discount: " + discount + "%" +
                " | Expiry Date: " + expiryDate;
    }
}