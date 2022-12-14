package Model;


import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class representing a player
 * @author Anna Andrlova, Christos Artemisios, Alex Bolfa, Jan Metela
 */
public class Player
{
  private int ID;

  private String name;
  private String phoneNumber;
  private String email;
  private boolean membership;
  private String comment;
  private String address;
  private Boolean voted;

  private LocalDate feePaymentDate;

  /**
   * The nine-arguments constructor calling the set method
   * @param ID the ID of the player
   * @param name the name of the player
   * @param phoneNumber the phone number of the player
   * @param email the email of the player
   * @param membership whether the player is a member or not
   * @param comment a custom comment
   * @param address the address of the player
   * @param voted whether the player has voted in the election or not
   * @param feePaymentDate the date until when the player must pay his member fee
   */
  public Player(int ID, String name, String phoneNumber, String email, boolean membership,
      String comment, String address, Boolean voted, LocalDate feePaymentDate)
  {
    set(ID, name, phoneNumber, email, membership, comment, address , voted, feePaymentDate);
  }
  /**
   * The eight-arguments constructor calling the set method. ID is set to -1.
   * @param name the name of the player
   * @param phoneNumber the phone number of the player
   * @param email the email of the player
   * @param membership whether the player is a member or not
   * @param comment a custom comment
   * @param address the address of the player
   * @param voted whether the player has voted in the election or not
   * @param feePaymentDate the date until when the player must pay his member fee
   */
  public Player(String name, String phoneNumber, String email, boolean membership,
      String comment, String address, Boolean voted, LocalDate feePaymentDate)
  {
    int ID = -1;
    set(ID,name, phoneNumber, email, membership, comment, address , voted, feePaymentDate);
  }

  /**
   * The setter for every attribute
   * @param ID the ID of the player
   * @param name the name of the player
   * @param phoneNumber the phone number of the player
   * @param email the email of the player
   * @param membership whether the player is a member or not
   * @param comment a custom comment
   * @param address the address of the player
   * @param voted whether the player has voted in the election or not
   * @param feePaymentDate the date until when the player must pay his member fee
   */

  public void set(int ID,String name, String phoneNumber, String email,
      boolean membership, String comment, String address,
      Boolean voted, LocalDate feePaymentDate)
  {
    name.trim();
    phoneNumber.trim();
    email.trim();
    comment.trim();
    address.trim();
    this.ID = ID;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.membership = membership;
    this.comment = comment;
    this.address = address;
    this.voted = voted;
    this.feePaymentDate = feePaymentDate;
  }

  /**
   *
   * @param name the name of the player
   * @throws RuntimeException if name is an empty string
   * @return true, if name is not an empty string
   */
  static public boolean VALIDATE_NAME(String name)
  {
    name = name.trim();
    if (name.length() > 0)
    {
      return true;
    }
    throw new RuntimeException("Name is not valid");
  }

  /**
   * Checks whether given phoneNumber meets those conditions:
   * phoneNumber contains only numbers and/or "+"
   * phoneNumber is not an empty string
   * if phoneNumber contains "+" it is the first character of the string
   * @param phoneNumber the phone number of the player
   * @throws RuntimeException if phoneNumber does not meet any of the conditions
   * @return true if phoneNumber meets all the conditions
   *
   */
  static public boolean VALIDATE_PHONE_NUMBER(String phoneNumber)
  {
    phoneNumber = phoneNumber.trim();
    if(phoneNumber.length() == 0){
      throw new RuntimeException("Phone number is not valid");
    }
    int amountOfPluses = phoneNumber.length() - phoneNumber.replace("+", "").length();
    if (amountOfPluses > 1){
      throw new RuntimeException("Phone number is not valid");
    }
    for (int i = 0; i < phoneNumber.length(); i++)
    {
      //If character on position is not a + or number, false is returned
      if (Character.compare(phoneNumber.charAt(i), ('+')) != 0
          && !Character.isDigit(phoneNumber.charAt(i)))
      {
        throw new RuntimeException("Phone number is not valid");
      }
    }
    if (phoneNumber.contains("+")
        && Character.compare(phoneNumber.charAt(0), ('+')) != 0)
    {
      throw new RuntimeException("Phone number is not valid");
    }
    return true;
  }

