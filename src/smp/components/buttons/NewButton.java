package smp.components.buttons;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import smp.ImageLoader;
import smp.components.Values;
import smp.components.general.ImagePushButton;
import smp.components.staff.sequences.StaffArrangement;
import smp.components.staff.sequences.StaffSequence;
import smp.fx.Dialog;
import smp.fx.SMPFXController;
import smp.stateMachine.ProgramState;
import smp.stateMachine.StateMachine;

/**
 * This is the button that creates a new song.
 *
 * @author RehdBlob
 * @since 2013.12.18
 *
 */
public class NewButton extends ImagePushButton {

    /**
     * Default constructor.
     *
     * @param i
     *            This is the <code>ImageView</code> object that will house the
     *            Load button.
     * @param ct
     *            The FXML controller object.
     * @param im
     *            The Image loader object.
     */
    public NewButton(ImageView i, SMPFXController ct, ImageLoader im) {
        super(i, ct, im);
    }

    @Override
    protected void reactPressed(MouseEvent event) {
        ProgramState curr = StateMachine.getState();
        if (curr == ProgramState.EDITING)
            newSong();
        else if (curr == ProgramState.ARR_EDITING)
            newArrangement();
    }

    @Override
    protected void reactReleased(MouseEvent event) {
        // do nothing.
    }

    /**
     * Creates a new song and clears the staff of all notes. Make sure you save
     * your song first! The action is ignored if the song is playing.
     */
    private void newSong() {
        boolean cont = true;
        if (StateMachine.isSongModified())
            cont = Dialog
                    .showYesNoDialog("The current song has been modified!\n"
                            + "Create a new song anyway?");

        if (cont) {
            theStaff.setSequence(new StaffSequence());
            theStaff.setSequenceFile(null);
            Slider sc = theStaff.getControlPanel().getScrollbar();
            sc.setValue(0);
            sc.setMax(Values.DEFAULT_LINES_PER_SONG - 10);
            ArrowButton.setEndOfFile(false);
            theStaff.redraw();
            controller.getNameTextField().clear();
            StateMachine.setSongModified(false);
        }
    }

    /**
     * Creates a new arrangement and clears the staff of all notes. Make sure
     * you save your arrangement first! The action is ignored if an arrangement
     * is playing.
     */
    private void newArrangement() {
        boolean cont = true;
        if (StateMachine.isArrModified()) {
            cont = Dialog
                    .showYesNoDialog("The current arrangement has been\n"
                            + "modified! Create a new arrangement\nanyway?");
        }
        if (cont) {
            theStaff.setArrangement(new StaffArrangement());
            theStaff.setArrangementFile(null);
            controller.getNameTextField().clear();
            theStaff.getArrangementList().getItems().clear();
            StateMachine.setArrModified(false);
        }
    }

}
