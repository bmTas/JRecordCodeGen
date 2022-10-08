package example.codeGen.support.interfaces;

public interface ICblField<Type> {

    public boolean isPresent();
    public void setPresent(boolean present);

    public Type get();
    public void set(Type value);
}
