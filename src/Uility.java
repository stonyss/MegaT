/**
 * @author sauliuss
 * The utility class for common jobs such as measurement conversion  
 */
public class Uility {

	// Measurement conversion from millimeters to points.
	public static final float millimetersToPoints(final float value) {
		return inchesToPoints(millimetersToInches(value));
	}

	// Measurement conversion from millimeters to inches.
	public static final float millimetersToInches(final float value) {
		return value / 25.4f;
	}

	// Measurement conversion from points to millimeters.
	public static final float pointsToMillimeters(final float value) {
		return inchesToMillimeters(pointsToInches(value));
	}

	// Measurement conversion from points to inches.
	public static final float pointsToInches(final float value) {
		return value / 72f;
	}

	// Measurement conversion from inches to millimeters.
	public static final float inchesToMillimeters(final float value) {
		return value * 25.4f;
	}

	// Measurement conversion from inches to points.
	public static final float inchesToPoints(final float value) {
		return value * 72f;
	}

}
