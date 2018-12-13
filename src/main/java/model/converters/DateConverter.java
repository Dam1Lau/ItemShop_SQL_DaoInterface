/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.converters;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author Laura
 */
public class DateConverter implements SingleValueConverter{
    
    
     //Indicates how to convert to String a Telephone object
        @Override
	public String toString(Object obj) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = (LocalDate) obj;
            return date1.format(formatter);
    }

    
        @Override
	public Object fromString(String date) {
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
           LocalDate finalDate = LocalDate.parse(date, formatter);
           return finalDate;
    }

    //Indicates the types to be converted

    /**
     *
     * @param type
     * @return
     */
        @Override
    public boolean canConvert(Class type) {
            return type.equals(LocalDate.class);
    }
}
