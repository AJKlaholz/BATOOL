package application.control;

public class GPCorrelationCalculater {

	public static double getCorrelation(Double[] characteristic, Double[] characteristic2, boolean round) {
		// TODO: check here that arrays are not null, of the same length etc

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

		// covariation
		double cov = sxy / n - sx * sy / n / n;
		// standard error of x
		double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
		// standard error of y
		double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

		// correlation is just a normalized covariation
		if (round) {

			return Math.rint((cov / sigmax / sigmay) * 1000) / 1000.;

		} else {
			return cov / sigmax / sigmay;
		}
	}
}