  /**
   *
   * @param email the email of the player
   * @throws RuntimeException if is not in format simple@example.com
   * @return true if email respects the format
   */
  static public boolean VALIDATE_EMAIL(String email)
  {
    email = email.trim();
    if (email.equals("")){
      return true;
    }

    int amountOfAts = email.length() - email.replace("@", "").length();

    if (!email.contains(".") || amountOfAts != 1)
    {
      throw new RuntimeException("Email is not valid");
    }
    if (email.indexOf('@') == 0 || email.lastIndexOf('.') == email.length() - 1)
    {
      throw new RuntimeException("Email is not valid");
    }

    if (email.lastIndexOf(".") <= email.indexOf('@') + 1)
    {
      throw new RuntimeException("Email is not valid");
    }
    String subString;
    subString = email.substring(email.indexOf('@'));
    if ((subString.length() - subString.replace(".", "").length()) != 1)
    {
      throw new RuntimeException("Email is not valid");
    }
    return true;
  }

  /**
   * Validates the inputted data by passing them to methods VALIDATE_NAME, VALIDATE EMAIL and VALIDATE_PHONE_NUMBER
   *
   * @param name the name of the player
   * @param phoneNumber the phone number of the player
   * @param email the email of the player
   * @return true, if the data is valid
   */
  static public boolean VALIDATE_DATA(String name, String phoneNumber,
      String email)
  {
    return VALIDATE_NAME(name) && VALIDATE_PHONE_NUMBER(phoneNumber)
        && VALIDATE_EMAIL(email);
  }

  /**
   * The getter for ID
   */
  public int getID()
  {
    return ID;
  }
  /**
   * The setter for ID
   */
  public void setID(int ID)
  {
    this.ID = ID;
  }
  /**
   * The getter for name
   */
  public String getName()
  {
    return name;
  }
  /**
   * The setter for name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * The getter for phoneNumber
   */
  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /**
   * The getter for Email
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * The getter for membership
   */

  public boolean isMembership()
  {
    return membership;
  }
  /**
   * The getter for comment
   */
  public String getComment()
  {
    return comment;
  }
  /**
   * The getter for address
   */
  public String getAddress()
  {
    return address;
  }
  /**
   * The getter for voted
   */
  public Boolean getVoted()
  {
    return voted;
  }
  /**
   * The setter for voted
   */
  public void setVoted(Boolean voted)
  {
    this.voted = voted;
  }
  /**
   * The getter for feePaymentDate
   */
  public LocalDate getFeePaymentDate()
  {
    return feePaymentDate;
  }

  /**
   * The setter for feePaymentDay
   */
  public void setFeePaymentDate(LocalDate feePaymentDate)
  {
    this.feePaymentDate = feePaymentDate;
  }

  /**
   * The setter for phoneNumber
   */
  public void setPhoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
  }
  /**
   * The setter for email
   */
  public void setEmail(String email)
  {
    this.email = email;
  }
  /**
   * The setter for membership
   */
  public void setMembership(boolean membership)
  {
    this.membership = membership;
  }
  /**
   * The setter for comment
   */
  public void setComment(String comment)
  {
    this.comment = comment;
  }
  /**
   * The setter for address
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * @return the value of all attributes as string
   */
  @Override public String toString()
  {
    return "Model.Player{" + "ID=" + ID + ", name='" + name + '\'' + ", phoneNumber='"
        + phoneNumber + '\'' + ", email='" + email + '\'' + ", membership="
        + membership +
        ", comment='" + comment + '\''
        + ", address='" + address + '\'' + ", voted=" + voted
        + ", feePaymentDate=" + feePaymentDate + '}';
  }

}
