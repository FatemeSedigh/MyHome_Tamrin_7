package exceptions;

class RuleException extends Exception {
    public RuleException(String message) { super(message); }
    public static RuleException invalidTime() { return new RuleException("invalid time"); }
    public static RuleException invalidAction() { return new RuleException("invalid action"); }
    public static RuleException duplicateRule() { return new RuleException("duplicate rule"); }
    }

