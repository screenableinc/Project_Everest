package reply_cirlce.screenable.project_everest;

public class CustomException extends Exception{
    String message;
    public CustomException(String message){
        this.message=message;
    }

    @Override
    public String toString() {
        return message;
    }
}