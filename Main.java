import java.util.Stack;

abstract class Shape {
    private int x;
    private int y;
    private String color;

    public Shape(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    void move(int x, int y) {
        setX(x);
        setY(y);
    } 

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

class Circle extends Shape {
    public Circle(int x, int y, String color) {
        super(x, y, color);
    }

    public void move(int x, int y) {
        setX(x);
        setY(y);
        System.out.println("Circle moved to (" + x + ", " + y + ")");
    }

    public void setColor(String color) {
        super.setColor(color);
        System.out.println("Circle color changed to " + color);
    }
}

class Square extends Shape {
    public Square(int x, int y, String color) {
        super(x, y, color);
    }

    public void move(int x, int y) {
        setX(x);
        setY(y);
        System.out.println("Square moved to (" + x + ", " + y + ")");
    }

    public void setColor(String color) {
        super.setColor(color);
        System.out.println("Square color changed to " + color);
    }
}

interface Command {
    void execute();
    void undo();
}

class MoveCommand implements Command {
    private Shape shape;
    private int x, y;
    private int prevX, prevY;

    public MoveCommand(Shape shape, int x, int y) {
        this.shape = shape;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        prevX = shape.getX();
        prevY = shape.getY();
        shape.move(x, y);
        // System.out.println("Moved shape to (" + x + ", " + y + ")");
    }

    @Override
    public void undo() {
        shape.move(prevX, prevY);
        System.out.println("Succesfully undo, Moved shape back to (" + prevX + ", " + prevY + ")");
    }
}

class ColorChangeCommand implements Command {
    private Shape shape;
    private String color;
    private String prevColor;

    public ColorChangeCommand(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public void execute() {
        prevColor = shape.getColor();
        shape.setColor(color);
        // System.out.println("Changed shape color to " + color);
    }

    @Override
    public void undo() {
        shape.setColor(prevColor);
        System.out.println("Succesfully undo, Changed shape color back to " + prevColor);
    }
}

class CommandHistory {
    private Stack<Command> history = new Stack<>();

    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return history.pop();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}

public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle(0 , 0, "blue");
        Shape square = new Square(0, 0, "green");

        CommandHistory history = new CommandHistory();

        Command moveCircle = new MoveCommand(circle, 10, 20);
        moveCircle.execute();
        history.push(moveCircle);

        Command colorSquare = new ColorChangeCommand(square, "red");
        colorSquare.execute();
        history.push(colorSquare);

        Command moveSquare = new MoveCommand(square, 30, 40);
        moveSquare.execute();
        history.push(moveSquare);

        Command moveSquare2 = new MoveCommand(square, 35, 45);
        moveSquare2.execute();
        history.push(moveSquare);

        Command colorCircle = new ColorChangeCommand(circle, "yellow");
        colorCircle.execute();
        history.push(colorCircle);

        Command moveCircleAgain = new MoveCommand(circle, 50, 60);
        moveCircleAgain.execute();
        history.push(moveCircleAgain);

        Command colorSquareAgain = new ColorChangeCommand(square, "red");
        colorSquareAgain.execute();
        history.push(colorSquareAgain);

        // Undo last action
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        }
        else{
            System.out.println("No more action to undo");
        }
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        }
        else{
            System.out.println("No more action to undo");
        }
    }
}