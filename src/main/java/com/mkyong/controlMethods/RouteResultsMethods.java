package com.mkyong.controlMethods;

import com.mkyong.controllers.EmployeePartAController;
import com.mkyong.controllers.RouteResultsController;
import com.mkyong.controllers.SelectCarsController;
import com.mkyong.controllers.SelectRouteController;
import com.mkyong.main.Main;
import com.mkyong.transport.*;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akielbiewski on 12.10.2016.
 */
public class RouteResultsMethods {

    private static String url = "http://nafta.wnp.pl/ceny_paliw/";


    public static List<PRACOWNIK> chooseDrivers(List<PRACOWNIK> availableWorker, List<POJAZD> selectedCars, String parameter){

        List<PRACOWNIK> selectedDrivers = new ArrayList<>();

        if(parameter.equals("Koszt")){

            for(int i = 0; i < selectedCars.size(); i++) {
                selectedDrivers.add(availableWorker.get(i));
            }
            return  selectedDrivers;

        }else{

        }
        return null;

    }




    public static List<POJAZD> chooseCars(List<POJAZD> availableCars, int loadSize, String parameter, int numberOfDrivers){

        if(parameter.equals("Koszt")){

            List<List<POJAZD>> listOfList = allSubsetsOfCars(availableCars, loadSize, numberOfDrivers);

            if(listOfList.size()>0) {
                Double minValue = listOfList.get(0).stream().mapToDouble(POJAZD::getSrednieSpalanie).sum();
                List<POJAZD> selectedList = listOfList.get(0);
                for (List<POJAZD> list : listOfList) {
                    if (list.stream().mapToDouble(POJAZD::getSrednieSpalanie).sum() < minValue) {
                        selectedList = list;
                        minValue = selectedList.stream().mapToDouble(POJAZD::getSrednieSpalanie).sum();
                    } else if (list.stream().mapToDouble(POJAZD::getSrednieSpalanie).sum() == minValue && list.size() < selectedList.size()) {
                        selectedList = list;
                        minValue = selectedList.stream().mapToDouble(POJAZD::getSrednieSpalanie).sum();
                    }
                }
                selectedList.stream().forEach(e -> System.out.println(e.getMarka() + " " + e.getModel()));
                return selectedList;
            }else{
                return null;
            }
        }
        else{

            return availableCars;
        }


    }

    private static List<List<POJAZD>> allSubsetsOfCars(List<POJAZD> pojazdList, int load, int driversCount) {

        List<List<POJAZD>> res = new ArrayList<>();
        int allMasks = (1 << pojazdList.size());
        for (int i = 1; i < allMasks; i++)
        {
            List<POJAZD> elementList = new ArrayList<>();
            for (int j = 0; j < pojazdList.size(); j++)
                if ((i & (1 << j)) > 0)
                    elementList.add(pojazdList.get(j));

            Long capacity = elementList.stream().mapToLong(POJAZD::getPojemnoscLadowni).sum();
            System.out.println(capacity + " " + load);
            if(capacity > load && elementList.size() <= driversCount)
            res.add(elementList);
        }
        return res;
    }

    public static void saveRoute(TRASA trasa, List<TRASAPOJAZD> trasapojazds, List<TRASAPRACOWNIK> trasapracowniks){

        Session session = HibernateUtil.getSessionFactory().openSession();

            try
            {
                session.beginTransaction();
                session.save(trasa);
                for(TRASAPOJAZD trasapojazd : trasapojazds)
                session.save(trasapojazd);
                for(TRASAPRACOWNIK trasapracownik : trasapracowniks)
                session.save(trasapracownik);
                session.getTransaction().commit();

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                session.close();
            }

    }

    public static void setDBObjects(){

        RouteResultsController.trasa  = new TRASA(SelectRouteController.distance, SelectRouteController.startLocation,
                SelectRouteController.endLocation, SelectRouteController.priorytet, Main.activeUserEntity);

        for(POJAZD pojazd : RouteResultsController.selectedCars)
            RouteResultsController.trasapojazdList.add(new TRASAPOJAZD(RouteResultsController.trasa, pojazd, getFuelCost(pojazd)));

        for(PRACOWNIK pracownik : RouteResultsController.selectedDrivers)
            RouteResultsController.trasapracownikList.add(new TRASAPRACOWNIK(RouteResultsController.trasa, pracownik));

    }



    public static Double getFuelCost(POJAZD pojazd)
    {

        Document document;
        Double costOfFuelType = 0.0;

        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();

            Elements elems = document.select(("td"));//ostrzezenia o panstwie

            boolean check = false;
            for (int i = 0; i < elems.size(); i++){
                if(elems.get(i).text().equals("Średnia")){

                    if(check){

                        if(pojazd.getRodzajPaliwa().equals("ON"))
                        costOfFuelType = Double.valueOf(elems.get(i+1).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));
                        else if(pojazd.getRodzajPaliwa().equals("e95"))
                        costOfFuelType = Double.valueOf(elems.get(i+2).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));
                        else if(pojazd.getRodzajPaliwa().equals("e98"))
                        costOfFuelType = Double.valueOf(elems.get(i+3).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));
                        else if(pojazd.getRodzajPaliwa().equals("LPG"))
                        costOfFuelType = Double.valueOf(elems.get(i+4).text().replaceAll("(?<=(\\d\\.\\d\\d)).*", ""));

                    }
                    else
                        check = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return getFuelCost(costOfFuelType, pojazd);

    }

    public static Double getFuelCost(Double cost, POJAZD pojazd)
    {
            return (SelectRouteController.distance/100) * pojazd.getSrednieSpalanie() * cost;
    }
    
}
