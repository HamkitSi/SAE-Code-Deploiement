package org.example;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.fr")

public class MonserviceWeb {
    @WebMethod(operationName = "convertir")
    public double conversion(double mt){
        return mt*0.9;
    }

    public double somme(@WebParam(name = "parametre 1") double a, @WebParam(name = "parametre 3") double b){
        return a+b;
    }
}
