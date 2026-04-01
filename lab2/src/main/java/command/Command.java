package command;

public interface Command<T> {

    Response<T> execute(String[] args);

}
