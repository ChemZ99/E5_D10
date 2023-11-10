package Exercises.E5_D10.Exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(int id){
        super("Elemento con id " + id + " non trovato!");
    }
}
