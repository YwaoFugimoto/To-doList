package br.edu.unifalmg.exception;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidDeadLineException extends RuntimeException{


    public InvalidDeadLineException (String message){
        super(message);
    }
}
