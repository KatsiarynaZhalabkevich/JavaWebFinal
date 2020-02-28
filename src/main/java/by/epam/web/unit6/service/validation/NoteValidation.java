package by.epam.web.unit6.service.validation;

public class NoteValidation {

    private final static NoteValidation instance = new NoteValidation();
    private NoteValidation(){}
    public NoteValidation getInstance(){
        return instance;
    }
    //нужно ли проверять id юзеров и тарифов, когда делается запись???

}
