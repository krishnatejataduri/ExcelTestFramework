package rough;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.testng.internal.ExitCode;

public class DebugPopup {
	
	
	public static Integer showMessage(String exceptionName, String ObjectName, String Action) {
		//Custom button text
		Integer selectedOption = null;
		try {
		final JFrame parent = new JFrame();
		Object[] options = {"Retry",
		                    "Ignore",
		                    "Abort"};
		selectedOption = JOptionPane.showOptionDialog(parent,
		    "Error Message:  "
		    + "\nObject/Function Name: \n"+ObjectName.toUpperCase()+"\nAction: "+Action+"\nException: \n"+exceptionName,
		    "Encountered error",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[2]);
		return (Integer)selectedOption;
		}
		catch(Exception e) {return (Integer)selectedOption;}
		finally{
			//System.exit(0);
		}
	}

	
	
}
