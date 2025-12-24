import java.io.Serializable;

public class Client implements Serializable
{
    private String username;
    private String password;
    private String academicStatus;
    private int yearsAtRuppin;

    public Client(String username, String password, String academicStatus, int yearsAtRuppin)
    {
        this.username = username;
        this.password = password;
        this.academicStatus = academicStatus;
        this.yearsAtRuppin = yearsAtRuppin;
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAcademicStatus()
    {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus)
    {
        this.academicStatus = academicStatus;
    }

    public int getYearsAtRuppin()
    {
        return yearsAtRuppin;
    }

    public void setYearsAtRuppin(int yearsAtRuppin)
    {
        this.yearsAtRuppin = yearsAtRuppin;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Client) {
            Client other = (Client) obj;
            return this.username.equals(other.getUsername());
        }

        return false;
    }

    @Override
    public String toString()
    {
        return username + "," + password + "," + academicStatus + "," + yearsAtRuppin;
    }
}