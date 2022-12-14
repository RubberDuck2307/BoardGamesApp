package Controllers;

import Application.ViewHandler;
import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.time.LocalDate;
import java.util.Optional;

public class ReservationsDetailsPageController implements Controller{

    private Region region;
    private ModelManager model;
    private ViewHandler viewHandler;

    public TextField boardGameField;
    public TextField memberField;
    public DatePicker startingDateField;
    public DatePicker endingDateField;
    public TextArea commentField;
    public Button borrowButton;
    public Button editButton;
    public Button deleteButton;
    public Button memberInfoButton;
    public Button boardGameButton;


    private int ID;
    private EventHandler save;

    @Override
    public void init(Region region, ModelManager model, ViewHandler viewHandler, int ID) {
        this.region = region;
        this.model = model;
        this.viewHandler = viewHandler;
        this.ID = ID;
        setData();
    }

    public void setData()
    {
        BoardGamesList boardGamesList = model.getBoardGamesList();
        PlayersList playersList = model.getPlayersList();
        Reservation reservation = model.getReservationsList().getReservationByID(ID);

        boardGameField.setText(boardGamesList.getNameByID(reservation.getGameID()));
        memberField.setText(playersList.getNameByID(reservation.getPlayerID()) + " " + playersList.getPlayerByID(reservation.getPlayerID()).getPhoneNumber());
        startingDateField.setValue(reservation.getFrom());
        endingDateField.setValue(reservation.getTo());
        commentField.setText(reservation.getComment());

        boardGameField.setMouseTransparent(true);
        boardGameField.setFocusTraversable(false);
        memberField.setMouseTransparent(true);
        memberField.setFocusTraversable(false);
        startingDateField.setMouseTransparent(true);
        startingDateField.setFocusTraversable(false);
        endingDateField.setMouseTransparent(true);
        endingDateField.setFocusTraversable(false);
        commentField.setMouseTransparent(true);
        commentField.setFocusTraversable(false);
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public void reset() {

    }

    public void edit(ActionEvent actionEvent)
    {
        Reservation reservation = model.getReservationsList().getReservationByID(ID);
        startingDateField.setMouseTransparent(false);
        startingDateField.setFocusTraversable(true);
        endingDateField.setMouseTransparent(false);
        endingDateField.setFocusTraversable(true);
        commentField.setMouseTransparent(false);
        commentField.setFocusTraversable(true);

        editButton.setText("Save Changes");

        save = event -> {
            int boardGameID = reservation.getGameID();
            int memberID = reservation.getPlayerID();
            LocalDate startingDate = startingDateField.getValue();
            LocalDate endingDate = endingDateField.getValue();
            String comment = commentField.getText();
            if (startingDate.isBefore(LocalDate.now()))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Data");
                alert.setHeaderText("The reservation cannot start in the past");
                Optional<ButtonType> result = alert.showAndWait();
            }
            else
            {
                try
                {
                    ReservationsList reservationsList = model.getReservationsList();
                    BorrowingsList borrowingsList = model.getBorrowingsList();
                    PlayersList playersList = model.getPlayersList();

                    if (Reservation.VALIDATE_DATA(reservationsList,
                        borrowingsList, startingDate, endingDate,
                        model.getReservationsList().getReservationByID(ID).getGameID(),
                        playersList,
                        model.getReservationsList().getReservationByID(ID).getPlayerID(), ID,
                        -1))
                    {
                        Reservation reservation1 = new Reservation(ID, memberID,
                            boardGameID, startingDate, endingDate, comment);
                        model.setReservationByID(reservation1, ID);
                        try
                        {
                            model.saveReservation();
                        }
                        catch (ParserConfigurationException e)
                        {
                            throw new RuntimeException(e);
                        }
                        catch (TransformerException e)
                        {
                            throw new RuntimeException(e);
                        }
                        setData();
                        editButton.setOnAction(this::edit);
                        editButton.setText("Edit");
                    }
                }
                catch (Exception error)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Data");
                    alert.setHeaderText(error.getMessage());
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
        };

            editButton.setOnAction(save);
        }


    public void delete() throws ParserConfigurationException, TransformerException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to delete the reservation");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            model.getReservationsList().deleteByID(ID);
            model.saveReservation();
            viewHandler.openView(5,-1, -1);}
    }

    public void goBack(){
        viewHandler.openView(5,-1,-1);
    }

    public void displayBoardGame(){
        viewHandler.openView(9, model.getReservationsList().getReservationByID(ID).getGameID());
    }

    public void displayMember(){
        viewHandler.openView(8,  model.getReservationsList().getReservationByID(ID).getPlayerID());
    }

    @FXML public void borrowGame()
        throws ParserConfigurationException, TransformerException
    {
        Reservation reservation = model.getReservationsList()
            .getReservationByID(ID);
        if (model.getBorrowingsList().getByGameID(reservation.getGameID()).size() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game is borrowed");
            alert.setHeaderText("The Game is already borrowed");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Reservation newBorrowing = new Reservation(reservation.getPlayerID(),
                reservation.getGameID(), reservation.getFrom(), reservation.getTo(), reservation.getComment());
            model.deleteReservationByID(reservation.getID());
            model.getBorrowingsList().addBorrowing(newBorrowing);
            model.saveReservation();
            model.saveBorrowing();
            viewHandler.openView(6, -1);
        }


    }
//    public void borrowGame()
//    {
//        viewHandler.openView();
//    }
}
