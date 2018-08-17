/**Mini Project 3 Songs Class contains methods for setting and getting song information
@author Krista Delap*/

public class Songs
{

    //Stock variables
    public String title;
    public String album;
    public String artist;
    public String description;
    public String price;
    public String code;
    
    /**@param title song title
    @param album album name
    @param price item price
    @param artist song artist
    @param description song description
    @param code song code*/
    public void setSong(String title, String album, String price, String code, 
    String artist, String description)
    {
       this.title = title;
       this.album = album;
       this.price = price;
       this.code = code;
       this.artist = artist;
       this.description = description;
    }
    
    /**Overrids toString method for Songs Class*/
    public String toString()
    {
        return this.title;
    }
}