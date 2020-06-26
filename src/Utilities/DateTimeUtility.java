/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Model.Appointment;
import Model.User;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author micha
 */
public class DateTimeUtility {
    
    public static Timestamp localDateTimeToUTC(LocalDateTime dateTime){
        
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime initialDateTime = dateTime.atZone(zoneId);
        ZonedDateTime initialUTC = initialDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        dateTime = initialUTC.toLocalDateTime();
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        
        return timestamp;
        
    }
    
    public static LocalDateTime UTCToLocalDateTime(Timestamp timestamp){
        
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime initialTimestamp = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime initialLocalDateTime = initialTimestamp.withZoneSameInstant(zoneId);
        LocalDateTime convertedDateTime = initialLocalDateTime.toLocalDateTime();
        
        return convertedDateTime;
        
    }
    

}
