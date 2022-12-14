package Controllers;

import Application.ViewHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import Model.ModelManager;
import Model.Event;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class EventsAddEventController implements Controller
{
  public TextField nameField;
  public TextField placeField;
  public TextField linkField;
  public DatePicker fromDateField;
  public TextField fromHoursField;
  public TextField fromMinutesField;
  public DatePicker toDateField;
  public TextField toHoursField;
  public TextField toMinutesField;
  public TextArea descriptionField;
  public TextArea commentField;
  public Button editButton;
  public Button deleteButton;
  public Button seeParticipantsButton;
  private Region region;
  private ModelManager model;
  private ViewHandler viewHandler;

  @Override public void init(Region region, ModelManager model,
      ViewHandler viewHandler, int ID)
  {
    this.region = region;
    this.model = model;
    this.viewHandler = viewHandler;
    fromDateField.setMouseTransparent(false);
    fromDateField.setFocusTraversable(true);
    fromDateField.setValue(LocalDate.now());
    fromHoursField.setText("16");
    fromMinutesField.setText("00");
    toDateField.setDisable(false);
    fromDateField.setMouseTransparent(false);
    fromDateField.setFocusTraversable(true);
    toDateField.setValue(LocalDate.now());
    toHoursField.setText("18");
    toMinutesField.setText("00");
  }

  @Override public Region getRegion()
  {
    return region;
  }

  @Override public void reset()
  {
  }

  public void addEvent()
      throws ParserConfigurationException, TransformerException
  {
    String name = nameField.getText();
    String place = placeField.getText();
    int intFromHours = 0;
    int intFromMinutes = 0;
    int intToHours = 0;
    int intToMinutes = 0;
    boolean valid = true;
    try
    {
      intFromHours = Integer.parseInt(fromHoursField.getText());
      intFromMinutes = Integer.parseInt(fromMinutesField.getText());
      intToHours = Integer.parseInt(toHoursField.getText());
      intToMinutes = Integer.parseInt(toMinutesField.getText());
    }
    catch (Exception e)
    { valid = false;
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText("The time data are invalid");
      alert.setTitle("Invalid data");
      Optional<ButtonType> result = alert.showAndWait();
    }
    if (valid)
    {
      LocalDateTime to = toDateField.getValue()
          .atTime(intToHours, intToMinutes);
      LocalDateTime from = fromDateField.getValue()
          .atTime(intFromHours, intFromMinutes);
      try
      {

        if (Event.VALIDATE_DATA(name, place, from, to))
        {

          model.addEvent(new Event(nameField.getText(), placeField.getText(),
              fromDateField.getValue(), intFromHours, intFromMinutes,
              toDateField.getValue(), intToHours, intToMinutes,
              descriptionField.getText(), new ArrayList<>(),
              commentField.getText(), linkField.getText()));
          model.saveEvent();
          viewHandler.openView(11, model.getEventsList().size() - 1);
        }
      }
      catch (Exception e)
      {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid data");
        alert.setHeaderText(e.getMessage());
        Optional<ButtonType> result = alert.showAndWait();
      }
    }
  }

  public void goBack()
  {
    viewHandler.openView(7, -1);
  }
}
