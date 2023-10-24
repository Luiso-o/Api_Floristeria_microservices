package ProyectoFloristeria.Api.Floristeria.excepciones;

public class ProductNotFountException extends Exception{
    public ProductNotFountException(String message){
        super(message);
    }
    public ProductNotFountException(){}
}
