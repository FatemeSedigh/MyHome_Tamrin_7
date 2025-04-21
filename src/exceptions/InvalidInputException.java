package exceptions;

class InvalidInputException extends Exception {
    public InvalidInputException(String message) { super(message); }
    public static InvalidInputException general() { return new InvalidInputException("invalid input"); }
    public static InvalidInputException forCommand(String command) { return new InvalidInputException("invalid " + command + " command"); }
}
