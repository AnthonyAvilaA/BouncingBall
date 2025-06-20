package software.ulpgc.BouncingBall.Model;

public record Vector2D(double x, double y) {
    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2D productByScalar(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public Double dotProduct(Vector2D otherVector) {
        return this.x * otherVector.x() + this.y * otherVector.y();
    }

    public Vector2D subtract(Vector2D subtrahend) {
        return new Vector2D(this.x - subtrahend.x(), this.y - subtrahend.y());
    }

    public Vector2D addition(Vector2D adding) {
        return new Vector2D(this.x + adding.x(), this.y + adding.y());
    }

    public Vector2D divisionByScalar(double scalar) {
        return new Vector2D(this.x / scalar, this.y / scalar);
    }

    public Double squaredModule() {
        return this.x * this.x + this.y * this.y;
    }


    public Double module() {
        return  Math.sqrt(this.x * this.x + this.y * this.y);
    }


    public Vector2D normalize() {
        double magnitude = Math.sqrt(this.squaredModule());
        if (magnitude == 0) {
            return new Vector2D(0, 0); // Avoid division by zero
        }
        return this.divisionByScalar(magnitude);
    }
}
