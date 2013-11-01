import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

// klase nustatanti vertiklaliu separatoriu vaizda impozicijoje
public class VerticalSeparator extends JPanel {
    
    JTextField txfLeft;
    JTextField txfRight;
    
    public VerticalSeparator() {
	
	SpringLayout layout = new SpringLayout();
	setLayout(layout);

	JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
	txfLeft = new JTextField(3);
	txfRight = new JTextField(3);
	
	txfLeft.setHorizontalAlignment(JTextField.RIGHT);
	
	add(sep);
	add(txfLeft);
	add(txfRight);
	
	SpringLayout.Constraints pnlCons = layout.getConstraints(this);
	SpringLayout.Constraints sepCons = layout.getConstraints(sep);
	SpringLayout.Constraints leftTxfCons = layout.getConstraints(txfLeft);
	SpringLayout.Constraints rightTxfCons = layout.getConstraints(txfRight);
	
	//prikabiname separatorisu galus prie paneles virsaus ir apacios
	pnlCons.setConstraint(SpringLayout.NORTH, Spring.sum(Spring.constant(0), sepCons.getConstraint(SpringLayout.NORTH)));
	pnlCons.setConstraint(SpringLayout.SOUTH, Spring.sum(Spring.constant(0), sepCons.getConstraint(SpringLayout.SOUTH)));
	
	//centruojame horizontaliai separatoriu
	layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, sep, 0, SpringLayout.HORIZONTAL_CENTER, this);
	//leftTxfCons.setConstraint(SpringLayout.EAST, Spring.sum);
	
	
	//prikabiname tekstinius laukus prie paneles kaires ir desines
	//pnlCons.setConstraint(SpringLayout.WEST, Spring.sum(Spring.constant(0), leftTxfCons.getConstraint(SpringLayout.WEST)));
	//pnlCons.setConstraint(SpringLayout.EAST, Spring.sum(Spring.constant(0), rightTxfCons.getConstraint(SpringLayout.EAST)));
	
	//prikabiname tekstinius laukus prie separatoriaus
	sepCons.setX(Spring.sum(Spring.constant(-3), leftTxfCons.getConstraint(SpringLayout.EAST)));
	//rightTxfCons.setConstraint(SpringLayout.WEST, sepCons.getWidth());
	rightTxfCons.setX(Spring.sum(Spring.constant(7),sepCons.getConstraint(SpringLayout.WEST)));
	
	
	
	
	
	
    }

}
