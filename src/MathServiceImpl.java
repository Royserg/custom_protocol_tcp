
public class MathServiceImpl implements MathService {


    @Override
    public double add(double value1, double value2) {
        return value1 + value2;
    }

    @Override
    public double sub(double value1, double value2) {
        return value1 - value2;
    }

    @Override
    public double div(double value1, double value2) {
        if (value2 != 0) {
            return value1 / value2;
        }
        return Double.MIN_VALUE;
    }

    @Override
    public double mul(double value1, double value2) {
        return value1 * value2;
    }
}
