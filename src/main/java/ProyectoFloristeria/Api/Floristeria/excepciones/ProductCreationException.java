package ProyectoFloristeria.Api.Floristeria.excepciones;

public class ProductCreationException extends Exception{
    public ProductCreationException(String message){
        super(message);
    }
    public ProductCreationException(){}
}
