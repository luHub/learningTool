/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commons;

import java.sql.Timestamp;

/**
 *
 * @author mey
 */
public class WorkbookItem {
    Timestamp timeStamp;
    ItemType itemType;
    
    public WorkbookItem(ItemType itemType, Timestamp timestamp){
        this.timeStamp = timestamp;
        this.itemType = itemType;
    }
    
}