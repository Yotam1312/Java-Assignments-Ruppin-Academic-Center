public class File
{
    private String fileName;
    private String fileType;

    public File(String fileName, String fileType)
    {
        setFileName(fileName);
        setFileType(fileType);
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        if (fileName == null || fileName.isEmpty())
        {
            throw new IllegalArgumentException("File Name cannot be null or empty");
        }
        this.fileName = fileName;
    }
    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        if (fileType == null || fileType.isEmpty())
        {
            throw new IllegalArgumentException("File type cannot be null or empty");
        }
        this.fileType = fileType;
    }
    @Override
    public String toString()
    {
        return "File{" + "fileName=" + this.fileName + ", fileType=" + this.fileType + '}';
    }


}
