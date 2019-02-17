import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The Postfix class implements an evaluator for integer postfix expressions.
 *
 * Postfix notation is a simple way to define and write arithmetic expressions
 * without the need for parentheses or priority rules. For example, the postfix
 * expression "1 2 - 3 4 + *" corresponds to the ordinary infix expression
 * "(1 - 2) * (3 + 4)". The expressions may contain decimal 32-bit integer
 * operands and the four operators +, -, *, and /. Operators and operands must
 * be separated by whitespace.
 *
 * @author  Johan Adamsson
 * @version 2017-12-12
 */
public class Postfix {
    public static class ExpressionException extends Exception {
        public ExpressionException(String message) {
            super(message);
        }
    }

    /**
     * Evaluates the given postfix expression.
     *
     * @param expr  Arithmetic expression in postfix notation
     * @return      The value of the evaluated expression
     * @throws      ExpressionException if the expression is wrong
     */
    public static int evaluate(String expr) throws ExpressionException {
        Stack<Integer> result = new LinkedList<>();
        expr = expr.trim();
        String[] arr = expr.split("\\s+"); //Separerar strängen baserat på mellanslag
        boolean throwException = false;
        for(int i = 0; i< arr.length; i++){
          if(isInteger(arr[i])){
            result.push(Integer.parseInt(arr[i]));
          }
          else if(isOperator(arr[i])){
            if(result.size() >= 2){
              int a = result.pop(), b = result.pop();
              switch(arr[i]){
                case "+":
                  result.push(b+a);
                  break;
                case "-":
                  result.push(b-a);
                  break;
                case "*":
                  result.push(b*a);
                  break;
                case "/":
                  if(a != 0){
                  result.push(b/a);
                  }
                  else{
                    throwException = true;
                  }
                  break;
              }
            }
            else{
              throwException = true;
              break;
            }
          }
          else{
            throwException = true;
            break;
          }
        }
        if(result.size() != 1){
          throwException = true;
        }
        if(throwException){
          throw new ExpressionException("Incorrect Arguments");
        }
        return result.top();
    }

    /**
     * Returns true if s is an operator.
     *
     * A word of caution on using the String.matches method: it returns true
     * if and only if the whole given string matches the regex. Therefore
     * using the regex "[0-9]" is equivalent to "^[0-9]$".
     *
     * An operator is one of '+', '-', '*', '/'.
     */
    private static boolean isOperator(String s) {
      if(s.length() == 1){ //Vi vill inte kolla t.ex. -1
        Pattern pattern = Pattern.compile("[+\\-*/()]");
        Matcher operator = pattern.matcher(s);
        while(operator.find()){
        if(operator.group().length() == s.length()){
          return true;
        }
      }
      }
        return false;
    }

    /**
     * Retujava.lang.IllegalStateException: No match found
rns true if s is an integer.
     *
     * A word of caution on using the String.matches method: it returns true
     * if and only if the whole given string matches the regex. Therefore
     * using the regex "[0-9]" is equivalent to "^[0-9]$".
     *
     * We accept two types of integers:
     *
     * - the first type consists of an optional '-'
     *   followed by a non-zero digit
     *   followed by zero or more digits,
     *
     * - the second type consists of an optional '-'
     *   followed by a single '0'.
     */
    private static boolean isInteger(String s) {
      Pattern pattern = Pattern.compile("(([1-9])([0-9]*))|((-)?([1-9])([0-9]*))|((-)?([0]$))");
      Matcher operand = pattern.matcher(s);
      while(operand.find()){
        if(operand.group().length() == s.length()){
          return true;
        }
      }
        return false;
    }
}
