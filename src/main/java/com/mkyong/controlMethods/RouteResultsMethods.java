package com.mkyong.controlMethods;

import com.mkyong.transport.POJAZD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akielbiewski on 12.10.2016.
 */
public class RouteResultsMethods {

    public static List<POJAZD> chooseCars(List<POJAZD> availbleCars, int loadSize, String parameter){

        if(parameter.equals("Koszt")){

            List<List<POJAZD>> listOfList = allSubsetsOfCars(availbleCars, loadSize);
            Double minValue = listOfList.get(0).stream().mapToDouble(POJAZD::getSrednieSpalanie).sum();
            List<POJAZD> selectedList = listOfList.get(0);
            for(List<POJAZD> list: listOfList){
                if(list.stream().mapToDouble(POJAZD::getSrednieSpalanie).sum() < minValue) {
                    selectedList = list;
                }
            }
            selectedList.stream().forEach(e-> System.out.println(e.getMarka() +" "+e.getModel()));
            return selectedList;
        }
        else{

            return availbleCars;
        }


    }


    private static List<List<POJAZD>> allSubsetsOfCars(List<POJAZD> pojazdList, int load) {

        List<List<POJAZD>> res = new ArrayList<>();
        int allMasks = (1 << pojazdList.size());
        for (int i = 1; i < allMasks; i++)
        {
            List<POJAZD> elementList = new ArrayList<>();
            for (int j = 0; j < pojazdList.size(); j++)
                if ((i & (1 << j)) > 0)
                    elementList.add(pojazdList.get(j));

            int capacity = elementList.stream().mapToInt(POJAZD::getPojemnoscLadowni).sum();
            System.out.println(capacity + " " + load);
            if(capacity > load)
            res.add(elementList);
        }
        return res;
    }

}
