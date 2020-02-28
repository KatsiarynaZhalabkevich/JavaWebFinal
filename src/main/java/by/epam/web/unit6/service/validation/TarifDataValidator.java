package by.epam.web.unit6.service.validation;

public class TarifDataValidator {
    /**
     * Класс для валидации данных при создании нового тарифа
     */
    private static final TarifDataValidator instance = new TarifDataValidator();
    private TarifDataValidator(){}
    public static TarifDataValidator getInstance(){
        return instance;
    }


    public boolean nameDescripValidate(String name){

        return name!=null&&name!="";
    }
    public boolean speedValidate(int speed){
        return speed>=10&&speed<=400;

    }
    public boolean priceValidate (double price){
        return price>10&&price<100;
    }

    public boolean discountValidate(double disc){
        return disc>=0&&disc<=90;
    }

}
