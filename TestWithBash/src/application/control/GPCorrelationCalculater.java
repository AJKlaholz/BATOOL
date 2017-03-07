package application.control;

public class GPCorrelationCalculater {
	// Berechnung der Korrelation
	public static double getCorrelation(Integer[] characteristic, Integer[] characteristic2) {

		double sx = 0.0;
		double sy = 0.0;
		double sxx = 0.0;
		double syy = 0.0;
		double sxy = 0.0;

		int n = characteristic.length;

		for (int i = 0; i < n; ++i) {
			double x = characteristic[i];
			double y = characteristic2[i];

			sx += x;
			sy += y;
			sxx += x * x;
			syy += y * y;
			sxy += x * y;
		}

		// Berechnung der Kovarianz
		double cov = sxy / n - sx * sy / n / n;
		// Standardabweichung für X
		double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
		// Standardabweichung für Y
		double sigmay = Math.sqrt(syy / n - sy * sy / n / n);
		// Fange Standardabweichung = 0 ab und gebe 0.0 statt NaN zurück
		if (java.lang.Double.isNaN(cov / (sigmax * sigmay))) {
			return 0.0;
		}
		return Math.rint((cov / (sigmax * sigmay)) * 1000) / 1000.;

	}
}
